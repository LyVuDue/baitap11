package vn.ute.dao.impl;

import vn.ute.dao.IOrderDao_24162016;
import vn.ute.entity.CartItem_24162016;
import vn.ute.entity.Order_24162016;
import vn.ute.util.DBConnect_24162016;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class OrderDaoImpl_24162016 implements IOrderDao_24162016 {
    private static final String SELECT_ORDER =
            "SELECT c.*, COALESCE(c.totalAmount, (SELECT SUM(i.quantity*i.unitPrice) FROM CartItem"
                + " i WHERE i.cartId=c.cartId),0) AS orderTotal FROM Cart c ";

    @Override
    public String createCodOrder(Order_24162016 order, List<CartItem_24162016> cartItems) {
        if (cartItems.isEmpty()) throw new IllegalArgumentException("Giỏ hàng đang trống.");
        List<CartItem_24162016> sorted = new ArrayList<>(cartItems);
        sorted.sort(Comparator.comparingInt(CartItem_24162016::getProductId));
        try (Connection c = DBConnect_24162016.getConnection()) {
            c.setAutoCommit(false);
            try {
                List<CartItem_24162016> verified = new ArrayList<>();
                BigDecimal total = BigDecimal.ZERO;
                for (CartItem_24162016 item : sorted) {
                    if (item.getQuantity() < 1 || item.getQuantity() > 1000)
                        throw new IllegalArgumentException("Số lượng không hợp lệ.");
                    try (PreparedStatement p =
                            c.prepareStatement(
                                    "SELECT productName,images,price,stock,status FROM Product"
                                        + " WHERE productId=? FOR UPDATE")) {
                        p.setInt(1, item.getProductId());
                        try (ResultSet r = p.executeQuery()) {
                            if (!r.next() || r.getInt("status") != 1)
                                throw new IllegalArgumentException(
                                        "Một sản phẩm trong giỏ không còn được bán.");
                            if (r.getInt("stock") < item.getQuantity())
                                throw new IllegalArgumentException(
                                        "Sản phẩm "
                                                + r.getString("productName")
                                                + " không đủ tồn kho. Hãy cập nhật giỏ hàng.");
                            BigDecimal price = r.getBigDecimal("price");
                            if (price == null || price.signum() < 0)
                                throw new IllegalArgumentException("Giá sản phẩm không hợp lệ.");
                            CartItem_24162016 checked =
                                    new CartItem_24162016(
                                            item.getProductId(),
                                            r.getString("productName"),
                                            r.getString("images"),
                                            price.setScale(2, RoundingMode.HALF_UP),
                                            item.getQuantity());
                            verified.add(checked);
                            total = total.add(checked.getSubtotal());
                        }
                    }
                }
                try (PreparedStatement p =
                        c.prepareStatement(
                                "INSERT INTO"
                                    + " Cart(cartId,userId,buyDate,status,recipientName,phone,address,note,paymentMethod,totalAmount)"
                                    + " VALUES(?,?,CURRENT_TIMESTAMP,0,?,?,?,?,'COD',?)")) {
                    p.setString(1, order.getOrderId());
                    p.setInt(2, order.getUserId());
                    p.setString(3, order.getRecipientName());
                    p.setString(4, order.getPhone());
                    p.setString(5, order.getAddress());
                    p.setString(6, order.getNote());
                    p.setBigDecimal(7, total);
                    p.executeUpdate();
                }
                try (PreparedStatement detail =
                                c.prepareStatement(
                                        "INSERT INTO"
                                            + " CartItem(cartItemId,quantity,unitPrice,productId,cartId,productName,images)"
                                            + " VALUES(?,?,?,?,?,?,?)");
                        PreparedStatement stock =
                                c.prepareStatement(
                                        "UPDATE Product SET stock=stock-? WHERE productId=? AND"
                                            + " stock>=?")) {
                    for (CartItem_24162016 item : verified) {
                        detail.setString(1, UUID.randomUUID().toString());
                        detail.setInt(2, item.getQuantity());
                        detail.setBigDecimal(3, item.getUnitPrice());
                        detail.setInt(4, item.getProductId());
                        detail.setString(5, order.getOrderId());
                        detail.setString(6, item.getProductName());
                        detail.setString(7, item.getImages());
                        detail.executeUpdate();
                        stock.setInt(1, item.getQuantity());
                        stock.setInt(2, item.getProductId());
                        stock.setInt(3, item.getQuantity());
                        if (stock.executeUpdate() != 1)
                            throw new SQLException("Tồn kho thay đổi trong khi đặt hàng.");
                    }
                }
                c.commit();
                return order.getOrderId();
            } catch (SQLException | RuntimeException e) {
                try {
                    c.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Không thể lưu đơn hàng.", e);
        }
    }

    @Override
    public List<Order_24162016> findByUser(int userId, Integer status) {
        List<Order_24162016> orders = new ArrayList<>();
        String sql =
                SELECT_ORDER
                        + "WHERE c.userId=?"
                        + (status == null ? "" : " AND c.status=?")
                        + " ORDER BY c.buyDate DESC,c.cartId DESC";
        try (Connection c = DBConnect_24162016.getConnection();
                PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, userId);
            if (status != null) p.setInt(2, status);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) orders.add(mapOrder(r));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Không thể đọc lịch sử đơn hàng.", e);
        }
        return orders;
    }

    @Override
    public Order_24162016 findById(String orderId, int userId) {
        try (Connection c = DBConnect_24162016.getConnection();
                PreparedStatement p =
                        c.prepareStatement(SELECT_ORDER + "WHERE c.cartId=? AND c.userId=?")) {
            p.setString(1, orderId);
            p.setInt(2, userId);
            Order_24162016 order;
            try (ResultSet r = p.executeQuery()) {
                if (!r.next()) return null;
                order = mapOrder(r);
            }
            List<CartItem_24162016> items = new ArrayList<>();
            try (PreparedStatement detail =
                    c.prepareStatement(
                            "SELECT"
                                + " i.productId,i.quantity,i.unitPrice,COALESCE(i.productName,p.productName,'Sản"
                                + " phẩm') AS productName,COALESCE(i.images,p.images) AS images"
                                + " FROM CartItem i LEFT JOIN Product p ON p.productId=i.productId"
                                + " WHERE i.cartId=? ORDER BY i.cartItemId")) {
                detail.setString(1, orderId);
                try (ResultSet r = detail.executeQuery()) {
                    while (r.next())
                        items.add(
                                new CartItem_24162016(
                                        r.getInt("productId"),
                                        r.getString("productName"),
                                        r.getString("images"),
                                        r.getBigDecimal("unitPrice")
                                                .setScale(2, RoundingMode.HALF_UP),
                                        r.getInt("quantity")));
                }
            }
            order.setItems(items);
            return order;
        } catch (SQLException e) {
            throw new IllegalStateException("Không thể đọc đơn hàng.", e);
        }
    }

    private Order_24162016 mapOrder(ResultSet r) throws SQLException {
        Order_24162016 order = new Order_24162016();
        order.setOrderId(r.getString("cartId"));
        order.setUserId(r.getInt("userId"));
        order.setBuyDate(r.getTimestamp("buyDate"));
        order.setStatus(r.getInt("status"));
        order.setRecipientName(r.getString("recipientName"));
        order.setPhone(r.getString("phone"));
        order.setAddress(r.getString("address"));
        order.setNote(r.getString("note"));
        order.setPaymentMethod(r.getString("paymentMethod"));
        order.setTotalAmount(r.getBigDecimal("orderTotal").setScale(2, RoundingMode.HALF_UP));
        return order;
    }
}

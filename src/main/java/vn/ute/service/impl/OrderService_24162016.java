package vn.ute.service.impl;

import vn.ute.dao.IOrderDao_24162016;
import vn.ute.dao.impl.OrderDaoImpl_24162016;
import vn.ute.entity.Order_24162016;
import vn.ute.entity.ShoppingCart_24162016;

public class OrderService_24162016 {
    private final IOrderDao_24162016 orders;

    public OrderService_24162016() {
        this(new OrderDaoImpl_24162016());
    }

    public OrderService_24162016(IOrderDao_24162016 orders) {
        this.orders = orders;
    }

    public String checkout(ShoppingCart_24162016 cart, Order_24162016 order) {
        synchronized (cart) {
            if (cart.isEmpty()) throw new IllegalArgumentException("Giỏ hàng đang trống.");
            order.setUserId(cart.getUserId());
            order.setRecipientName(required(order.getRecipientName(), 2, 100, "Tên người nhận"));
            String phone =
                    required(order.getPhone(), 9, 20, "Số điện thoại").replaceAll("[\\s().-]", "");
            if (!phone.matches("\\+?\\d{9,15}"))
                throw new IllegalArgumentException("Số điện thoại phải có từ 9 đến 15 chữ số.");
            order.setPhone(phone);
            order.setAddress(required(order.getAddress(), 10, 500, "Địa chỉ giao hàng"));
            String note = order.getNote() == null ? "" : order.getNote().trim();
            if (note.length() > 500)
                throw new IllegalArgumentException("Ghi chú tối đa 500 ký tự.");
            order.setNote(note);
            order.setPaymentMethod("COD");
            if (order.getOrderId() == null || !order.getOrderId().matches("OD-[a-f0-9-]{36}"))
                throw new IllegalArgumentException(
                        "Phiên đặt hàng không hợp lệ. Hãy tải lại trang.");
            String id = orders.createCodOrder(order, cart.getItems());
            cart.clear();
            return id;
        }
    }

    private String required(String value, int min, int max, String field) {
        String text = value == null ? "" : value.trim();
        if (text.length() < min || text.length() > max)
            throw new IllegalArgumentException(
                    field + " cần từ " + min + " đến " + max + " ký tự.");
        return text;
    }
}

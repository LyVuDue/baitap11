package vn.ute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import vn.ute.dao.impl.OrderDaoImpl_24162016;
import vn.ute.entity.*;
import vn.ute.util.CommerceSchema_24162016;
import vn.ute.util.DBConnect_24162016;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

class OrderDatabaseIT {
    private int userId;
    private int productId;
    private final OrderDaoImpl_24162016 dao = new OrderDaoImpl_24162016();

    @BeforeEach
    void setup() throws Exception {
        CommerceSchema_24162016.migrate();
        try (Connection c = DBConnect_24162016.getConnection()) {
            try (PreparedStatement p =
                    c.prepareStatement(
                            "INSERT INTO Users(username,fullname,password,status,roleId)"
                                + " VALUES(?,'Integration test','temporary',1,2)",
                            Statement.RETURN_GENERATED_KEYS)) {
                p.setString(1, "kiemthu_" + UUID.randomUUID().toString().substring(0, 20));
                p.executeUpdate();
                try (ResultSet r = p.getGeneratedKeys()) {
                    r.next();
                    userId = r.getInt(1);
                }
            }
            try (PreparedStatement p =
                    c.prepareStatement(
                            "INSERT INTO Product(productName,price,amount,stock,status)"
                                + " VALUES('Integration product',100.25,5,5,1)",
                            Statement.RETURN_GENERATED_KEYS)) {
                p.executeUpdate();
                try (ResultSet r = p.getGeneratedKeys()) {
                    r.next();
                    productId = r.getInt(1);
                }
            }
        }
    }

    @AfterEach
    void cleanup() throws Exception {
        try (Connection c = DBConnect_24162016.getConnection()) {
            execute(
                    c,
                    "DELETE i FROM CartItem i JOIN Cart c ON c.cartId=i.cartId WHERE c.userId=?",
                    userId);
            execute(c, "DELETE FROM Cart WHERE userId=?", userId);
            if (productId > 0) execute(c, "DELETE FROM Product WHERE productId=?", productId);
            if (userId > 0) execute(c, "DELETE FROM Users WHERE userId=?", userId);
        }
    }

    @Test
    void codPersistsSnapshotsAndEveryStatusIsReadFromDatabase() throws Exception {
        Order_24162016 order = newOrder();
        dao.createCodOrder(order, List.of(item(2)));
        assertEquals(3, stock());
        Order_24162016 saved = dao.findById(order.getOrderId(), userId);
        assertEquals(new BigDecimal("200.50"), saved.getTotalAmount());
        assertEquals("COD", saved.getPaymentMethod());
        assertEquals("Test recipient", saved.getRecipientName());
        assertEquals(new BigDecimal("100.25"), saved.getItems().get(0).getUnitPrice().setScale(2));
        assertNull(dao.findById(order.getOrderId(), userId + 100000));
        try (Connection c = DBConnect_24162016.getConnection()) {
            execute(c, "UPDATE Product SET price=999 WHERE productId=?", productId);
            for (int state = 0; state < 8; state++) {
                try (PreparedStatement p =
                        c.prepareStatement("UPDATE Cart SET status=? WHERE cartId=?")) {
                    p.setInt(1, state);
                    p.setString(2, order.getOrderId());
                    p.executeUpdate();
                }
                assertEquals(state, dao.findById(order.getOrderId(), userId).getStatus());
                assertEquals(1, dao.findByUser(userId, state).size());
                assertTrue(dao.findByUser(userId, (state + 1) % 8).isEmpty());
            }
        }
        assertEquals(
                new BigDecimal("200.50"),
                dao.findById(order.getOrderId(), userId).getTotalAmount());
    }

    @Test
    void failedOrderRollsBackAllRowsAndInventory() {
        Order_24162016 order = newOrder();
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        dao.createCodOrder(
                                order,
                                List.of(
                                        item(2),
                                        new CartItem_24162016(
                                                Integer.MAX_VALUE,
                                                "Missing",
                                                "",
                                                BigDecimal.ONE,
                                                1))));
        assertNull(dao.findById(order.getOrderId(), userId));
        assertEquals(5, stock());
    }

    @Test
    void simultaneousOrdersCannotOversellLastUnit() throws Exception {
        try (Connection c = DBConnect_24162016.getConnection()) {
            execute(c, "UPDATE Product SET stock=1 WHERE productId=?", productId);
        }
        ExecutorService pool = Executors.newFixedThreadPool(2);
        CountDownLatch start = new CountDownLatch(1);
        Callable<Boolean> attempt =
                () -> {
                    start.await();
                    try {
                        dao.createCodOrder(newOrder(), List.of(item(1)));
                        return true;
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                };
        try {
            Future<Boolean> a = pool.submit(attempt), b = pool.submit(attempt);
            start.countDown();
            int successes =
                    (a.get(15, TimeUnit.SECONDS) ? 1 : 0) + (b.get(15, TimeUnit.SECONDS) ? 1 : 0);
            assertEquals(1, successes);
            assertEquals(0, stock());
            assertEquals(1, dao.findByUser(userId, null).size());
        } finally {
            pool.shutdownNow();
        }
    }

    private Order_24162016 newOrder() {
        Order_24162016 o = new Order_24162016();
        o.setOrderId("OD-" + UUID.randomUUID());
        o.setUserId(userId);
        o.setRecipientName("Test recipient");
        o.setPhone("0901234567");
        o.setAddress("01 Test street, Thu Duc");
        o.setNote("Integration test");
        return o;
    }

    private CartItem_24162016 item(int quantity) {
        return new CartItem_24162016(productId, "Forged price", "", BigDecimal.ONE, quantity);
    }

    private int stock() {
        try (Connection c = DBConnect_24162016.getConnection();
                PreparedStatement p =
                        c.prepareStatement("SELECT stock FROM Product WHERE productId=?")) {
            p.setInt(1, productId);
            try (ResultSet r = p.executeQuery()) {
                r.next();
                return r.getInt(1);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void execute(Connection c, String sql, int id) throws SQLException {
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        }
    }
}

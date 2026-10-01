package vn.ute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vn.ute.dao.IOrderDao_24162016;
import vn.ute.entity.*;
import vn.ute.service.impl.OrderService_24162016;

import java.math.BigDecimal;
import java.util.List;

class OrderServiceTest {
    private ShoppingCart_24162016 cart;
    private Order_24162016 order;
    private FakeOrders dao;
    private OrderService_24162016 service;

    @BeforeEach
    void setup() {
        cart = new ShoppingCart_24162016(42);
        cart.put(new CartItem_24162016(1, "Sản phẩm", "", new BigDecimal("1000.00"), 2));
        order = new Order_24162016();
        order.setOrderId("OD-00000000-0000-0000-0000-000000000001");
        order.setRecipientName(" Nguyễn Văn A ");
        order.setPhone("090 123 4567");
        order.setAddress("  01 Võ Văn Ngân, Thủ Đức  ");
        dao = new FakeOrders();
        service = new OrderService_24162016(dao);
    }

    @Test
    void checkoutUsesCartOwnerNormalizesFieldsAndClearsOnlyAfterSave() {
        order.setUserId(999);
        service.checkout(cart, order);
        assertEquals(42, order.getUserId());
        assertEquals("0901234567", order.getPhone());
        assertEquals("Nguyễn Văn A", order.getRecipientName());
        assertEquals("COD", order.getPaymentMethod());
        assertEquals(1, dao.calls);
        assertTrue(cart.isEmpty());
    }

    @Test
    void failedDatabaseSaveKeepsCart() {
        dao.fail = true;
        assertThrows(IllegalStateException.class, () -> service.checkout(cart, order));
        assertEquals(2, cart.getItemCount());
    }

    @Test
    void invalidPhoneAndAddressDoNotCreateOrder() {
        order.setPhone("abcdefghi");
        assertThrows(IllegalArgumentException.class, () -> service.checkout(cart, order));
        order.setPhone("0901234567");
        order.setAddress("abc");
        assertThrows(IllegalArgumentException.class, () -> service.checkout(cart, order));
        assertEquals(0, dao.calls);
        assertEquals(2, cart.getItemCount());
    }

    @Test
    void emptyCartCannotCheckout() {
        cart.clear();
        assertThrows(IllegalArgumentException.class, () -> service.checkout(cart, order));
        assertEquals(0, dao.calls);
    }

    @Test
    void allEightDatabaseStatusesHaveDistinctLabels() {
        assertEquals(8, OrderStatus_24162016.values().length);
        for (int i = 0; i < 8; i++) assertEquals(i, OrderStatus_24162016.fromCode(i).getCode());
        assertThrows(IllegalArgumentException.class, () -> OrderStatus_24162016.fromCode(8));
    }

    private static class FakeOrders implements IOrderDao_24162016 {
        int calls;
        boolean fail;

        public String createCodOrder(Order_24162016 order, List<CartItem_24162016> items) {
            calls++;
            if (fail) throw new IllegalStateException("Database unavailable");
            return order.getOrderId();
        }

        public List<Order_24162016> findByUser(int userId, Integer status) {
            return List.of();
        }

        public Order_24162016 findById(String id, int userId) {
            return null;
        }
    }
}

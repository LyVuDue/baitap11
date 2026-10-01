package vn.ute.dao;

import vn.ute.entity.CartItem_24162016;
import vn.ute.entity.Order_24162016;

import java.util.List;

public interface IOrderDao_24162016 {
    String createCodOrder(Order_24162016 order, List<CartItem_24162016> cartItems);

    List<Order_24162016> findByUser(int userId, Integer status);

    Order_24162016 findById(String orderId, int userId);
}

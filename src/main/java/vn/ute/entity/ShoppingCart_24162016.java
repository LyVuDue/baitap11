package vn.ute.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart_24162016 implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int userId;
    private final Map<Integer, CartItem_24162016> items = new LinkedHashMap<>();

    public ShoppingCart_24162016(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public synchronized List<CartItem_24162016> getItems() {
        return new ArrayList<>(items.values());
    }

    public synchronized int quantityOf(int productId) {
        CartItem_24162016 item = items.get(productId);
        return item == null ? 0 : item.getQuantity();
    }

    public synchronized void put(CartItem_24162016 item) {
        items.put(item.getProductId(), item);
    }

    public synchronized void remove(int productId) {
        items.remove(productId);
    }

    public synchronized void clear() {
        items.clear();
    }

    public synchronized boolean isEmpty() {
        return items.isEmpty();
    }

    public synchronized int getItemCount() {
        return items.values().stream().mapToInt(CartItem_24162016::getQuantity).sum();
    }

    public synchronized BigDecimal getTotal() {
        return items.values().stream()
                .map(CartItem_24162016::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

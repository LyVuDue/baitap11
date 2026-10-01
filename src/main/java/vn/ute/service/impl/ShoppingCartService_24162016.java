package vn.ute.service.impl;

import vn.ute.entity.CartItem_24162016;
import vn.ute.entity.Product_24162016;
import vn.ute.entity.ShoppingCart_24162016;
import vn.ute.service.IProductService_24162016;

import java.math.BigDecimal;

public class ShoppingCartService_24162016 {
    private final IProductService_24162016 products;

    public ShoppingCartService_24162016() {
        this(new ProductService_24162016());
    }

    public ShoppingCartService_24162016(IProductService_24162016 products) {
        this.products = products;
    }

    public void add(ShoppingCart_24162016 cart, int productId, int quantity) {
        synchronized (cart) {
            if (quantity <= 0 || quantity > 1000)
                throw new IllegalArgumentException("Số lượng phải từ 1 đến 1.000.");
            setQuantity(cart, productId, Math.addExact(cart.quantityOf(productId), quantity));
        }
    }

    public void setQuantity(ShoppingCart_24162016 cart, int productId, int quantity) {
        if (quantity <= 0 || quantity > 1000)
            throw new IllegalArgumentException("Số lượng phải từ 1 đến 1.000.");
        Product_24162016 p = products.findById(productId);
        if (p == null || p.getStatus() == null || p.getStatus() != 1)
            throw new IllegalArgumentException("Sản phẩm không còn được bán.");
        if (p.getStock() == null || p.getStock() < quantity)
            throw new IllegalArgumentException("Sản phẩm không đủ tồn kho cho số lượng đã chọn.");
        if (p.getPrice() == null || !Double.isFinite(p.getPrice()) || p.getPrice() < 0)
            throw new IllegalArgumentException("Giá sản phẩm không hợp lệ.");
        cart.put(
                new CartItem_24162016(
                        productId,
                        p.getProductName(),
                        p.getImages(),
                        BigDecimal.valueOf(p.getPrice())
                                .setScale(2, java.math.RoundingMode.HALF_UP),
                        quantity));
    }

    public void refresh(ShoppingCart_24162016 cart) {
        synchronized (cart) {
            for (CartItem_24162016 item : cart.getItems()) {
                setQuantity(cart, item.getProductId(), item.getQuantity());
            }
        }
    }
}

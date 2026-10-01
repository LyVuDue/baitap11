package vn.ute.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem_24162016 implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int productId;
    private final String productName;
    private final String images;
    private final BigDecimal unitPrice;
    private final int quantity;

    public CartItem_24162016(
            int productId, String productName, String images, BigDecimal unitPrice, int quantity) {
        if (quantity <= 0 || unitPrice == null || unitPrice.signum() < 0) {
            throw new IllegalArgumentException("Số lượng hoặc giá sản phẩm không hợp lệ.");
        }
        this.productId = productId;
        this.productName = productName;
        this.images = images;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getImages() {
        return images;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}

package vn.ute.entity;

import java.io.Serializable;
import java.sql.Date;

public class Product_24162016 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int productId;
    private String productName;
    private Long productCode;
    private Integer categoryId;
    private String categoryName;   // lấy từ bảng Category (JOIN)
    private String description;
    private Double price;
    private Integer amount;
    private Integer stock;
    private String images;
    private Integer wishlist;
    private Integer status;
    private Date createDate;
    private Integer sellerId;

    public Product_24162016() {
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Long getProductCode() { return productCode; }
    public void setProductCode(Long productCode) { this.productCode = productCode; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public Integer getWishlist() { return wishlist; }
    public void setWishlist(Integer wishlist) { this.wishlist = wishlist; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Integer getSellerId() { return sellerId; }
    public void setSellerId(Integer sellerId) { this.sellerId = sellerId; }
}
package vn.ute.entity;

import java.io.Serializable;

public class Category_24162016 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int categoryId;
    private String categoryName;
    private String images;
    private Integer status;

    public Category_24162016() {
    }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
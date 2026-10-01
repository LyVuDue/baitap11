package vn.ute.dao;

import java.util.List;

import vn.ute.entity.Product_24162016;

public interface IProductDao_24162016 {
    List<Product_24162016> findAll();

    Product_24162016 findById(int productId);
}
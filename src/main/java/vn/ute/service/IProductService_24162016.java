package vn.ute.service;

import java.util.List;
import java.util.Map;

import vn.ute.entity.Product_24162016;

public interface IProductService_24162016 {
    List<Product_24162016> findAll();

    Map<Integer, List<Product_24162016>> groupBySeller();

    Product_24162016 findById(int productId);
}
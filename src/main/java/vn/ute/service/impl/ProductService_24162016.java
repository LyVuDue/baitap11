package vn.ute.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import vn.ute.dao.IProductDao_24162016;
import vn.ute.dao.impl.ProductDaoImpl_24162016;
import vn.ute.entity.Product_24162016;
import vn.ute.service.IProductService_24162016;

public class ProductService_24162016 implements IProductService_24162016 {

    private final IProductDao_24162016 dao = new ProductDaoImpl_24162016();

    @Override
    public List<Product_24162016> findAll() {
        return dao.findAll();
    }

    @Override
    public Map<Integer, List<Product_24162016>> groupBySeller() {
        Map<Integer, List<Product_24162016>> result = new LinkedHashMap<>();
        for (Product_24162016 p : dao.findAll()) {
            result.computeIfAbsent(p.getSellerId(), k -> new ArrayList<>()).add(p);
        }
        return result;
    }

    @Override
    public Product_24162016 findById(int productId) {
        return dao.findById(productId);
    }
}
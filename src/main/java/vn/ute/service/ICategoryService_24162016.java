package vn.ute.service;

import java.util.List;

import vn.ute.entity.Category_24162016;

public interface ICategoryService_24162016 {

    List<Category_24162016> findPage(int page, int size);

    int count();

    int totalPages(int size);

    Category_24162016 findById(int categoryId);

    void save(Category_24162016 c);

    boolean delete(int categoryId);
}
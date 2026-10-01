package vn.ute.dao;

import java.util.List;

import vn.ute.entity.Category_24162016;

public interface ICategoryDao_24162016 {
    List<Category_24162016> findPage(int offset, int limit);

    int count();

    Category_24162016 findById(int categoryId);

    void insert(Category_24162016 c);

    void update(Category_24162016 c);

    boolean delete(int categoryId);
}
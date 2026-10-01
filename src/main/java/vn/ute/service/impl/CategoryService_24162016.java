package vn.ute.service.impl;

import java.util.List;

import vn.ute.dao.ICategoryDao_24162016;
import vn.ute.dao.impl.CategoryDaoImpl_24162016;
import vn.ute.entity.Category_24162016;
import vn.ute.service.ICategoryService_24162016;

public class CategoryService_24162016 implements ICategoryService_24162016 {

    private final ICategoryDao_24162016 dao = new CategoryDaoImpl_24162016();

    @Override
    public List<Category_24162016> findPage(int page, int size) {
        int offset = (Math.max(page, 1) - 1) * size;
        return dao.findPage(offset, size);
    }

    @Override
    public int count() {
        return dao.count();
    }

    @Override
    public int totalPages(int size) {
        int total = dao.count();
        return Math.max(1, (int) Math.ceil(total / (double) size));
    }

    @Override
    public Category_24162016 findById(int categoryId) {
        return dao.findById(categoryId);
    }

    @Override
    public void save(Category_24162016 c) {
        if (c.getCategoryId() == 0) {
            dao.insert(c);
        } else {
            dao.update(c);
        }
    }

    @Override
    public boolean delete(int categoryId) {
        return dao.delete(categoryId);
    }
}
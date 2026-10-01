package vn.ute.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ute.entity.Category_24162016;
import vn.ute.service.ICategoryService_24162016;
import vn.ute.service.impl.CategoryService_24162016;

@WebServlet(urlPatterns = {
        "/admin/categories",
        "/admin/category/add",
        "/admin/category/edit",
        "/admin/category/save",
        "/admin/category/delete" })
public class AdminCategoryController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 5;

    private final ICategoryService_24162016 service = new CategoryService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        int page = toInt(req.getParameter("page"), 1);

        if (path.endsWith("/add")) {
            Category_24162016 c = new Category_24162016();
            c.setStatus(1);
            showForm(req, resp, c, page);
        } else if (path.endsWith("/edit")) {
            Category_24162016 c = service.findById(toInt(req.getParameter("id"), 0));
            if (c == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
                return;
            }
            showForm(req, resp, c, page);
        } else {
            showList(req, resp, page);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        String ctx = req.getContextPath();
        int page = toInt(req.getParameter("page"), 1);

        if (path.endsWith("/delete")) {
            boolean ok = service.delete(toInt(req.getParameter("categoryId"), 0));
            resp.sendRedirect(ctx + "/admin/categories?page=" + page + "&msg=" + (ok ? "deleted" : "inuse"));
            return;
        }

        if (path.endsWith("/save")) {
            Category_24162016 c = new Category_24162016();
            c.setCategoryId(toInt(req.getParameter("categoryId"), 0));
            c.setCategoryName(trim(req.getParameter("categoryName")));
            c.setImages(trim(req.getParameter("images")));
            c.setStatus(toInt(req.getParameter("status"), 1));

            if (c.getCategoryName().isEmpty()) {
                req.setAttribute("error", "Tên danh mục không được để trống");
                showForm(req, resp, c, page);
                return;
            }

            boolean isNew = c.getCategoryId() == 0;
            service.save(c);
            resp.sendRedirect(ctx + "/admin/categories?page=" + (isNew ? 1 : page)
                    + "&msg=" + (isNew ? "added" : "updated"));
            return;
        }

        resp.sendRedirect(ctx + "/admin/categories");
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp, int page)
            throws ServletException, IOException {
        int totalPages = service.totalPages(PAGE_SIZE);
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        req.setAttribute("categories", service.findPage(page, PAGE_SIZE));
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalItems", service.count());
        req.getRequestDispatcher("/WEB-INF/views/admin/category/list.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Category_24162016 c, int page)
            throws ServletException, IOException {
        req.setAttribute("category", c);
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("/WEB-INF/views/admin/category/form.jsp").forward(req, resp);
    }

    private int toInt(String s, int def) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return def;
        }
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
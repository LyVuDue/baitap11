package vn.ute.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ute.entity.Product_24162016;
import vn.ute.service.IProductService_24162016;
import vn.ute.service.impl.ProductService_24162016;

@WebServlet(urlPatterns = { "/product-detail" })
public class ProductDetailController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IProductService_24162016 productService = new ProductService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Product_24162016 product = null;
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            product = productService.findById(id);
        } catch (NumberFormatException e) {
        }

        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        req.setAttribute("product", product);
        req.getRequestDispatcher("/WEB-INF/views/product-detail.jsp").forward(req, resp);
    }
}
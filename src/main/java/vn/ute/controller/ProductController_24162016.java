package vn.ute.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ute.entity.Product_24162016;
import vn.ute.service.IProductService_24162016;
import vn.ute.service.impl.ProductService_24162016;

@WebServlet(urlPatterns = { "/products" })
public class ProductController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IProductService_24162016 productService = new ProductService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<Integer, List<Product_24162016>> productsBySeller = productService.groupBySeller();
        req.setAttribute("productsBySeller", productsBySeller);
        req.getRequestDispatcher("/WEB-INF/views/product-list.jsp").forward(req, resp);
    }
}
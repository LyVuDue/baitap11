package vn.ute.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.ute.util.CommerceWeb_24162016;

import java.io.IOException;
import java.util.Set;

@WebFilter(
        urlPatterns = {
            "/home",
            "/products",
            "/product-detail",
            "/cart",
            "/checkout",
            "/orders",
            "/order-detail"
        })
public class CommerceFilter_24162016 extends HttpFilter {
    private static final long serialVersionUID = 1L;
    private static final Set<String> PROTECTED =
            Set.of("/cart", "/checkout", "/orders", "/order-detail");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        if (PROTECTED.contains(req.getServletPath()) && CommerceWeb_24162016.account(req) == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        CommerceWeb_24162016.ensureCsrf(req.getSession());
        if (CommerceWeb_24162016.account(req) != null) CommerceWeb_24162016.cart(req);
        chain.doFilter(req, resp);
    }
}

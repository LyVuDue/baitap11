package vn.ute.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.ute.entity.ShoppingCart_24162016;
import vn.ute.service.impl.ShoppingCartService_24162016;
import vn.ute.util.CommerceWeb_24162016;

import java.io.IOException;

@WebServlet("/cart")
public class CartController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ShoppingCartService_24162016 service = new ShoppingCartService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart_24162016 cart = CommerceWeb_24162016.cart(req);
        CommerceWeb_24162016.readFlash(req);
        boolean ready = true;
        try {
            service.refresh(cart);
        } catch (IllegalArgumentException e) {
            req.setAttribute("cartError", e.getMessage());
            ready = false;
        } catch (RuntimeException e) {
            getServletContext().log("Không thể cập nhật giỏ hàng", e);
            req.setAttribute("cartError", "Không thể kiểm tra tồn kho. Hãy thử lại.");
            ready = false;
        }
        req.setAttribute("cart", cart);
        req.setAttribute("canCheckout", ready && !cart.isEmpty());
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!CommerceWeb_24162016.validCsrf(req)) {
            resp.sendError(403, "Phiên thao tác không hợp lệ. Hãy tải lại trang.");
            return;
        }
        synchronized (req.getSession()) {
            ShoppingCart_24162016 cart = CommerceWeb_24162016.cart(req);
            String action = req.getParameter("action");
            try {
                if ("clear".equals(action)) {
                    cart.clear();
                    CommerceWeb_24162016.flash(req, "success", "Đã xóa toàn bộ giỏ hàng.");
                } else {
                    int id = Integer.parseInt(req.getParameter("productId"));
                    if (id <= 0) throw new IllegalArgumentException("Mã sản phẩm không hợp lệ.");
                    if ("remove".equals(action)) {
                        cart.remove(id);
                        CommerceWeb_24162016.flash(
                                req, "success", "Đã xóa sản phẩm khỏi giỏ hàng.");
                    } else if ("add".equals(action)) {
                        service.add(cart, id, Integer.parseInt(req.getParameter("quantity")));
                        CommerceWeb_24162016.flash(
                                req, "success", "Đã thêm sản phẩm vào giỏ hàng.");
                    } else if ("update".equals(action)) {
                        if (cart.quantityOf(id) == 0)
                            throw new IllegalArgumentException("Sản phẩm không có trong giỏ hàng.");
                        service.setQuantity(
                                cart, id, Integer.parseInt(req.getParameter("quantity")));
                        CommerceWeb_24162016.flash(req, "success", "Đã cập nhật số lượng.");
                    } else throw new IllegalArgumentException("Thao tác giỏ hàng không hợp lệ.");
                }
                req.getSession().removeAttribute("checkoutToken");
            } catch (NumberFormatException e) {
                CommerceWeb_24162016.flash(
                        req, "danger", "Mã sản phẩm và số lượng phải là số nguyên hợp lệ.");
            } catch (IllegalArgumentException e) {
                CommerceWeb_24162016.flash(req, "danger", e.getMessage());
            } catch (RuntimeException e) {
                getServletContext().log("Lỗi cập nhật giỏ hàng", e);
                CommerceWeb_24162016.flash(
                        req, "danger", "Không thể cập nhật giỏ hàng. Hãy thử lại.");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}

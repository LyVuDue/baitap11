package vn.ute.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.ute.entity.*;
import vn.ute.service.impl.OrderService_24162016;
import vn.ute.service.impl.ShoppingCartService_24162016;
import vn.ute.util.CommerceWeb_24162016;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/checkout")
public class CheckoutController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OrderService_24162016 service = new OrderService_24162016();
    private final ShoppingCartService_24162016 carts = new ShoppingCartService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart_24162016 cart = CommerceWeb_24162016.cart(req);
        if (cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        try {
            carts.refresh(cart);
        } catch (IllegalArgumentException e) {
            CommerceWeb_24162016.flash(req, "danger", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        } catch (RuntimeException e) {
            getServletContext().log("Lỗi kiểm tra giỏ trước đặt hàng", e);
            CommerceWeb_24162016.flash(req, "danger", "Không thể kiểm tra giỏ hàng. Hãy thử lại.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        synchronized (req.getSession()) {
            if (req.getSession().getAttribute("checkoutToken") == null)
                req.getSession().setAttribute("checkoutToken", UUID.randomUUID().toString());
        }
        User_24162016 user = CommerceWeb_24162016.account(req);
        Order_24162016 form = new Order_24162016();
        form.setRecipientName(user.getFullname());
        form.setPhone(user.getPhone());
        render(req, resp, cart, form);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!CommerceWeb_24162016.validCsrf(req)) {
            resp.sendError(403);
            return;
        }
        synchronized (req.getSession()) {
            HttpSession session = req.getSession();
            String token = req.getParameter("checkoutToken");
            if (token != null && token.equals(session.getAttribute("lastCheckoutToken"))) {
                resp.sendRedirect(
                        req.getContextPath()
                                + "/order-detail?id="
                                + session.getAttribute("lastOrderId"));
                return;
            }
            if (token == null || !token.equals(session.getAttribute("checkoutToken"))) {
                CommerceWeb_24162016.flash(
                        req,
                        "danger",
                        "Giỏ hàng hoặc phiên đặt hàng đã thay đổi. Hãy kiểm tra lại.");
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            ShoppingCart_24162016 cart = CommerceWeb_24162016.cart(req);
            if (cart.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            Order_24162016 form = new Order_24162016();
            form.setOrderId("OD-" + token);
            form.setRecipientName(req.getParameter("recipientName"));
            form.setPhone(req.getParameter("phone"));
            form.setAddress(req.getParameter("address"));
            form.setNote(req.getParameter("note"));
            try {
                String id = service.checkout(cart, form);
                session.removeAttribute("checkoutToken");
                session.setAttribute("lastCheckoutToken", token);
                session.setAttribute("lastOrderId", id);
                CommerceWeb_24162016.flash(
                        req,
                        "success",
                        "Đặt hàng thành công. Anh/chị thanh toán khi nhận hàng (COD).");
                resp.sendRedirect(req.getContextPath() + "/order-detail?id=" + id);
            } catch (IllegalArgumentException e) {
                req.setAttribute("error", e.getMessage());
                render(req, resp, cart, form);
            } catch (RuntimeException e) {
                getServletContext().log("Không thể tạo đơn COD", e);
                req.setAttribute(
                        "error",
                        "Không thể lưu đơn hàng lúc này. Giỏ hàng được giữ lại để bạn thử lại.");
                render(req, resp, cart, form);
            }
        }
    }

    private void render(
            HttpServletRequest req,
            HttpServletResponse resp,
            ShoppingCart_24162016 cart,
            Order_24162016 form)
            throws ServletException, IOException {
        req.setAttribute("cart", cart);
        req.setAttribute("form", form);
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }
}

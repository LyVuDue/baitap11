package vn.ute.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import vn.ute.entity.ShoppingCart_24162016;
import vn.ute.entity.User_24162016;

import java.util.UUID;

public final class CommerceWeb_24162016 {
    private CommerceWeb_24162016() {}

    public static User_24162016 account(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (User_24162016) session.getAttribute("account");
    }

    public static ShoppingCart_24162016 cart(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User_24162016 user = account(req);
        if (user == null)
            throw new IllegalStateException("Cần đăng nhập trước khi sử dụng giỏ hàng.");
        synchronized (session) {
            ShoppingCart_24162016 cart =
                    (ShoppingCart_24162016) session.getAttribute("shoppingCart");
            if (cart == null || cart.getUserId() != user.getUserId()) {
                cart = new ShoppingCart_24162016(user.getUserId());
                session.setAttribute("shoppingCart", cart);
            }
            return cart;
        }
    }

    public static void ensureCsrf(HttpSession session) {
        synchronized (session) {
            if (session.getAttribute("csrfToken") == null)
                session.setAttribute("csrfToken", UUID.randomUUID().toString());
        }
    }

    public static boolean validCsrf(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null
                && session.getAttribute("csrfToken") != null
                && session.getAttribute("csrfToken").equals(req.getParameter("csrfToken"));
    }

    public static void flash(HttpServletRequest req, String type, String text) {
        req.getSession().setAttribute("commerceFlashType", type);
        req.getSession().setAttribute("commerceFlash", text);
    }

    public static void readFlash(HttpServletRequest req) {
        HttpSession s = req.getSession();
        req.setAttribute("flash", s.getAttribute("commerceFlash"));
        req.setAttribute("flashType", s.getAttribute("commerceFlashType"));
        s.removeAttribute("commerceFlash");
        s.removeAttribute("commerceFlashType");
    }
}

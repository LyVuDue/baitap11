package vn.ute.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.ute.entity.User_24162016;

@WebServlet(urlPatterns = { "/seller/home" })
public class SellerHomeController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User_24162016 acc = session == null ? null : (User_24162016) session.getAttribute("account");
        if (acc == null || acc.getRoleId() == null || acc.getRoleId() != 3) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/seller/home.jsp").forward(req, resp);
    }
}
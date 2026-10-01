package vn.ute.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.ute.service.IUserService_24162016;
import vn.ute.service.impl.UserService_24162016;

@WebServlet(urlPatterns = { "/verify" })
public class VerifyController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24162016 userService = new UserService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("verifyEmail") == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("verifyEmail") == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        String email = (String) session.getAttribute("verifyEmail");
        String otp = req.getParameter("otp");

        if (userService.verifyOtp(email, otp == null ? "" : otp.trim())) {
            session.removeAttribute("verifyEmail");
            resp.sendRedirect(req.getContextPath() + "/login?msg=verified");
        } else {
            req.setAttribute("error", "Mã OTP không đúng");
            req.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(req, resp);
        }
    }
}
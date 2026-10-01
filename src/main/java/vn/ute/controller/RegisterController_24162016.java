package vn.ute.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.ute.entity.User_24162016;
import vn.ute.service.IUserService_24162016;
import vn.ute.service.impl.UserService_24162016;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24162016 userService = new UserService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = trim(req.getParameter("username"));
        String password = trim(req.getParameter("password"));
        String fullname = trim(req.getParameter("fullname"));
        String email = trim(req.getParameter("email"));
        String phone = trim(req.getParameter("phone"));

        String error = null;
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
            error = "Vui lòng nhập đầy đủ thông tin bắt buộc";
        } else {
            User_24162016 u = new User_24162016();
            u.setUsername(username);
            u.setPassword(password);
            u.setFullname(fullname);
            u.setEmail(email);
            u.setPhone(phone);
            u.setImages("");
            error = userService.register(u);
        }

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("username", username);
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("verifyEmail", email);
        resp.sendRedirect(req.getContextPath() + "/verify");
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
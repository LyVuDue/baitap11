package vn.ute.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.ute.entity.User_24162016;
import vn.ute.service.IUserService_24162016;
import vn.ute.service.impl.UserService_24162016;

@WebServlet(urlPatterns = { "/login" })
public class LoginController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24162016 userService = new UserService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            redirectByRole(req, resp, (User_24162016) session.getAttribute("account"));
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User_24162016 user = userService.login(username == null ? "" : username.trim(), password);

        if (user == null) {
            req.setAttribute("error", "Sai tên đăng nhập, mật khẩu hoặc tài khoản chưa kích hoạt");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("account", user);
        redirectByRole(req, resp, user);
    }

    private void redirectByRole(HttpServletRequest req, HttpServletResponse resp, User_24162016 user)
            throws IOException {
        String ctx = req.getContextPath();
        Integer role = user.getRoleId();
        if (role != null && role == 1) {
            resp.sendRedirect(ctx + "/admin/home");
        } else if (role != null && role == 3) {
            resp.sendRedirect(ctx + "/seller/home");
        } else {
            resp.sendRedirect(ctx + "/home");
        }
    }
}
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

@WebServlet(urlPatterns = {
        "/admin/users",
        "/admin/user/add",
        "/admin/user/edit",
        "/admin/user/save",
        "/admin/user/delete" })
public class AdminUserController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 5;

    private final IUserService_24162016 service = new UserService_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        int page = toInt(req.getParameter("page"), 1);

        if (path.endsWith("/add")) {
            User_24162016 u = new User_24162016();
            u.setStatus(1);
            u.setRoleId(2);
            showForm(req, resp, u, page);
        } else if (path.endsWith("/edit")) {
            User_24162016 u = service.findById(toInt(req.getParameter("id"), 0));
            if (u == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                return;
            }
            u.setPassword(""); 
            showForm(req, resp, u, page);
        } else {
            showList(req, resp, page);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        String ctx = req.getContextPath();
        int page = toInt(req.getParameter("page"), 1);

        if (path.endsWith("/delete")) {
            int id = toInt(req.getParameter("userId"), 0);

            HttpSession session = req.getSession(false);
            User_24162016 acc = session == null ? null : (User_24162016) session.getAttribute("account");
            String msg;
            if (acc != null && acc.getUserId() == id) {
                msg = "self";     
            } else {
                msg = service.delete(id) ? "deleted" : "inuse";
            }
            resp.sendRedirect(ctx + "/admin/users?page=" + page + "&msg=" + msg);
            return;
        }

        if (path.endsWith("/save")) {
            User_24162016 u = new User_24162016();
            u.setUserId(toInt(req.getParameter("userId"), 0));
            u.setUsername(trim(req.getParameter("username")));
            u.setPassword(trim(req.getParameter("password")));
            u.setFullname(trim(req.getParameter("fullname")));
            u.setEmail(trim(req.getParameter("email")));
            u.setPhone(trim(req.getParameter("phone")));
            u.setImages(trim(req.getParameter("images")));
            u.setStatus(toInt(req.getParameter("status"), 1));
            u.setRoleId(toInteger(req.getParameter("roleId")));
            u.setSellerId(toInteger(req.getParameter("sellerId")));

            String error = null;
            if (u.getUsername().isEmpty() || u.getFullname().isEmpty() || u.getEmail().isEmpty()) {
                error = "Vui lòng nhập đủ Tên đăng nhập, Họ tên và Email";
            } else {
                error = service.adminSave(u);
            }

            if (error != null) {
                req.setAttribute("error", error);
                showForm(req, resp, u, page);
                return;
            }

            boolean isNew = toInt(req.getParameter("userId"), 0) == 0;
            resp.sendRedirect(ctx + "/admin/users?page=" + (isNew ? 1 : page)
                    + "&msg=" + (isNew ? "added" : "updated"));
            return;
        }

        resp.sendRedirect(ctx + "/admin/users");
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp, int page)
            throws ServletException, IOException {
        int totalPages = service.totalPages(PAGE_SIZE);
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        req.setAttribute("users", service.findPage(page, PAGE_SIZE));
        req.setAttribute("roles", service.findRoles());
        req.setAttribute("sellers", service.findSellers());
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalItems", service.count());
        req.getRequestDispatcher("/WEB-INF/views/admin/user/list.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, User_24162016 u, int page)
            throws ServletException, IOException {
        req.setAttribute("editUser", u);
        req.setAttribute("roles", service.findRoles());
        req.setAttribute("sellers", service.findSellers());
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("/WEB-INF/views/admin/user/form.jsp").forward(req, resp);
    }

    private int toInt(String s, int def) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return def;
        }
    }

    private Integer toInteger(String s) {
        try {
            return Integer.valueOf(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
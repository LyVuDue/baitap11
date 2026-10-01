package vn.ute.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.ute.entity.User_24162016;

@WebFilter(urlPatterns = { "/admin/*" })
public class AdminFilter_24162016 extends HttpFilter {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        User_24162016 acc = session == null ? null : (User_24162016) session.getAttribute("account");

        if (acc == null || acc.getRoleId() == null || acc.getRoleId() != 1) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        chain.doFilter(req, resp);
    }
}
package vn.ute.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.ute.dao.IOrderDao_24162016;
import vn.ute.dao.impl.OrderDaoImpl_24162016;
import vn.ute.entity.OrderStatus_24162016;
import vn.ute.entity.Order_24162016;
import vn.ute.util.CommerceWeb_24162016;

import java.io.IOException;

@WebServlet(urlPatterns = {"/orders", "/order-detail"})
public class OrderController_24162016 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IOrderDao_24162016 orders = new OrderDaoImpl_24162016();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-store");
        int userId = CommerceWeb_24162016.account(req).getUserId();
        CommerceWeb_24162016.readFlash(req);
        try {
            if ("/order-detail".equals(req.getServletPath())) {
                Order_24162016 order = orders.findById(req.getParameter("id"), userId);
                if (order == null) {
                    resp.sendError(404, "Không tìm thấy đơn hàng.");
                    return;
                }
                req.setAttribute("order", order);
                req.getRequestDispatcher("/WEB-INF/views/order-detail.jsp").forward(req, resp);
            } else {
                Integer status = null;
                String raw = req.getParameter("status");
                if (raw != null && !raw.isBlank()) {
                    try {
                        status = Integer.valueOf(raw);
                        OrderStatus_24162016.fromCode(status);
                    } catch (IllegalArgumentException e) {
                        resp.sendError(400, "Trạng thái không hợp lệ.");
                        return;
                    }
                }
                req.setAttribute("orders", orders.findByUser(userId, status));
                req.setAttribute("statuses", OrderStatus_24162016.values());
                req.setAttribute("selectedStatus", status);
                req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
            }
        } catch (RuntimeException e) {
            getServletContext().log("Lỗi đọc đơn hàng", e);
            resp.sendError(503, "Không thể đọc đơn hàng lúc này. Hãy thử lại.");
        }
    }
}

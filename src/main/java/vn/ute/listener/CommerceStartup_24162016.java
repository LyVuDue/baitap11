package vn.ute.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import vn.ute.util.CommerceSchema_24162016;

@WebListener
public class CommerceStartup_24162016 implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            CommerceSchema_24162016.migrate();
            event.getServletContext().log("Đã chuẩn bị cấu trúc đơn hàng COD.");
        } catch (java.sql.SQLException e) {
            throw new IllegalStateException(
                    "Không thể chuẩn bị database đơn hàng. Kiểm tra kết nối MySQL và quyền thêm"
                        + " cột.",
                    e);
        }
    }
}

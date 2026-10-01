package vn.ute.entity;

public enum OrderStatus_24162016 {
    NEW(0, "Đơn hàng mới", "primary"),
    CONFIRMED(1, "Đã xác nhận", "info"),
    PREPARING(2, "Chuẩn bị hàng", "warning"),
    SHIPPING(3, "Vận chuyển", "info"),
    DELIVERING(4, "Giao hàng", "warning"),
    DELIVERED(5, "Đã giao", "success"),
    CANCELLED(6, "Đơn hàng hủy", "danger"),
    RETURNED(7, "Đơn hàng hoàn", "secondary");

    private final int code;
    private final String label;
    private final String color;

    OrderStatus_24162016(int code, String label, String color) {
        this.code = code;
        this.label = label;
        this.color = color;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public String getColor() {
        return color;
    }

    public static OrderStatus_24162016 fromCode(int code) {
        for (OrderStatus_24162016 status : values()) if (status.code == code) return status;
        throw new IllegalArgumentException("Trạng thái đơn hàng không hợp lệ.");
    }
}

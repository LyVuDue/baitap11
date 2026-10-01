package vn.ute.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order_24162016 {
    private String orderId;
    private int userId;
    private Timestamp buyDate;
    private int status;
    private String recipientName;
    private String phone;
    private String address;
    private String note;
    private String paymentMethod;
    private BigDecimal totalAmount;
    private List<CartItem_24162016> items = new ArrayList<>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Timestamp buyDate) {
        this.buyDate = buyDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusLabel() {
        try {
            return OrderStatus_24162016.fromCode(status).getLabel();
        } catch (IllegalArgumentException e) {
            return "Trạng thái chưa xác định";
        }
    }

    public String getStatusColor() {
        try {
            return OrderStatus_24162016.fromCode(status).getColor();
        } catch (IllegalArgumentException e) {
            return "secondary";
        }
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<CartItem_24162016> getItems() {
        return items;
    }

    public void setItems(List<CartItem_24162016> items) {
        this.items = items;
    }
}

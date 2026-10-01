<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<html>
    <head>
        <title>
            Chi tiết đơn hàng
        </title>
    </head>
    <body>
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
        <div class="d-flex flex-wrap justify-content-between gap-3 align-items-center mb-4">
            <h3 class="mb-0">
                Chi tiết đơn hàng
            </h3>
            <a class="btn btn-outline-primary" href="${ctx}/orders">
                <i class="bi bi-arrow-left">
                </i>
                Lịch sử đặt hàng
            </a>
        </div>
        <c:if test="${not empty flash}">
            <div class="alert alert-${flashType}" role="alert">
                <c:out value="${flash}"/>
            </div>
        </c:if>
        <div class="commerce-panel mb-4">
            <div class="d-flex flex-wrap justify-content-between gap-3 align-items-center mb-3">
                <span class="commerce-order-id fw-semibold">
                    <c:out value="${order.orderId}"/>
                </span>
                <span class="badge text-bg-${order.statusColor} fs-6">
                    <c:out value="${order.statusLabel}"/>
                </span>
            </div>
            <div class="text-muted mb-3">
                Đặt lúc
                <fmt:formatDate value="${order.buyDate}" pattern="dd/MM/yyyy HH:mm" timeZone="Asia/Ho_Chi_Minh"/>
            </div>
            <div class="row g-4">
                <div class="col-md-7">
                    <h6>
                        Thông tin người nhận
                    </h6>
                    <div>
                        <c:out value="${order.recipientName}"/>
                    </div>
                    <div>
                        <c:out value="${order.phone}"/>
                    </div>
                    <div class="commerce-address">
                        <c:out value="${order.address}"/>
                    </div>
                    <c:if test="${not empty order.note}">
                        <div class="mt-2 text-muted">
                            Ghi chú:
                            <c:out value="${order.note}"/>
                        </div>
                    </c:if>
                </div>
                <div class="col-md-5">
                    <h6>
                        Phương thức thanh toán
                    </h6>
                    <div>
                        <i class="bi bi-cash-coin text-primary">
                        </i>
                        Thanh toán khi nhận hàng (COD)
                    </div>
                </div>
            </div>
        </div>
        <div class="commerce-panel">
            <div class="table-responsive">
                <table class="table commerce-items-table align-middle">
                    <thead>
                        <tr>
                            <th>
                                Sản phẩm
                            </th>
                            <th>
                                Đơn giá lúc đặt
                            </th>
                            <th>
                                Số lượng
                            </th>
                            <th>
                                Thành tiền
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${order.items}">
                            <tr>
                                <td>
                                    <div class="d-flex align-items-center gap-3">
                                        <img class="commerce-thumbnail" src="<c:out value='${item.images}'/>" alt="">
                                        <c:out value="${item.productName}"/>
                                    </div>
                                </td>
                                <td class="text-nowrap">
                                    <fmt:formatNumber value="${item.unitPrice}" maxFractionDigits="0"/>
                                    đ
                                </td>
                                <td>
                                    ${item.quantity}
                                </td>
                                <td class="text-nowrap">
                                    <fmt:formatNumber value="${item.subtotal}" maxFractionDigits="0"/>
                                    đ
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="text-end fw-bold fs-5">
                Tổng thanh toán:
                <span class="text-primary">
                    <fmt:formatNumber value="${order.totalAmount}" maxFractionDigits="0"/>
                    đ
                </span>
            </div>
        </div>
    </body>
</html>

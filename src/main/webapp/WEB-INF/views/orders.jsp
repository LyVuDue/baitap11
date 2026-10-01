<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<html>
    <head>
        <title>
            Lịch sử đặt hàng
        </title>
    </head>
    <body>
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
        <h3 class="mb-4">
            <i class="bi bi-clock-history text-primary">
            </i>
            Lịch sử đặt hàng
        </h3>
        <c:if test="${not empty flash}">
            <div class="alert alert-${flashType}" role="alert">
                <c:out value="${flash}"/>
            </div>
        </c:if>
        <div class="d-flex flex-wrap gap-2 mb-4" aria-label="Lọc trạng thái đơn hàng">
            <a class="btn btn-sm ${selectedStatus == null ? 'btn-primary' : 'btn-outline-primary'}" href="${ctx}/orders">
                Tất cả
            </a>
            <c:forEach var="state" items="${statuses}">
                <a class="btn btn-sm ${selectedStatus != null && selectedStatus == state.code ? 'btn-primary' : 'btn-outline-primary'}" href="${ctx}/orders?status=${state.code}">
                    <c:out value="${state.label}"/>
                </a>
            </c:forEach>
        </div>
        <c:choose>
            <c:when test="${empty orders}">
                <div class="commerce-panel text-center py-5">
                    <i class="bi bi-receipt fs-1 text-muted">
                    </i>
                    <h5 class="mt-3">
                        Chưa có đơn hàng trong mục này
                    </h5>
                    <p class="text-muted">
                        Đổi trạng thái lọc hoặc chọn sản phẩm để đặt đơn đầu tiên.
                    </p>
                    <a class="btn btn-primary" href="${ctx}/products">
                        Mua sắm ngay
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="commerce-panel">
                    <div class="table-responsive">
                        <table class="table commerce-orders-table align-middle mb-0">
                            <thead>
                                <tr>
                                    <th>
                                        Mã đơn hàng
                                    </th>
                                    <th>
                                        Ngày đặt
                                    </th>
                                    <th>
                                        Thanh toán
                                    </th>
                                    <th>
                                        Tổng tiền
                                    </th>
                                    <th>
                                        Trạng thái
                                    </th>
                                    <th>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="order" items="${orders}">
                                    <tr>
                                        <td>
                                            <span class="commerce-order-id">
                                                <c:out value="${order.orderId}"/>
                                            </span>
                                        </td>
                                        <td class="text-nowrap">
                                            <fmt:formatDate value="${order.buyDate}" pattern="dd/MM/yyyy HH:mm" timeZone="Asia/Ho_Chi_Minh"/>
                                        </td>
                                        <td>
                                            COD
                                        </td>
                                        <td class="text-nowrap fw-semibold">
                                            <fmt:formatNumber value="${order.totalAmount}" maxFractionDigits="0"/>
                                            đ
                                        </td>
                                        <td>
                                            <span class="badge text-bg-${order.statusColor}">
                                                <c:out value="${order.statusLabel}"/>
                                            </span>
                                        </td>
                                        <td>
                                            <a class="btn btn-outline-primary btn-sm text-nowrap" href="${ctx}/order-detail?id=${order.orderId}">
                                                Chi tiết
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>

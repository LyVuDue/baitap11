<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<html>
    <head>
        <title>
            Thanh toán COD
        </title>
    </head>
    <body>
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
        <h3 class="mb-4">
            <i class="bi bi-truck text-primary">
            </i>
            Đặt hàng và thanh toán COD
        </h3>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${error}"/>
            </div>
        </c:if>
        <div class="row g-4">
            <div class="col-lg-7">
                <div class="commerce-panel">
                    <h5 class="mb-4">
                        Thông tin giao hàng
                    </h5>
                    <form method="post" action="${ctx}/checkout">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                        <input type="hidden" name="checkoutToken" value="${sessionScope.checkoutToken}">
                        <div class="mb-3">
                            <label class="form-label" for="recipientName">
                                Tên người nhận
                            </label>
                            <input class="form-control" id="recipientName" name="recipientName" value="<c:out value='${form.recipientName}'/>" autocomplete="name" minlength="2" maxlength="100" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="phone">
                                Số điện thoại
                            </label>
                            <input class="form-control" id="phone" name="phone" type="tel" value="<c:out value='${form.phone}'/>" autocomplete="tel" maxlength="20" placeholder="Ví dụ: 0901234567" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="address">
                                Địa chỉ giao hàng
                            </label>
                            <textarea class="form-control" id="address" name="address" rows="3" autocomplete="street-address" minlength="10" maxlength="500" placeholder="Số nhà, đường, phường/xã, tỉnh/thành phố" required><c:out value="${form.address}"/></textarea>
                        </div>
                        <div class="mb-4">
                            <label class="form-label" for="note">
                                Ghi chú
                                <span class="text-muted">
                                    (không bắt buộc)
                                </span>
                            </label>
                            <textarea class="form-control" id="note" name="note" rows="2" maxlength="500"><c:out value="${form.note}"/></textarea>
                        </div>
                        <div class="alert alert-info">
                            <strong>
                                <i class="bi bi-cash-coin">
                                </i>
                                Thanh toán khi nhận hàng (COD)
                            </strong>
                            <div class="mt-1">
                                Bạn thanh toán cho nhân viên giao hàng sau khi nhận sản phẩm.
                            </div>
                        </div>
                        <div class="d-flex flex-wrap gap-2 mt-4">
                            <button class="btn btn-primary px-4" type="submit">
                                Xác nhận đặt hàng COD
                            </button>
                            <a class="btn btn-outline-secondary" href="${ctx}/cart">
                                Quay lại giỏ hàng
                            </a>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-lg-5">
                <div class="commerce-panel">
                    <h5 class="mb-4">
                        Đơn hàng của bạn
                    </h5>
                    <c:forEach var="item" items="${cart.items}">
                        <div class="d-flex justify-content-between gap-3 border-bottom pb-3 mb-3">
                            <div>
                                <div class="fw-semibold">
                                    <c:out value="${item.productName}"/>
                                </div>
                                <small class="text-muted">
                                    Số lượng: ${item.quantity}
                                </small>
                            </div>
                            <span class="text-nowrap">
                                <fmt:formatNumber value="${item.subtotal}" maxFractionDigits="0"/>
                                đ
                            </span>
                        </div>
                    </c:forEach>
                    <div class="d-flex justify-content-between mb-2">
                        <span>
                            Phí vận chuyển
                        </span>
                        <span>
                            Miễn phí
                        </span>
                    </div>
                    <div class="d-flex justify-content-between fw-bold fs-5 mt-3">
                        <span>
                            Tổng thanh toán
                        </span>
                        <span class="text-primary text-nowrap">
                            <fmt:formatNumber value="${cart.total}" maxFractionDigits="0"/>
                            đ
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

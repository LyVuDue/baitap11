<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<html>
    <head>
        <title>
            Giỏ hàng
        </title>
    </head>
    <body>
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
        <div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
            <h3 class="mb-0">
                <i class="bi bi-cart3 text-primary">
                </i>
                Giỏ hàng của bạn
            </h3>
            <a href="${ctx}/products" class="btn btn-outline-primary">
                <i class="bi bi-arrow-left">
                </i>
                Tiếp tục mua sắm
            </a>
        </div>
        <c:if test="${not empty flash}">
            <div class="alert alert-${flashType}" role="alert">
                <c:out value="${flash}"/>
            </div>
        </c:if>
        <c:if test="${not empty cartError}">
            <div class="alert alert-warning" role="alert">
                <c:out value="${cartError}"/>
                Bạn có thể giảm số lượng hoặc xóa sản phẩm để tiếp tục.
            </div>
        </c:if>
        <c:choose>
            <c:when test="${empty cart.items}">
                <div class="commerce-panel text-center py-5">
                    <i class="bi bi-cart-x fs-1 text-muted">
                    </i>
                    <h4 class="mt-3">
                        Giỏ hàng đang trống
                    </h4>
                    <p class="text-muted">
                        Chọn sản phẩm bạn muốn mua để bắt đầu.
                    </p>
                    <a class="btn btn-primary" href="${ctx}/products">
                        Xem sản phẩm
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="commerce-panel mb-4">
                    <div class="table-responsive">
                        <table class="table commerce-items-table align-middle mb-0">
                            <thead>
                                <tr>
                                    <th>
                                        Sản phẩm
                                    </th>
                                    <th>
                                        Đơn giá
                                    </th>
                                    <th style="min-width:210px">
                                        Số lượng
                                    </th>
                                    <th>
                                        Thành tiền
                                    </th>
                                    <th>
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${cart.items}">
                                    <tr>
                                        <td>
                                            <div class="d-flex align-items-center gap-3">
                                                <img class="commerce-thumbnail" src="<c:out value='${item.images}'/>" alt="">
                                                <a class="fw-semibold" href="${ctx}/product-detail?id=${item.productId}">
                                                    <c:out value="${item.productName}"/>
                                                </a>
                                            </div>
                                        </td>
                                        <td class="text-nowrap">
                                            <fmt:formatNumber value="${item.unitPrice}" maxFractionDigits="0"/>
                                            đ
                                        </td>
                                        <td>
                                            <form method="post" action="${ctx}/cart" class="d-flex gap-2 align-items-center">
                                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                                <input type="hidden" name="action" value="update">
                                                <input type="hidden" name="productId" value="${item.productId}">
                                                <label class="visually-hidden" for="quantity-${item.productId}">
                                                    Số lượng
                                                </label>
                                                <input class="form-control commerce-quantity" type="number" id="quantity-${item.productId}" name="quantity" value="${item.quantity}" min="1" max="1000" required>
                                                <button class="btn btn-outline-primary btn-sm" type="submit">
                                                    Cập nhật
                                                </button>
                                            </form>
                                        </td>
                                        <td class="fw-semibold text-nowrap">
                                            <fmt:formatNumber value="${item.subtotal}" maxFractionDigits="0"/>
                                            đ
                                        </td>
                                        <td>
                                            <form method="post" action="${ctx}/cart">
                                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                                <input type="hidden" name="action" value="remove">
                                                <input type="hidden" name="productId" value="${item.productId}">
                                                <button class="btn btn-outline-danger btn-sm" type="submit" aria-label="Xóa sản phẩm">
                                                    <i class="bi bi-trash">
                                                    </i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="d-flex flex-wrap justify-content-between align-items-center gap-4">
                    <form action="${ctx}/cart" method="post">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                        <input type="hidden" name="action" value="clear">
                        <button class="btn btn-outline-danger" type="submit">
                            Xóa toàn bộ giỏ hàng
                        </button>
                    </form>
                    <div class="text-end">
                        <p class="mb-2 text-muted">
                            ${cart.itemCount} sản phẩm
                        </p>
                        <h4 class="mb-3">
                            Tổng cộng:
                            <span class="text-primary">
                                <fmt:formatNumber value="${cart.total}" maxFractionDigits="0"/>
                                đ
                            </span>
                        </h4>
                        <c:choose>
                            <c:when test="${canCheckout}">
                                <a class="btn btn-primary px-4" href="${ctx}/checkout">
                                    Tiến hành đặt hàng
                                    <i class="bi bi-arrow-right">
                                    </i>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-secondary" disabled>
                                    Kiểm tra lại giỏ hàng
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>

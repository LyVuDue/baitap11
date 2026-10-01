<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<html>
<head><title>Sản phẩm</title></head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h3 class="mb-4"><i class="bi bi-grid text-primary"></i> Danh sách sản phẩm theo cửa hàng</h3>

<c:if test="${empty productsBySeller}">
    <div class="alert alert-info">Chưa có sản phẩm nào.</div>
</c:if>

<c:forEach var="entry" items="${productsBySeller}">
    <section class="mb-5">
        <div class="seller-header">
            <i class="bi bi-shop text-primary"></i>
            <span>Mã cửa hàng: ${entry.key}</span>
            <span class="count">${entry.value.size()} sản phẩm</span>
        </div>

        <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 g-4">
            <c:forEach var="p" items="${entry.value}">
                <div class="col">
                    <div class="card product-card h-100">
                        <img src="${p.images}" alt="${p.productName}" class="product-img">
                        <div class="card-body">
                            <div class="mb-2">
                                <span class="label">Tên sản phẩm:</span>
                                <a class="product-name" href="${ctx}/product-detail?id=${p.productId}">
                                    <c:out value="${p.productName}"/></a>
                            </div>
                            <div class="meta"><span class="label">Mã sản phẩm:</span> ${p.productCode}</div>
                            <div class="meta"><span class="label">Danh mục:</span>
                                <span class="badge badge-cat"><c:out value="${p.categoryName}"/></span></div>
                            <div class="meta"><span class="label">Giá:</span>
                                <span class="price"><fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/> VNĐ</span></div>
                            <div class="meta"><span class="label">Amount:</span> ${p.amount}</div>
                        </div>
                        <div class="card-footer bg-white border-0 pb-3">
                            <a class="btn btn-primary btn-sm w-100" href="${ctx}/product-detail?id=${p.productId}">
                                <i class="bi bi-eye"></i> Xem chi tiết</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
</c:forEach>
</body>
</html>
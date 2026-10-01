<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<html>
<head><title>Chi tiết sản phẩm</title></head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${ctx}/home">Trang chủ</a></li>
        <li class="breadcrumb-item"><a href="${ctx}/products">Sản phẩm</a></li>
        <li class="breadcrumb-item active"><c:out value="${product.productName}"/></li>
    </ol>
</nav>

<div class="detail-card">
    <div class="row g-4">
        <div class="col-md-5">
            <img src="${product.images}" alt="${product.productName}" class="detail-img">
        </div>
        <div class="col-md-7">
            <h3 class="mb-1"><c:out value="${product.productName}"/></h3>
            <div class="detail-price mb-3">
                <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/> VNĐ
            </div>

            <div class="info-row"><span class="k">Tên sản phẩm</span><span><c:out value="${product.productName}"/></span></div>
            <div class="info-row"><span class="k">Mã sản phẩm</span><span>${product.productCode}</span></div>
            <div class="info-row"><span class="k">Danh mục</span>
                <span class="badge badge-cat"><c:out value="${product.categoryName}"/></span></div>
            <div class="info-row"><span class="k">Giá</span>
                <span><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/> VNĐ</span></div>
            <div class="info-row"><span class="k">Amount</span><span>${product.amount}</span></div>

            <div class="desc-box">
                <div class="label mb-1">Description</div>
                <c:out value="${product.description}"/>
            </div>
        </div>
    </div>
</div>

<a href="${ctx}/products" class="btn btn-secondary mt-4"><i class="bi bi-arrow-left"></i> Quay lại danh sách</a>
</body>
</html>
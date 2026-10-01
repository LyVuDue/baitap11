<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Tổng quan</title></head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h3 class="mb-1">Xin chào, <c:out value="${sessionScope.account.fullname}"/> 👋</h3>
<p class="text-muted mb-4">Chọn chức năng quản trị bên dưới hoặc ở thanh menu bên trái.</p>

<div class="row g-4">
    <div class="col-md-6 col-lg-4">
        <a class="stat-card" href="${ctx}/admin/users">
            <i class="bi bi-people"></i>
            <h5 class="mt-2 mb-1">Quản lý User</h5>
            <span class="text-muted">Thêm, sửa, xóa và phân trang người dùng</span>
        </a>
    </div>
    <div class="col-md-6 col-lg-4">
        <a class="stat-card" href="${ctx}/admin/categories">
            <i class="bi bi-tags"></i>
            <h5 class="mt-2 mb-1">Quản lý Category</h5>
            <span class="text-muted">Thêm, sửa, xóa và phân trang danh mục</span>
        </a>
    </div>
</div>
</body>
</html>
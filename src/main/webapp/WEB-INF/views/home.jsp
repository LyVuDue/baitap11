<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>Trang chủ</title></head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="hero mb-5">
    <h1 class="display-5">Chào mừng đến Shop Online</h1>
    <p class="lead mb-4">Mua sắm từ nhiều cửa hàng uy tín, sản phẩm đa dạng, giá tốt mỗi ngày.</p>
    <a href="${pageContext.request.contextPath}/products" class="btn btn-light btn-lg rounded-pill px-4">
        <i class="bi bi-bag"></i> Xem sản phẩm</a>
</div>

<div class="row g-4">
    <div class="col-md-4">
        <div class="feature">
            <i class="bi bi-shop"></i>
            <h5 class="mt-2">Nhiều cửa hàng</h5>
            <p class="text-muted mb-0">Sản phẩm được gom theo từng cửa hàng, dễ tìm và dễ so sánh.</p>
        </div>
    </div>
    <div class="col-md-4">
        <div class="feature">
            <i class="bi bi-tags"></i>
            <h5 class="mt-2">Danh mục rõ ràng</h5>
            <p class="text-muted mb-0">Điện thoại, laptop, phụ kiện và nhiều loại hàng khác.</p>
        </div>
    </div>
    <div class="col-md-4">
        <div class="feature">
            <i class="bi bi-shield-check"></i>
            <h5 class="mt-2">Tài khoản an toàn</h5>
            <p class="text-muted mb-0">Đăng ký được xác thực bằng mã OTP gửi qua email.</p>
        </div>
    </div>
</div>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Đăng ký</title></head>
<body>
<div class="row justify-content-center">
    <div class="col-md-5">
        <h3 class="mb-3">Đăng ký tài khoản</h3>

        <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="mb-3">
                <label class="form-label">Tên đăng nhập *</label>
                <input type="text" name="username" class="form-control" value="<c:out value='${username}'/>" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mật khẩu *</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Họ tên *</label>
                <input type="text" name="fullname" class="form-control" value="<c:out value='${fullname}'/>" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Email * (nhận mã OTP)</label>
                <input type="email" name="email" class="form-control" value="<c:out value='${email}'/>" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Số điện thoại</label>
                <input type="text" name="phone" class="form-control" value="<c:out value='${phone}'/>">
            </div>
            <button type="submit" class="btn btn-success">Đăng ký</button>
            <a href="${pageContext.request.contextPath}/login" class="btn btn-link">Đã có tài khoản? Đăng nhập</a>
        </form>
    </div>
</div>
</body>
</html>
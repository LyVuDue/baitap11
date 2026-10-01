<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Xác thực OTP</title></head>
<body>
<div class="row justify-content-center">
    <div class="col-md-5">
        <h3 class="mb-3">Nhập mã OTP</h3>
        <p>Mã OTP 6 số đã được gửi tới <b><c:out value="${sessionScope.verifyEmail}"/></b>.</p>

        <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/verify">
            <div class="mb-3">
                <input type="text" name="otp" class="form-control" maxlength="6" placeholder="Nhập mã OTP" required>
            </div>
            <button type="submit" class="btn btn-primary">Kích hoạt</button>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-link">Đăng ký lại</a>
        </form>
    </div>
</div>
</body>
</html>
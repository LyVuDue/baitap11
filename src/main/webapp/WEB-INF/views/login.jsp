<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Đăng nhập</title></head>
<body>
<div class="row justify-content-center">
    <div class="col-md-5">
        <h3 class="mb-3">Đăng nhập</h3>

        <c:if test="${param.msg == 'verified'}">
            <div class="alert alert-success">Kích hoạt tài khoản thành công, hãy đăng nhập.</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="mb-3">
                <label class="form-label">Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" value="<c:out value='${username}'/>" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Đăng nhập</button>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-link">Chưa có tài khoản? Đăng ký</a>
        </form>
    </div>
</div>
</body>
</html>
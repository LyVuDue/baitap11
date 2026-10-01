<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Form User</title></head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="row justify-content-center">
    <div class="col-md-7">
        <h3 class="mb-3">
            <c:choose>
                <c:when test="${editUser.userId == 0}">Thêm người dùng</c:when>
                <c:otherwise>Sửa người dùng #${editUser.userId}</c:otherwise>
            </c:choose>
        </h3>

        <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
        </c:if>

        <form method="post" action="${ctx}/admin/user/save">
            <input type="hidden" name="userId" value="${editUser.userId}">
            <input type="hidden" name="page" value="${currentPage}">

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Tên đăng nhập *</label>
                    <input type="text" name="username" class="form-control"
                           value="<c:out value='${editUser.username}'/>" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">
                        Mật khẩu
                        <c:choose>
                            <c:when test="${editUser.userId == 0}">*</c:when>
                            <c:otherwise><small class="text-muted">(để trống = giữ nguyên)</small></c:otherwise>
                        </c:choose>
                    </label>
                    <input type="password" name="password" class="form-control"
                           <c:if test="${editUser.userId == 0}">required</c:if>>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Họ tên *</label>
                <input type="text" name="fullname" class="form-control"
                       value="<c:out value='${editUser.fullname}'/>" required>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Email *</label>
                    <input type="email" name="email" class="form-control"
                           value="<c:out value='${editUser.email}'/>" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Điện thoại</label>
                    <input type="text" name="phone" class="form-control"
                           value="<c:out value='${editUser.phone}'/>">
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Link ảnh</label>
                <input type="text" name="images" class="form-control"
                       value="<c:out value='${editUser.images}'/>" placeholder="https://...">
            </div>

            <div class="row">
                <div class="col-md-4 mb-3">
                    <label class="form-label">Vai trò *</label>
                    <select name="roleId" class="form-select" required>
                        <c:forEach var="r" items="${roles}">
                            <option value="${r.key}" ${editUser.roleId == r.key ? 'selected' : ''}>
                                <c:out value="${r.value}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label class="form-label">Cửa hàng <small class="text-muted">(chỉ với Seller)</small></label>
                    <select name="sellerId" class="form-select">
                        <option value="">-- Không --</option>
                        <c:forEach var="s" items="${sellers}">
                            <option value="${s.key}" ${editUser.sellerId == s.key ? 'selected' : ''}>
                                #${s.key} - <c:out value="${s.value}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4 mb-3">
                    <label class="form-label">Trạng thái</label>
                    <select name="status" class="form-select">
                        <option value="1" ${editUser.status == 1 ? 'selected' : ''}>Đã kích hoạt</option>
                        <option value="0" ${editUser.status == 0 ? 'selected' : ''}>Chưa kích hoạt</option>
                    </select>
                </div>
            </div>

            <button type="submit" class="btn btn-primary">Lưu</button>
            <a href="${ctx}/admin/users?page=${currentPage}" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</div>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Quản lý User</title></head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h3>Quản lý người dùng (User)</h3>
    <a href="${ctx}/admin/user/add" class="btn btn-success">+ Thêm người dùng</a>
</div>

<c:choose>
    <c:when test="${param.msg == 'added'}"><div class="alert alert-success">Thêm người dùng thành công.</div></c:when>
    <c:when test="${param.msg == 'updated'}"><div class="alert alert-success">Cập nhật người dùng thành công.</div></c:when>
    <c:when test="${param.msg == 'deleted'}"><div class="alert alert-success">Xóa người dùng thành công.</div></c:when>
    <c:when test="${param.msg == 'inuse'}"><div class="alert alert-danger">Không thể xóa: người dùng đã có giỏ hàng.</div></c:when>
    <c:when test="${param.msg == 'self'}"><div class="alert alert-danger">Bạn không thể xóa chính tài khoản đang đăng nhập.</div></c:when>
</c:choose>

<table class="table table-bordered table-hover align-middle">
    <thead class="table-light">
        <tr>
            <th style="width:60px">ID</th>
            <th>Tên đăng nhập</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>Điện thoại</th>
            <th>Vai trò</th>
            <th>Cửa hàng</th>
            <th>Trạng thái</th>
            <th style="width:160px">Thao tác</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.userId}</td>
                <td><c:out value="${u.username}"/></td>
                <td><c:out value="${u.fullname}"/></td>
                <td><c:out value="${u.email}"/></td>
                <td><c:out value="${u.phone}"/></td>
                <td><c:out value="${roles[u.roleId]}"/></td>
                <td><c:out value="${sellers[u.sellerId]}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${u.status == 1}"><span class="badge bg-success">Đã kích hoạt</span></c:when>
                        <c:otherwise><span class="badge bg-secondary">Chưa kích hoạt</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-sm btn-warning"
                       href="${ctx}/admin/user/edit?id=${u.userId}&page=${currentPage}">Sửa</a>

                    <form method="post" action="${ctx}/admin/user/delete" class="d-inline"
                          onsubmit="return confirm('Bạn chắc chắn muốn xóa người dùng này?');">
                        <input type="hidden" name="userId" value="${u.userId}">
                        <input type="hidden" name="page" value="${currentPage}">
                        <button type="submit" class="btn btn-sm btn-danger">Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty users}">
            <tr><td colspan="9" class="text-center text-muted">Chưa có người dùng nào.</td></tr>
        </c:if>
    </tbody>
</table>

<div class="d-flex justify-content-between align-items-center">
    <span class="text-muted">Trang ${currentPage} / ${totalPages} (tổng ${totalItems} người dùng)</span>

    <nav>
        <ul class="pagination mb-0">
            <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                <a class="page-link" href="${ctx}/admin/users?page=${currentPage - 1}">&laquo;</a>
            </li>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="${ctx}/admin/users?page=${i}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                <a class="page-link" href="${ctx}/admin/users?page=${currentPage + 1}">&raquo;</a>
            </li>
        </ul>
    </nav>
</div>
</body>
</html>
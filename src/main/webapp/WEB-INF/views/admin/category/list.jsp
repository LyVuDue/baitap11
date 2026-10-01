<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Quản lý Category</title></head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h3>Quản lý danh mục (Category)</h3>
    <a href="${ctx}/admin/category/add" class="btn btn-success">+ Thêm danh mục</a>
</div>

<c:choose>
    <c:when test="${param.msg == 'added'}"><div class="alert alert-success">Thêm danh mục thành công.</div></c:when>
    <c:when test="${param.msg == 'updated'}"><div class="alert alert-success">Cập nhật danh mục thành công.</div></c:when>
    <c:when test="${param.msg == 'deleted'}"><div class="alert alert-success">Xóa danh mục thành công.</div></c:when>
    <c:when test="${param.msg == 'inuse'}"><div class="alert alert-danger">Không thể xóa: danh mục đang có sản phẩm.</div></c:when>
</c:choose>

<table class="table table-bordered table-hover align-middle">
    <thead class="table-light">
        <tr>
            <th style="width:70px">ID</th>
            <th style="width:100px">Ảnh</th>
            <th>Tên danh mục</th>
            <th style="width:130px">Trạng thái</th>
            <th style="width:170px">Thao tác</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="c" items="${categories}">
            <tr>
                <td>${c.categoryId}</td>
                <td>
                    <c:if test="${not empty c.images}">
                        <img src="${c.images}" alt="" height="45">
                    </c:if>
                </td>
                <td><c:out value="${c.categoryName}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${c.status == 1}"><span class="badge bg-success">Hoạt động</span></c:when>
                        <c:otherwise><span class="badge bg-secondary">Ngừng</span></c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a class="btn btn-sm btn-warning"
                       href="${ctx}/admin/category/edit?id=${c.categoryId}&page=${currentPage}">Sửa</a>

                    <form method="post" action="${ctx}/admin/category/delete" class="d-inline"
                          onsubmit="return confirm('Bạn chắc chắn muốn xóa danh mục này?');">
                        <input type="hidden" name="categoryId" value="${c.categoryId}">
                        <input type="hidden" name="page" value="${currentPage}">
                        <button type="submit" class="btn btn-sm btn-danger">Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty categories}">
            <tr><td colspan="5" class="text-center text-muted">Chưa có danh mục nào.</td></tr>
        </c:if>
    </tbody>
</table>

<div class="d-flex justify-content-between align-items-center">
    <span class="text-muted">Trang ${currentPage} / ${totalPages} (tổng ${totalItems} danh mục)</span>

    <nav>
        <ul class="pagination mb-0">
            <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                <a class="page-link" href="${ctx}/admin/categories?page=${currentPage - 1}">&laquo;</a>
            </li>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="${ctx}/admin/categories?page=${i}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                <a class="page-link" href="${ctx}/admin/categories?page=${currentPage + 1}">&raquo;</a>
            </li>
        </ul>
    </nav>
</div>
</body>
</html>
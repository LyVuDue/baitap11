<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Form Category</title></head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="row justify-content-center">
    <div class="col-md-6">
        <h3 class="mb-3">
            <c:choose>
                <c:when test="${category.categoryId == 0}">Thêm danh mục</c:when>
                <c:otherwise>Sửa danh mục #${category.categoryId}</c:otherwise>
            </c:choose>
        </h3>

        <c:if test="${not empty error}">
            <div class="alert alert-danger"><c:out value="${error}"/></div>
        </c:if>

        <form method="post" action="${ctx}/admin/category/save">
            <input type="hidden" name="categoryId" value="${category.categoryId}">
            <input type="hidden" name="page" value="${currentPage}">

            <div class="mb-3">
                <label class="form-label">Tên danh mục *</label>
                <input type="text" name="categoryName" class="form-control"
                       value="<c:out value='${category.categoryName}'/>" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Link ảnh</label>
                <input type="text" name="images" class="form-control"
                       value="<c:out value='${category.images}'/>" placeholder="https://...">
            </div>
            <div class="mb-3">
                <label class="form-label">Trạng thái</label>
                <select name="status" class="form-select">
                    <option value="1" ${category.status == 1 ? 'selected' : ''}>Hoạt động</option>
                    <option value="0" ${category.status == 0 ? 'selected' : ''}>Ngừng</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Lưu</button>
            <a href="${ctx}/admin/categories?page=${currentPage}" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</div>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="uri" value="${pageContext.request.requestURI}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin - <sitemesh:write property="title" /></title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@400;500;600;700;800&display=swap" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link href="${ctx}/assets/css/style.css" rel="stylesheet">
<sitemesh:write property="head" />
</head>
<body>
<div class="admin-wrap">

    <aside class="admin-sidebar">
        <a class="brand" href="${ctx}/admin/home"><i class="bi bi-shield-lock-fill"></i> ADMIN</a>

        <a class="side-link ${fn:endsWith(uri, '/admin/home') ? 'active' : ''}" href="${ctx}/admin/home">
            <i class="bi bi-speedometer2"></i> Tổng quan</a>
        <a class="side-link ${fn:contains(uri, '/admin/user') ? 'active' : ''}" href="${ctx}/admin/users">
            <i class="bi bi-people"></i> Quản lý User</a>
        <a class="side-link ${fn:contains(uri, '/admin/categor') ? 'active' : ''}" href="${ctx}/admin/categories">
            <i class="bi bi-tags"></i> Quản lý Category</a>
        <hr class="border-secondary">
        <a class="side-link" href="${ctx}/home"><i class="bi bi-arrow-left-circle"></i> Về trang chủ</a>
    </aside>

    <div class="admin-main">
        <header class="admin-topbar">
            <span class="fw-semibold"><i class="bi bi-person-circle text-primary"></i>
                <c:out value="${sessionScope.account.fullname}"/></span>
            <a class="btn btn-outline-danger btn-sm" href="${ctx}/logout">
                <i class="bi bi-box-arrow-right"></i> Đăng xuất</a>
        </header>

        <main class="admin-content">
            <sitemesh:write property="body" />
        </main>

        <jsp:include page="/WEB-INF/decorators/footer.jsp" />
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
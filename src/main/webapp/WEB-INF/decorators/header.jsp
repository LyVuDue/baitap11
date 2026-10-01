<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="uri" value="${pageContext.request.requestURI}"/>

<nav class="navbar navbar-expand-lg navbar-dark app-navbar">
    <div class="container">
        <a class="navbar-brand" href="${ctx}/home"><i class="bi bi-bag-heart-fill me-1"></i> Shop Online</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link ${fn:endsWith(uri, '/home') ? 'active' : ''}" href="${ctx}/home">
                        <i class="bi bi-house-door"></i> Trang Chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${fn:contains(uri, '/product') ? 'active' : ''}" href="${ctx}/products">
                        <i class="bi bi-grid"></i> Sản phẩm</a>
                </li>
                <c:if test="${sessionScope.account != null && sessionScope.account.roleId == 1}">
                    <li class="nav-item">
                        <a class="nav-link" href="${ctx}/admin/home"><i class="bi bi-speedometer2"></i> Trang quản trị</a>
                    </li>
                </c:if>
            </ul>

            <c:choose>
                <c:when test="${sessionScope.account == null}">
                    <div class="d-flex gap-2">
                        <a class="btn btn-outline-light btn-sm rounded-pill px-3" href="${ctx}/login">
                            <i class="bi bi-box-arrow-in-right"></i> Đăng nhập</a>
                        <a class="btn btn-warning btn-sm rounded-pill px-3" href="${ctx}/register">Đăng ký</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="dropdown">
                        <a class="btn btn-light btn-sm dropdown-toggle rounded-pill px-3" href="#" data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle"></i> <c:out value="${sessionScope.account.fullname}"/>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end shadow border-0">
                            <c:if test="${sessionScope.account.roleId == 3}">
                                <li><a class="dropdown-item" href="${ctx}/seller/home"><i class="bi bi-shop"></i> Trang Seller</a></li>
                            </c:if>
                            <c:if test="${sessionScope.account.roleId == 1}">
                                <li><a class="dropdown-item" href="${ctx}/admin/home"><i class="bi bi-speedometer2"></i> Trang quản trị</a></li>
                            </c:if>
                            <li><a class="dropdown-item text-danger" href="${ctx}/logout"><i class="bi bi-box-arrow-right"></i> Đăng xuất</a></li>
                        </ul>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
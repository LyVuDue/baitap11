<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head><title>Trang chủ Seller</title></head>
<body>
    <h2>Trang chủ Seller</h2>
    <p>Xin chào <b><c:out value="${sessionScope.account.fullname}"/></b>, mã cửa hàng: ${sessionScope.account.sellerId}</p>
</body>
</html>
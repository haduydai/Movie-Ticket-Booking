<%--
  Created by IntelliJ IDEA.
  User: tl
  Date: 17/6/26
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kết quả thanh toán</title>
</head>
<body>
    <h2>${message}</h2>
<p> Mã vé: ${ticket.id}</p>
<p>Tổng tiền: ${ticket.totalPrice}</p>
<a href="${pageContext.request.contextPath}/">Về trang chủ</a>
</body>
</html>

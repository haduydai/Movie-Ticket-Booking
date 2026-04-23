<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Thực OTP - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="auth-container">
        <form class="auth-form" action="verify-otp" method="post">
            <div class="auth-logo">
                <a href="home" class="logo">MyCinema</a>
            </div>

            <h2>Xác Thực OTP</h2>

            <p style="color: var(--text-muted); text-align: center; font-size: 14px; margin-bottom: 20px;">
                Vui lòng kiểm tra email và nhập mã OTP 6 số vào bên dưới.
            </p>

            <%-- Ô nhập OTP --%>
            <div class="form-group-auth">
                <label for="otp">Mã OTP</label>
                <input type="text" id="otp" name="otp" placeholder="Nhập mã 6 số (VD: 123456)" required 
                       style="letter-spacing: 2px; font-weight: bold; text-align: center;">
            </div>

            <%-- Thông báo lỗi --%>
            <c:if test="${not empty error}">
                <p style="color: red; text-align: center; margin-bottom: 10px;">${error}</p>
            </c:if>

            <button type="submit" class="auth-btn">Xác Nhận</button>

            <%-- Link quay lại --%>
            <div class="auth-link">
                <p><a href="forgot-password">Gửi lại mã?</a> hoặc <a href="login">Quay về đăng nhập</a></p>
            </div>
        </form>
    </div>
</body>
</html>
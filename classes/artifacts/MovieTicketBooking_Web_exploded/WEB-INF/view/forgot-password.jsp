<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên Mật Khẩu - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="auth-container">
        
        <form class="auth-form" action="forgot-password" method="post">
            
            <div class="auth-logo">
                <a href="home" class="logo">MyCinema</a>
            </div>

            <h2>Khôi Phục Mật Khẩu</h2>

            <p style="color: var(--text-muted); text-align: center; font-size: 14px; margin-bottom: 20px;">
                Nhập email đã đăng ký để nhận mã OTP xác thực.
            </p>
            
            <div class="form-group-auth">
                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập" required>
            </div>

            <div class="form-group-auth">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Nhập email của bạn" required>
            </div>

            <c:if test="${not empty error}">
                <p style="color: red; text-align: center; margin-bottom: 10px;">${error}</p>
            </c:if>

            <button type="submit" class="auth-btn">Gửi Mã OTP</button>

            <div class="auth-link">
                <p>Bạn đã nhớ mật khẩu? <a href="login">Đăng nhập ngay</a></p>
            </div>
        </form>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Lại Mật Khẩu - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="auth-container">
        <form class="auth-form" action="reset-password" method="post">
            <div class="auth-logo">
                <a href="home" class="logo">MyCinema</a>
            </div>

            <h2>Mật Khẩu Mới</h2>
            
            <p style="color: var(--text-muted); text-align: center; font-size: 14px; margin-bottom: 20px;">
                Hãy thiết lập mật khẩu mới an toàn cho tài khoản của bạn.
            </p>

            <%-- Mật khẩu mới --%>
            <div class="form-group-auth">
                <label for="newPass">Mật khẩu mới</label>
                <input type="password" id="newPass" name="newPass" placeholder="Nhập mật khẩu mới" required>
            </div>

            <%-- Xác nhận mật khẩu --%>
            <div class="form-group-auth">
                <label for="confirmPass">Nhập lại mật khẩu</label>
                <input type="password" id="confirmPass" name="confirmPass" placeholder="Xác nhận mật khẩu mới" required>
            </div>

            <%-- Thông báo lỗi --%>
            <c:if test="${not empty error}">
                <p style="color: red; text-align: center; margin-bottom: 10px;">${error}</p>
            </c:if>

            <button type="submit" class="auth-btn">Đổi Mật Khẩu</button>
        </form>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Nhận OTP - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="auth-container">
        <form class="auth-form" id="otpForm">
            <div class="auth-logo">
                <a href="home.jsp" class="logo">MyCinema</a>
            </div>
            <h2>Xác Nhận OTP</h2>
            <p style="color: var(--text-muted); margin-bottom: var(--spacing-lg);">Mã OTP đã gửi đến email của bạn. Nhập mã để đổi mật khẩu.</p>
            <div class="form-group-auth">
                <label for="otp">Mã OTP (6 chữ số)</label>
                <input type="text" id="otp" name="otp" placeholder="Nhập mã OTP" maxlength="6" required>
            </div>
            <div class="form-group-auth">
                <label for="new-password">Mật Khẩu Mới</label>
                <input type="password" id="new-password" name="new-password" placeholder="Nhập mật khẩu mới" required>
            </div>
            <div class="form-group-auth">
                <label for="confirm-new-password">Xác Nhận Mật Khẩu Mới</label>
                <input type="password" id="confirm-new-password" name="confirm-new-password" placeholder="Nhập lại mật khẩu mới" required>
            </div>
            <button type="submit" class="auth-btn">Đổi Mật Khẩu</button>
            <div class="auth-link">
                <p><a href="login.jsp">Quay lại đăng nhập</a> | <a href="forgot-password.jsp">Gửi lại OTP</a></p>
            </div>
            <p class="error-msg" style="display: none;">Mã OTP không đúng hoặc mật khẩu không khớp!</p>
        </form>
    </div>

    <script>
        // Mock OTP validation
        document.getElementById('otpForm').addEventListener('submit', function(e) {
            e.preventDefault();
            const otp = document.getElementById('otp').value;
            const newPass = document.getElementById('new-password').value;
            const confirmPass = document.getElementById('confirm-new-password').value;
            const error = document.querySelector('.error-msg');
            if (otp !== '123456' || newPass !== confirmPass || newPass.length < 6) {
                error.textContent = 'Mã OTP không đúng hoặc mật khẩu không khớp!';
                error.style.display = 'block';
            } else {
                error.style.display = 'none';
                alert('Đổi mật khẩu thành công! Quay lại đăng nhập.');
                window.location.href = 'login.jsp';
            }
        });
    </script>
</body>
</html>
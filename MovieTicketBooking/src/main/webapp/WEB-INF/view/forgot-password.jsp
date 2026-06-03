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
        
        <form class="auth-form" id="forgotPasswordForm">

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

            <%-- Thông báo lỗi động --%>
            <p id="errorMsg" style="color: red; text-align: center; margin-bottom: 10px; display: none;"></p>

            <button type="submit" class="auth-btn">Gửi Mã OTP</button>

            <div class="auth-link">
                <p>Bạn đã nhớ mật khẩu? <a href="login">Đăng nhập ngay</a></p>
            </div>
        </form>
    </div>

    <script>
        document.getElementById("forgotPasswordForm").addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn trình duyệt reload trang

            const form = this;
            const submitBtn = form.querySelector('button[type="submit"]');
            const originalBtnText = submitBtn.innerText;
            const errorMsg = document.getElementById("errorMsg");

            // Hiệu ứng Loading
            submitBtn.disabled = true;
            submitBtn.innerText = "Đang gửi OTP...";
            submitBtn.style.opacity = "0.7";
            submitBtn.style.cursor = "not-allowed";
            errorMsg.style.display = "none";

            const formData = new URLSearchParams(new FormData(form));

            fetch("forgot-password", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData.toString()
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    // Thành công: chuyển tiếp sang trang nhập OTP
                    window.location.href = data.message;
                } else {
                    // Thất bại: hiển thị lỗi ngay trên giao diện
                    errorMsg.innerText = data.message;
                    errorMsg.style.display = "block";

                    // Bật lại nút bấm
                    submitBtn.disabled = false;
                    submitBtn.innerText = originalBtnText;
                    submitBtn.style.opacity = "1";
                    submitBtn.style.cursor = "pointer";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                errorMsg.innerText = "Lỗi kết nối máy chủ!";
                errorMsg.style.display = "block";

                // Bật lại nút bấm
                submitBtn.disabled = false;
                submitBtn.innerText = originalBtnText;
                submitBtn.style.opacity = "1";
                submitBtn.style.cursor = "pointer";
            });
        });
    </script>
</body>
</html>
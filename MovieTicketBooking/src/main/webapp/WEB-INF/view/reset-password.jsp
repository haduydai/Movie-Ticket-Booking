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
       <form class="auth-form" id="resetPasswordForm">
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

            <%-- Thông báo lỗi động --%>
            <p id="errorMsg" style="color: red; text-align: center; margin-bottom: 10px; display: none;"></p>

            <button type="submit" class="auth-btn">Đổi Mật Khẩu</button>
            <%-- Các link điều hướng phụ trợ --%>
            <div class="auth-link" style="margin-top: 15px; font-size: 14px; text-align: center;">
                <p>
                    <a href="forgot-password">Quay lại trang trước</a>
                    <br><br>
                    <a href="login">Quay về đăng nhập</a>
                </p>
            </div>
        </form>
    </div>
    <script>
        document.getElementById("resetPasswordForm").addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn form tự động reload trang

            const form = this;
            const submitBtn = form.querySelector('button[type="submit"]');
            const originalBtnText = submitBtn.innerText;
            const errorMsg = document.getElementById("errorMsg");

            // Hiệu ứng Loading
            submitBtn.disabled = true;
            submitBtn.innerText = "Đang xử lý...";
            submitBtn.style.opacity = "0.7";
            submitBtn.style.cursor = "not-allowed";
            errorMsg.style.display = "none";

            const formData = new URLSearchParams(new FormData(form));

            fetch("reset-password", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData.toString()
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    // Thành công -> Thông báo và chuyển hướng về trang login
                    alert("Đặt lại mật khẩu thành công! Vui lòng đăng nhập lại.");
                    window.location.href = data.message;
                } else {
                    // Thất bại -> Hiển thị lỗi ngay trên giao diện
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
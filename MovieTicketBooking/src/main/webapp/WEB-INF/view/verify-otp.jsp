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
        <form class="auth-form" id="verifyOtpForm">
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
            <p id="errorMsg" style="color: red; text-align: center; margin-bottom: 10px; display: none;"></p>


            <button type="submit" class="auth-btn">Xác Nhận</button>

            <%-- Link quay lại --%>
            <div class="auth-link">
                <p><a href="forgot-password">Gửi lại mã?</a> hoặc <a href="login">Quay về đăng nhập</a></p>
            </div>
        </form>
    </div>
    <script>
        document.getElementById("verifyOtpForm").addEventListener("submit", function (event) {
            event.preventDefault()  ; // Ngăn form tự động tải lại trang

            const form = this ;
            const submitBtn = form.querySelector('button[type="submit"]');
            const originalBtnText = submitBtn.innerText;

            // Vô hiệu hóa nút và đổi trạng thái thành loading
            submitBtn.disabled= true ;
            submitBtn.innerText = "Đang xác thực...";
            submitBtn.style.opacity= "0.7";
            submitBtn.style.cursor  ="not-allowed" ;

            const formData = new URLSearchParams(new FormData(form)) ;

            fetch("verify-otp" , {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData.toString()
            })
                .then(response => response.json())
                .then(data => {
                    if (data.status === "success")  {
                        // Thành công -> Servlet bảo đi đâu thì mình chuyển hướng tới đó (login hoặc reset-password)
                        window.location.href = data.message;
                    } else {
                        // Sai mã OTP -> Hiện thông báo lỗi
                        document.getElementById("errorMsg").innerText = data.message;
                        document.getElementById("errorMsg").style.display = "block" ;

                        // Bật lại nút
                        submitBtn.disabled = false;
                        submitBtn.innerText = originalBtnText;
                        submitBtn.style.opacity = "1";
                        submitBtn.style.cursor = "pointer";
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    document.getElementById("errorMsg").innerText = "Lỗi kết nối. Vui lòng thử lại sau.";
                    document.getElementById("errorMsg").style.display = "block";

                    // Bật lại nút
                    submitBtn.disabled = false;
                    submitBtn.innerText = originalBtnText;
                    submitBtn.style.opacity = "1";
                    submitBtn.style.cursor = "pointer";
                });
        });
    </script>

</body>
</html>
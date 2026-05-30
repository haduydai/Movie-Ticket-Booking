<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng Ký - MyCinema</title>
        <link rel="stylesheet" href="styles.css">
    </head>

    <body>
        <div class="auth-container">
            <form class="auth-form" id="registerForm">
                <div class="auth-logo">
                    <a href="home" class="logo">MyCinema</a>
                </div>
                <h2>Đăng Ký</h2>

                <div class="form-group-auth">
                    <label for="fullname">Tên đăng nhập</label>
                    <input type="text" id="fullname" name="username" placeholder="Nhập tên đăng nhập" required>
                </div>

                <div class="form-group-auth">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Nhập email" required>
                </div>

                <div class="form-group-auth">
                    <label for="phone">Số Điện Thoại</label>
                    <input type="tel" id="phone" name="phone" placeholder="Nhập số điện thoại" required>
                </div>

                <div class="form-group-auth">
                    <label for="password">Mật Khẩu</label>
                    <input type="password" id="password" name="password" placeholder="Mật khẩu" required>
                </div>

                <div class="form-group-auth">
                    <label for="confirm-password">Xác Nhận Mật Khẩu</label>
                    <input type="password" id="confirm-password" name="confirmPassword" placeholder="Nhập lại mật khẩu"
                        required>
                </div>

                <p id="errorMsg" style="color: red; text-align: center; margin-bottom: 10px; display: none;"></p>


                <button type="submit" class="auth-btn">Đăng Ký</button>

                <div class="social-login">
                    <p>Hoặc đăng ký bằng</p>
                    <a href="#" class="social-btn google-btn">Đăng ký với Google</a>
                    <a href="#" class="social-btn facebook-btn">Đăng ký với Facebook</a>
                </div>

                <div class="auth-link">
                    <p>Đã có tài khoản? <a href="login">Đăng nhập ngay</a></p>

                </div>
            </form>
        </div>


        <script>
            // Bắt sự kiện submit của form
            document.getElementById("registerForm").addEventListener("submit", function (event) {
                event.preventDefault(); // Ngăn form gửi dữ liệu theo cách thông thường

                const form = this; // Lấy form
                //Bọc FormData vào URLSearchParams
                const formData = new URLSearchParams(new FormData(form));

                // Gửi dữ liệu bằng Fetch API
                fetch("register", {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: formData.toString()
                })

                    .then(response => response.json()) // Chuyển response sang JSON
                    .then(data => {
                        // Xử lý kết quả từ server
                        if (data.status === "success") {
                            // Đăng ký thành công, chuyển hướng đến trang login
                            window.location.href = data.message;
                        } else {
                            // Hiển thị thông báo lỗi
                            document.getElementById("errorMsg").innerText = data.message;
                            document.getElementById("errorMsg").style.display = "block";
                        }
                    })
                    .catch(error => {
                        // Xử lý lỗi mạng hoặc lỗi hệ thống
                        console.error("Error:", error);
                        document.getElementById("errorMsg").innerText = "Đã có lỗi xảy ra. Vui lòng thử lại.";
                        document.getElementById("errorMsg").style.display = "block";
                    });
            });
        </script>
    </body>

    </html>
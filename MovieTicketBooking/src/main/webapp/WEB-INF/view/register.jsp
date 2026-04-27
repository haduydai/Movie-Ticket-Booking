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
                    <div class="password-wrapper">
                        <input type="password" id="password" name="password" placeholder="Mật khẩu" required>
                        <span class="toggle-password-icon" onclick="togglePassword('password', this)" title="Hiện mật khẩu">👁️</span>
                    </div>
                </div>

                <div class="form-group-auth">
                    <label for="confirm-password">Xác Nhận Mật Khẩu</label>
                    <div class="password-wrapper">
                        <input type="password" id="confirm-password" name="confirmPassword" placeholder="Nhập lại mật khẩu" required>
                        <span class="toggle-password-icon" onclick="togglePassword('confirm-password', this)" title="Hiện mật khẩu">👁️</span>
                    </div>
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

            // Hàm xử lý sự kiện Ẩn/Hiện mật khẩu
            function togglePassword(inputId, iconElement) {
                // Tìm thẻ input dựa vào ID được truyền vào
                const input = document.getElementById(inputId);

                // Nếu nó đang là password (bị ẩn) -> Đổi thành text (hiện chữ)
                if (input.type === "password") {
                    input.type = "text";
                    iconElement.innerText = "🙈"; // Đổi icon thành chú khỉ che mắt
                    iconElement.title = "Ẩn mật khẩu";
                }
                // Nếu nó đang là text (hiện chữ) -> Đổi lại thành password (ẩn đi)
                else {
                    input.type = "password";
                    iconElement.innerText = "👁️"; // Đổi icon về lại con mắt
                    iconElement.title = "Hiện mật khẩu";
                }
            }

        </script>

    </body>

    </html>
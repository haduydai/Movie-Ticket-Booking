<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Đăng Nhập - MyCinema</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	<div class="auth-container">

		<form class="auth-form" id="loginForm">
		<div class="auth-logo">
				<a href="home" class="logo">MyCinema</a>
			</div>

			<h2>Đăng Nhập</h2>

			<div class="form-group-auth">
				<label for="username">Tên đăng nhập</label> <input type="text"
					id="username" name="username" placeholder="Nhập tên đăng nhập"
					required>
			</div>

			<div class="form-group-auth">
				<label for="password">Mật Khẩu</label>
				<div class="password-wrapper">
					<input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required>
					<span class="toggle-password-icon" onclick="togglePassword('password',this)" title="hiện mật khẩu">👁️</span>
				</div>
			</div>

			<a href="forgot-password"
				style="color: var(--text-muted); font-size: 12px; text-align: right; display: block; margin-bottom: var(--spacing-md);">Quên
				mật khẩu?</a>

			<p id="errorMsg" style="color: red; text-align: center; margin-bottom: 10px; display: none;"></p>
			<button type="submit" class="auth-btn">Đăng Nhập</button>
			<div class="social-login">
				<p>Hoặc đăng nhập bằng</p>
				<a href="#" class="social-btn google-btn">Đăng nhập với Google</a> <a
					href="#" class="social-btn facebook-btn">Đăng nhập với Facebook</a>
			</div>

			<div class="auth-link">
				<p>
					Chưa có tài khoản? <a href="register">Đăng ký ngay</a>
				</p>
			</div>
		</form>
	</div>
	<script>
		// Bắt sự kiện submit của form đăng nhập
		document.getElementById("loginForm").addEventListener("submit", function (event) {
			event.preventDefault(); // Tuyệt đối không cho trình duyệt tự tải lại trang

			const form = this;
			const formData = new URLSearchParams(new FormData(form)); // Gói dữ liệu

			// Gửi dữ liệu đi bằng Fetch API
			fetch("login", {
				method: "POST",
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				},
				body: formData.toString()
			})
					.then(response => response.json()) // Dịch kết quả từ Backend sang JSON
					.then(data => {
						if (data.status === "success") {
							// Thành công: Chuyển hướng sang trang chủ
							window.location.href = data.message;
						} else {
							// Thất bại: Hiển thị lỗi, form VẪN GIỮ NGUYÊN không bị mất chữ!
							document.getElementById("errorMsg").innerHTML = data.message;
							document.getElementById("errorMsg").style.display = "block";
						}
					})
					.catch(error => {
						console.error("Error:", error);
						document.getElementById("errorMsg").innerText = "Lỗi kết nối máy chủ!";
						document.getElementById("errorMsg").style.display = "block";
					});
		});
		function togglePassword(inputId,iconElement){
			const input = document.getElementById(inputId);
			if(input.type ==="password"){
				input.type="text";
				iconElement.innerText = "🙈";
				iconElement.title="Ẩn mật khẩu";

			}else{
				input.type="password";
				iconElement.innerText="👁️";
				iconElement.title="Hiện mật khẩu";
			}

		}
	</script>

</body>
</html>
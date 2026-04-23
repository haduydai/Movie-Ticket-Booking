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
		
		<form class="auth-form" action="login" method="post">
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
				<label for="password">Mật Khẩu</label> <input type="password"
					id="password" name="password" placeholder="Nhập mật khẩu" required>
			</div>

			<a href="forgot-password"
				style="color: var(--text-muted); font-size: 12px; text-align: right; display: block; margin-bottom: var(--spacing-md);">Quên
				mật khẩu?</a>

			<p style="color: red; text-align: center; margin-bottom: 10px;">${error}</p>

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

</body>
</html>
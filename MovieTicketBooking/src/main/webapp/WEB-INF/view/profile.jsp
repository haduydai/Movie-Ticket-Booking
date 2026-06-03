<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Hồ Sơ Của Tôi - MyCinema</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	<jsp:include page="header.jsp" />

	<main class="profile-container">

		<%-- Nếu đã đăng nhập --%>
		<c:if test="${not empty sessionScope.user}">

			<div class="profile-section">
				<h2
					style="text-align: center; margin-bottom: 20px; color: var(--primary-color);">HỒ
					SƠ CÁ NHÂN</h2>

				<%-- Thông báo lỗi/thành công động --%>
                <p id="msgAlert" style="text-align: center; padding: 10px; border-radius: 4px; margin-bottom: 15px; display: none;"></p>

				<form id="profileForm">

					<%-- THÔNG TIN CƠ BẢN --%>
					<div class="profile-info">
						<div class="profile-field">
							<label>Tên đăng nhập</label> 
							${sessionScope.user.username}
						</div>

						<div class="profile-field">
							<label for="email">Email</label> <input type="email" id="email"
								name="email" value="${sessionScope.user.email}" required>
						</div>

						<div class="profile-field">
							<label for="phone">Số điện thoại</label> <input type="tel"
								id="phone" name="phone" value="${sessionScope.user.phoneNumber}"
								required>
						</div>
					</div>

					<%-- NÚT BẬT TÍNH NĂNG ĐỔI MẬT KHẨU --%>
					<div style="text-align: right;">
						<button type="button" class="btn-toggle"
							onclick="togglePasswordForm()">🔒 Đổi mật khẩu</button>
					</div>

					<%-- KHỐI ĐỔI MẬT KHẨU --%>
					<%-- QUAN TRỌNG: Thêm style="display: none;" vào đây để mặc định ẩn đi --%>
					<div id="passwordSection" style="display: none;">
						<h3 style="color: var(--secondary-color); margin-bottom: 10px;">Thay
							Đổi Mật Khẩu</h3>

						<div class="profile-info">
							<div class="profile-field">
								<label for="currentPass">Mật khẩu hiện tại <span
									style="color: red">*</span></label> <input type="password"
									id="currentPass" name="currentPass"
									placeholder="Nhập mật khẩu cũ">
							</div>

							<div class="profile-field">
								<label for="newPass">Mật khẩu mới</label> <input type="password"
									id="newPass" name="newPass"
									placeholder="Mật khẩu mới (tối thiểu 6 ký tự)">
							</div>

							<div class="profile-field">
								<label for="confirmPass">Xác nhận mật khẩu mới</label> <input
									type="password" id="confirmPass" name="confirmPass"
									placeholder="Nhập lại mật khẩu mới">
							</div>
						</div>
					</div>

					<%-- NÚT LƯU CHUNG --%>
					<div
						style="text-align: center; margin-top: 30px; display: flex; justify-content: center; gap: 15px;">
						<button type="submit" class="btn" style="min-width: 200px;">Lưu
							Thay Đổi</button>
						<a href="logout" class="btn btn-secondary">Đăng Xuất</a>
					</div>
				</form>
			</div>

			<%-- LỊCH SỬ ĐẶT VÉ (Không thay đổi) --%>
			<section class="profile-section">
				<h3>Lịch Sử Đặt Vé</h3>
				<div class="booking-history">
					<c:if test="${empty ticketHistory}">
						<p style="text-align: center; color: #aaa;">Bạn chưa có lịch
							sử đặt vé nào.</p>
					</c:if>

					<c:if test="${not empty ticketHistory}">
						<table class="booking-table">
							<thead>
								<tr>
									<th>Mã Vé</th>
									<th>Phim & Rạp</th>
									<th>Thời Gian</th>
									<th>Ghế</th>
									<th>Tổng Tiền</th>
									<th>Trạng Thái</th>
									<th>Thao Tác</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${ticketHistory}" var="t">
									<tr>
										<td><strong>${t.uid}</strong></td>

										<td>
											${t.showTime.movie.name}<br> <small>${t.showTime.cinema.name}
												- ${t.showTime.room.name}</small>
										</td>
										<td>
											<fmt:parseDate value="${t.showTime.startTime}"
												pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
											<fmt:formatDate value="${parsedDate}"
												pattern="HH:mm dd/MM/yyyy" />
										</td>
										<td>${t.seats}</td>
										<td>
											<fmt:formatNumber 
									        value="${t.totalPrice}" 
									        type="currency" 
									        currencySymbol="₫" 
									        maxFractionDigits="0"/>
									    </td>
										<td>
											<c:choose>
												<c:when test="${t.status == 'PAID'}">
													<span style="color: green; font-weight: bold;">Đã thanh toán</span>
												</c:when>
												<c:when test="${t.status == 'CHECKEDIN'}">
													<span style="color: blue; font-weight: bold;">Đã check in</span>
												</c:when>
												<c:when test="${t.status == 'CANCELLED'}">
													<span style="color: red; font-weight: bold;">Đã hủy</span>
												</c:when>
												<c:when test="${t.status == 'UNPAID'}">
													<span style="color: yellow; font-weight: bold;">Chưa thanh toán</span>
												</c:when>
											</c:choose></td>

										<td>
											<c:if test="${t.status != 'CANCELLED'}">
												<form action="cancel-ticket" method="post"
													onsubmit="return confirm('Bạn có chắc chắn muốn hủy vé ${t.uid}?');">
													<input type="hidden" name="ticketId" value="${t.id}">
													<button type="submit"
														style="background: #ff4444; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer;">
														Hủy Vé</button>
												</form>
											</c:if>
										</td>
									</tr>
								</c:forEach>

								<c:if test="${empty ticketHistory}">
									<tr>
										<td colspan="7" style="text-align: center;">Chưa có lịch
											sử đặt vé.</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</c:if>
				</div>
			</section>

		</c:if>
	</main>

	<jsp:include page="footer.jsp" />


	<script>
        function togglePasswordForm() {
            var x = document.getElementById("passwordSection");
            // Kiểm tra và bật tắt hiển thị
            if (x.style.display === "none" || x.style.display === "") {
                x.style.display = "block";
                x.style.animation = "fadeIn 0.5s ease-in-out";
            } else {
                x.style.display = "none";
                // Reset input khi ẩn
                document.getElementById("currentPass").value = "";
                document.getElementById("newPass").value = "";
                document.getElementById("confirmPass").value = "";
            }
        }

        document.getElementById("profileForm").addEventListener("submit", function(event) {
            event.preventDefault(); // Ngăn form reload trang

            const form = this;
            const submitBtn = form.querySelector('button[type="submit"]');
            const originalBtnText = submitBtn.innerText;
            const alertMsg = document.getElementById("msgAlert");

            // Hiệu ứng Loading
            submitBtn.disabled = true;
            submitBtn.innerText = "Đang lưu thay đổi...";
            submitBtn.style.opacity = "0.7";
            submitBtn.style.cursor = "not-allowed";
            alertMsg.style.display = "none";

            const formData = new URLSearchParams(new FormData(form));

            fetch("profile", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData.toString()
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    // Thành công -> Hiện thông báo màu xanh
                    alertMsg.innerText = data.message;
                    alertMsg.style.color = "#00C851";
                    alertMsg.style.background = "rgba(0, 200, 81, 0.1)";
                    alertMsg.style.display = "block";

                    // Reset các ô nhập mật khẩu nếu có
                    document.getElementById("currentPass").value = "";
                    document.getElementById("newPass").value = "";
                    document.getElementById("confirmPass").value = "";
                    document.getElementById("passwordSection").style.display = "none";
                } else {
                    // Thất bại -> Hiện thông báo màu đỏ
                    alertMsg.innerText = data.message;
                    alertMsg.style.color = "#ff4444";
                    alertMsg.style.background = "rgba(255, 0, 0, 0.1)";
                    alertMsg.style.display = "block";

                    // Tự động mở khung mật khẩu nếu lỗi liên quan đến mật khẩu
                    if (data.message.includes("mật khẩu") || data.message.includes("Mật khẩu")) {
                        document.getElementById("passwordSection").style.display = "block";
                    }
                }

                // Khôi phục nút bấm
                submitBtn.disabled = false;
                submitBtn.innerText = originalBtnText;
                submitBtn.style.opacity = "1";
                submitBtn.style.cursor = "pointer";
            })
            .catch(error => {
                console.error("Error:", error);
                alertMsg.innerText = "Lỗi kết nối máy chủ!";
                alertMsg.style.color = "#ff4444";
                alertMsg.style.background = "rgba(255, 0, 0, 0.1)";
                alertMsg.style.display = "block";

                submitBtn.disabled = false;
                submitBtn.innerText = originalBtnText;
                submitBtn.style.opacity = "1";
                submitBtn.style.cursor = "pointer";
            });
        });
    </script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}">
	
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">
<style>
	.nav-item {
		width: 100%;
		background: transparent;
		border: none;
		text-align: left;
		border-radius: 8px;
	}
	
	.nav-item:hover {
		background-color: #495057;
	}
	
</style>
</head>
<body>
	<div class="d-flex">
		<!-- Sidebar -->
		<nav class="sidebar bg-dark text-white vh-100 p-3 d-flex flex-column" style="position: fixed;width: 250px;">
			<h4 class="text-center mb-2">Admin Panel</h4>
			<hr>
			<ul class="nav nav-pills flex-column mb-auto" id="listPage">
				<li class="nav-item">
					<form action="${pageContext.request.contextPath}/admin/movies" id="movie" method="post">
						<input type="submit" value="Quản lý phim" class="nav-link p-3 text-white">
					</form>
				</li>
				<li class="nav-item">
					<form action="${pageContext.request.contextPath}/admin/cinemas" id="cinema" method="post">
						<input type="submit" value="Quản lý rạp" class="nav-link p-3 text-white">
					</form>
				</li>
				<li class="nav-item">
					<form action="${pageContext.request.contextPath}/admin/rooms" id="room" method="post">
						<input type="submit" value="Quản lý phòng chiếu" class="nav-link p-3 text-white">
					</form>
				</li>
				<li class="nav-item">
					<form action="${pageContext.request.contextPath}/admin/showtimes" id="showtime" method="post">
						<input type="submit" value="Quản lý suất chiếu" class="nav-link p-3 text-white">
					</form>
				</li>
				<li class="nav-item">
					<form action="${pageContext.request.contextPath}/admin/tickets" id="ticket" method="post">
						<input type="submit" value="Quản lý vé" class="nav-link p-3 text-white">
					</form>
				</li>
				<li class="nav-item">
					<form action="${pageContext.request.contextPath}/admin/users" id="user" method="post">
						<input type="submit" value="Quản lý tài khoản" class="nav-link p-3 text-white">
					</form>
				</li>
			</ul>
			
			<div class="nav-item d-flex justify-content-center align-items-center">
				<form action="${pageContext.request.contextPath}/admin/logout" method="get" >
					<input type="submit" value="Đăng xuất" class="nav-link text-white mt-auto text-center p-3">
				</form>
			</div>
			
		</nav>
		<div id="currentPage" current-page="${pageView }"></div>
		<!-- Main content -->
		<main class="flex-fill p-4" style="margin-left: 250px;">
			<jsp:include page="${pageView}" />
		</main>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
	document.addEventListener("DOMContentLoaded", function () {

	    // Lấy page hiện tại
	    const currentPage = currentPageDiv.getAttribute("current-page");
	
	    if (!currentPage) return;
	
	    // Lấy ul chứa menu
	    const listPage = document.getElementById("listPage");
	
	    // Xóa hết active cũ
	    listPage.querySelectorAll(".nav-item").forEach(li => {
	        li.classList.remove("active");
	    });
	
	    const listId = ["movie", "cinema", "room", "showtime", "ticket", "user"];
	 	// Duyệt mảng và set active
	    for (let i = 0; i < listId.length; i++) {
	        if (currentPage.includes(listId[i])) {
	            const li = document.getElementById(listId[i]);
	            if (li) {
	                li.classList.add("active");
	            }
	            break;
	        }
	    }
		});
	</script>


</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MyCinema - Trang Chủ</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	<jsp:include page="header.jsp" />

	<main>
		<section class="hero">
			<div class="hero-overlay" style="display:flex; justify-content:center; align-items:center;">
				<img style=" border: 3px solid #888; border-radius: 8px;" 
				alt="" src="https://iguov8nhvyobj.vcdn.cloud/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/2/8/28namsau.jpg">
			</div>
		</section>

		<section class="section">
			<h2>Phim Đang Chiếu</h2>
			<div class="movie-grid">
				<%-- Dùng JSTL để lặp qua danh sách listShowing từ Servlet --%>
				<c:forEach items="${listShowing}" var="m">
					<div class="movie-card">
						<img src="${m.imageUrl}" alt="${m.name}" loading="lazy"
							onerror="this.onerror=null; this.src='https://via.placeholder.com/180x270?text=No+Image'">
						<div class="movie-info">
							<h3>${m.name}</h3>
							<div class="rating">${m.duration} phút</div>
							<p class="genre">${m.type}</p>

							<a href="movie-detail?movieId=${m.id}" class="btn">Chi tiết</a>
							<a href="book-ticket?movieId=${m.id}" class="btn">Đặt Vé</a>
						</div>
					</div>
				</c:forEach>
			</div>
			<a href="showing" class="btn"
				style="margin-top: var(--spacing-md); display: inline-block;">Xem
				Tất Cả</a>
		</section>

		<section class="section">
			<h2>Phim Sắp Chiếu</h2>
			<div class="movie-grid">
				<c:forEach items="${listUpcoming}" var="m">
					<div class="movie-card">
						<img src="${m.imageUrl}" alt="${m.name}" loading="lazy"
							onerror="this.onerror=null; this.src='https://via.placeholder.com/180x270?text=No+Image'">
						<div class="movie-info">
							<h3>${m.name}</h3>
							<div class="rating">${m.duration} phút</div>
							<p class="genre">${m.type}</p>
							
							<a href="movie-detail?movieId=${m.id}" class="btn">Chi tiết</a>
						</div>
					</div>
				</c:forEach>
			</div>
			<a href="upcoming" class="btn"
				style="margin-top: var(--spacing-md); display: inline-block;">Xem
				Tất Cả</a>
		</section>
	</main>

	<jsp:include page="footer.jsp" />
</body>
</html>
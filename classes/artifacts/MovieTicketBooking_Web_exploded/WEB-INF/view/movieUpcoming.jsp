<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Phim Sắp Chiếu - MyCinema</title>
<link rel="stylesheet" href="styles.css">
<style>
    .movie-card img {
        width: 100%; height: 270px; object-fit: cover; background-color: #222; display: block;
    }
</style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <main>
        <section class="hero" style="height: 200px;">
            <div class="hero-banner"
                style="background-image: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('http://www.impawards.com/2024/posters/dune_part_two.jpg');"></div>
            <div class="hero-overlay">
                <h1>Phim Sắp Chiếu</h1>
                <p>Cập nhật phim bom tấn sắp ra mắt tại MyCinema.</p>
            </div>
        </section>

        <section class="section">
            <div class="movie-grid">
                <%-- Vòng lặp lấy dữ liệu từ MovieUpcomingServlet --%>
                <c:forEach items="${fullListUpcoming}" var="m">
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

                <c:if test="${empty fullListUpcoming}">
                    <p>Hiện chưa có phim sắp chiếu.</p>
                </c:if>
            </div>
        </section>
    </main>
    
    <jsp:include page="footer.jsp" />
</body>
</html>
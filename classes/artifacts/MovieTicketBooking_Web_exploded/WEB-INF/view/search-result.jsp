<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết Quả Tìm Kiếm - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <main>
        <section class="hero" style="height: 200px;">
            <div class="hero-banner" style="background-image: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), url('https://via.placeholder.com/1400x300?text=Search');"></div>
            <div class="hero-overlay">
                <h1>Kết Quả Tìm Kiếm</h1>
                <p>Từ khóa: "${param.keyword}"</p>
            </div>
        </section>

        <section class="section">
            <div class="movie-grid">
                <c:choose>
                    <c:when test="${not empty searchResults}">
                        <c:forEach var="m" items="${searchResults}">
                            <div class="movie-card">
                                <img src="${m.imageUrl}" alt="${m.name}">
                                <div class="movie-info">
                                    <h3>${m.name}</h3>
                                    <p class="genre">${m.type}</p>
                                    <a href="movie-detail?id=${m.id}" class="btn">Chi Tiết</a>
                                    <a href="book-ticket?id=${m.id}" class="btn">Đặt Vé</a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div style="grid-column: 1 / -1; text-align: center; padding: 50px;">
                            <h3>Không tìm thấy phim nào phù hợp với từ khóa "${param.keyword}".</h3>
                            <p style="color: var(--text-muted);">Vui lòng thử lại với từ khóa khác.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>
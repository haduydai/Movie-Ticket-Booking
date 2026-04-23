<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>${cinema.name} - Lịch Chiếu</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        /* CSS riêng cho header của trang chi tiết rạp */
        .theater-info-header {
            background-color: #1a1a1a;
            padding: 40px 20px;
            text-align: center;
            border-bottom: 1px solid #333;
        }
        .theater-info-header h1 {
            color: var(--primary-color);
            margin: 0 0 10px 0;
            font-size: 2.5rem;
        }
        .theater-info-header p {
            color: #ccc;
            font-size: 1.1rem;
        }
        
        /* CSS giữ khung ảnh phim */
        .movie-card img {
            width: 100%; height: 270px; object-fit: cover; background-color: #222;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <main>
        <%-- PHẦN 1: THÔNG TIN RẠP (Thay cho map) --%>
        <section class="theater-info-header">
            <h1>${cinema.name}</h1>
            <p>📍 Địa chỉ: ${cinema.address}</p>
        </section>

        <%-- PHẦN 2: DANH SÁCH PHIM TẠI RẠP NÀY --%>
        <section class="section">
            <h2 style="border-left: 4px solid var(--primary-color); padding-left: 10px;">Phim Đang Chiếu Tại Đây</h2>
            
            <div class="movie-grid">
                <%-- Nếu không có phim nào --%>
                <c:if test="${empty moviesAtCinema}">
                    <div style="grid-column: 1/-1; text-align: center; padding: 50px;">
                        <p style="color: white; font-size: 1.2rem;">Hiện chưa có lịch chiếu tại rạp này.</p>
                        <a href="movies" class="btn" style="margin-top: 10px;">Xem Phim Tại Rạp Khác</a>
                    </div>
                </c:if>

                <%-- Vòng lặp hiển thị phim --%>
                <c:forEach items="${moviesAtCinema}" var="m">
                    <div class="movie-card">
                      <img src="${m.imageUrl}" 
                             alt="${m.name}"
                             loading="lazy" 
                             onerror="this.src='https://via.placeholder.com/180x270?text=No+Image'">	   
                        <div class="movie-info">
                            <h3>${m.name}</h3>
                            <div class="rating">★ ${m.duration} phút</div>
                            <p class="genre">${m.type}</p>
                            
                            <%-- Nút đặt vé: Gửi kèm cả ID Phim và ID Rạp --%>
                            <a href="book-ticket.jsp?id=${m.id}&cinemaId=${cinema.id}" class="btn">Đặt Vé</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>
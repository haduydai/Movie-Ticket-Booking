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
        
        <section class="theater-info-header">
            <h1>${cinema.name}</h1>
            <p>📍 Địa chỉ: ${cinema.address}</p>
        </section>

        
        <section class="section" style="padding-bottom: 0;">
            <h2 style="border-left: 4px solid var(--primary-color); padding-left: 10px; margin-bottom: 15px;">Chọn Phòng Chiếu</h2>
            <div style="display: flex; gap: 10px; flex-wrap: wrap;">
                <a href="theater-detail?id=${cinema.id}" 
                   style="padding: 10px 20px; border-radius: 20px; text-decoration: none; font-weight: bold; transition: all 0.3s;
                          background-color: ${empty selectedRoomId ? 'var(--primary-color)' : '#222'};
                          color: ${empty selectedRoomId ? 'white' : '#ccc'};
                          border: 1px solid ${empty selectedRoomId ? 'var(--primary-color)' : '#444'};">
                    Tất Cả Phòng
                </a>
                
                <c:forEach items="${rooms}" var="r">
                    <a href="theater-detail?id=${cinema.id}&roomId=${r.id}" 
                       style="padding: 10px 20px; border-radius: 20px; text-decoration: none; font-weight: bold; transition: all 0.3s;
                              background-color: ${selectedRoomId == r.id ? 'var(--primary-color)' : '#222'};
                              color: ${selectedRoomId == r.id ? 'white' : '#ccc'};
                              border: 1px solid ${selectedRoomId == r.id ? 'var(--primary-color)' : '#444'};">
                        Phòng ${r.name}
                    </a>
                </c:forEach>
            </div>
        </section>

        
        <section class="section">
            <h2 style="border-left: 4px solid var(--primary-color); padding-left: 10px;">Phim Đang Chiếu Tại Đây</h2>
            
            <div class="movie-grid">
                
                <c:if test="${empty moviesAtCinema}">
                    <div style="grid-column: 1/-1; text-align: center; padding: 50px;">
                        <p style="color: white; font-size: 1.2rem;">Hiện chưa có lịch chiếu nào phù hợp tại đây.</p>
                        <a href="theater-detail?id=${cinema.id}" class="btn" style="margin-top: 10px;">Xem Tất Cả Phòng</a>
                    </div>
                </c:if>

                
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
                            
                            
                            <a href="${pageContext.request.contextPath}/book-ticket?movieId=${m.id}" class="btn">Đặt Vé</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>
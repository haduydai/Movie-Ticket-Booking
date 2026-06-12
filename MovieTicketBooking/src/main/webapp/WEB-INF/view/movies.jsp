<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tất Cả Phim - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
    
    <style>
        /* CSS giữ khung ảnh cố định */
        .movie-card img {
            width: 100%;
            height: 270px;
            object-fit: cover;
            background-color: #222;
            display: block;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <main>
        <%-- Hero Banner (Giữ nguyên) --%>
        <section class="hero" style="height: 200px">
            <div class="hero-banner"
                style="background-image: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('http://www.impawards.com/2019/posters/avengers_endgame.jpg');"></div>
            <div class="hero-overlay">
                <h1>Tất Cả Phim Tại MyCinema</h1>
                <p>Khám phá danh sách phim đa dạng từ đang chiếu đến sắp ra mắt.</p>
            </div>
        </section>

            <section class="section">
                <form action="movies" method="get">
                <select name = "type">
                    <option value=""> Tất cả thể loại</option>
                    <option value="Action" ${selectType == 'Action' ? 'selected' : ''}> Hành động </option>
                    <option value="Comedy" ${selectType == 'Comedy' ? 'selected' : ''}> Hài kịch </option>
                    <option value="Drama" ${selectType == 'Drama' ? 'selected' : ''}> Chính kịch  </option>
                    <option value="Horror" ${selectType == 'Horror' ? 'selected' : ''}> Kinh dị </option>
                    <option value="Animation" ${selectType == 'Animation' ? 'selected' : ''}> Hoạt hình </option>
                    <option value="Romance" ${selectType == 'Romance' ? 'selected' : ''}> Tình cảm </option>
                    <option value="Sci-fi" ${selectType == 'Sci-fi' ? 'selected' : ''}> Khoa học viễn tưởng </option>
                    <option value="Adventure" ${selectType == 'Adventure' ? 'selected' : ''}> Phiêu lưu </option>
                    <option value="Action" ${selectType == 'Action' ? 'selected' : ''}> Hành động </option>
                </select>


                <select name ="country">
                    <option value="">Tất cả </option>
                    <option value="USA" ${selectCountry == 'USA' ? 'selected' :''}>Mỹ</option>
                    <option value="VietNam" ${selectCountry == 'VietNam' ? 'selected' :''}>Việt Nam</option>
                    <option value="Korea" ${selectCountry == 'Korea' ? 'selected' :''}>Hàn Quốc</option>
                    <option value="Japan" ${selectCountry == 'Japan' ? 'selected' :''}>Nhật Bản</option>
                    <option value="ThaiLan" ${selectCountry == 'ThaiLan' ? 'selected' :''}>Thái Lan</option>
                    <option value="Indian" ${selectCountry == 'Indian' ? 'selected' :''}>Ấn Độ</option>
                </select>

                    <select name = "tag">
                        <option value="">None </option>
                        <option value="Marvel" ${selectTag == 'Marvel' ? 'selected':''}>Marvel </option>
                        <option value="Anime" ${selectTag == 'Anime' ? 'selected':''}>Anime</option>
                        <option value="HocDuong" ${selectTag == 'HocDuong' ? 'selected':''}>Học đường </option>
                        <option value="Disney" ${selectTag == 'Disney' ? 'selected':''}>Disney </option>
                    </select>

                <button class = "btn" type="submit">Lọc phim</button>
                <a href ="movies" class ="btn">Xoá lọc</a>
                </form>
            </section>
            <section class="section">
            <h2>Phim Đang Chiếu</h2>
            <div class="movie-grid">
                
                <%-- Kiểm tra nếu danh sách rỗng --%>
                <c:if test="${empty listShowing}">
                    <p style="color:white; font-style: italic;">Hiện chưa có phim đang chiếu.</p>
                </c:if>

                <c:forEach items="${listShowing}" var="m">
                    <div class="movie-card">
                        <img src="${m.imageUrl}" 
                             alt="${m.name}"
                             loading="lazy" 
                             onerror="this.src='https://via.placeholder.com/180x270?text=No+Image'">
                             
                        <div class="movie-info">
                            <h3>${m.name}</h3>
                            <div class="rating">${m.duration} phút</div>
                            <p class="genre">${m.type}</p>
                            <%-- Nút Đặt Vé cho phim đang chiếu --%>
                            <a href="movie-detail?movieId=${m.id}" class="btn">Chi tiết</a>
                            <a href="book-ticket?movieId=${m.id}" class="btn">Đặt Vé</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>

        <section class="section">
            <h2>Phim Sắp Chiếu</h2>
            <div class="movie-grid">
                
                <%-- Kiểm tra nếu danh sách rỗng --%>
                <c:if test="${empty listUpcoming}">
                    <p style="color:white; font-style: italic;">Hiện chưa có phim sắp chiếu.</p>
                </c:if>

                <c:forEach items="${listUpcoming}" var="m">
                    <div class="movie-card">
                        <img src="${m.imageUrl}" 
                             alt="${m.name}"
                             loading="lazy" 
                             onerror="this.src='https://via.placeholder.com/180x270?text=No+Image'">
                             
                        <div class="movie-info">
                            <h3>${m.name}</h3>
                            <div class="rating">${m.duration} phút</div>
                            <p class="genre">${m.type}</p>
                            
                            <a href="movie-detail?movieId=${m.id}" class="btn">Chi tiết</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>
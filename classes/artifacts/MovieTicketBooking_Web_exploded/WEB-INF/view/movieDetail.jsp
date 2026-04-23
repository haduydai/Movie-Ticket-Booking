<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${movie != null ? movie.name : 'Chi tiết phim'} - MyCinema</title>
    <link rel="stylesheet" href="styles.css">

    <%-- CSS riêng cho trang movieDetail (giữ gọn, không đụng tới styles.css chung) --%>
    <style>
        .movie-detail-page { padding: var(--spacing-lg) 0; }

        /* Hero (giống kiểu Cinestar: poster + thông tin trên nền ảnh) */
        .md-hero {
            border-radius: 12px;
            overflow: hidden;
            border: 1px solid #222;
            background-size: cover;
            background-position: center;
        }
        .md-hero::before {
            content: "";
            display: block;
            background: rgba(0,0,0,0.55);
        }
        .md-hero-inner {
            display: grid;
            grid-template-columns: 260px 1fr;
            gap: var(--spacing-lg);
            padding: clamp(16px, 3vw, 28px);
            background: linear-gradient(
                90deg,
                rgba(0,0,0,0.85) 0%,
                rgba(0,0,0,0.72) 45%,
                rgba(0,0,0,0.55) 100%
            );
        }

        .md-poster img {
            width: 100%;
            height: 380px;
            object-fit: cover;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.5);
            border: 1px solid #2a2a2a;
            background: #111;
        }

        .md-info h1 {
            font-size: clamp(22px, 3vw, 34px);
            margin-bottom: 10px;
            line-height: 1.2;
        }

        .md-chips {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin: 10px 0 14px;
        }
        .chip {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 6px 10px;
            border-radius: 999px;
            border: 1px solid #333;
            background: rgba(20,20,20,0.6);
            color: var(--text-color);
            font-size: 12px;
            white-space: nowrap;
        }
        .chip-status {
            border-color: rgba(229,9,20,0.6);
            background: rgba(229,9,20,0.12);
        }

        .md-kv {
            display: grid;
            gap: 8px;
            margin-top: 6px;
        }
        .kv-row {
            display: grid;
            grid-template-columns: 110px 1fr;
            gap: 10px;
            font-size: 14px;
        }
        .kv-label { color: var(--text-muted); }
        .kv-value { color: var(--text-color); }

        .md-actions {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 16px;
        }
        .btn.disabled {
            opacity: 0.55;
            cursor: not-allowed;
            pointer-events: none;
        }
        .md-note {
            margin-top: 10px;
            color: var(--text-muted);
            font-size: 12px;
        }

        /* Nội dung chi tiết (giữ phần mô tả) */
        .md-content {
            display: grid;
            grid-template-columns: 1fr 360px;
            gap: var(--spacing-lg);
            margin-top: var(--spacing-lg);
        }
        .md-card {
            background: var(--bg-secondary);
            border: 1px solid #222;
            border-radius: 12px;
            padding: clamp(14px, 2.2vw, 20px);
        }
        .md-card h2, .md-card h3 {
            margin-bottom: 10px;
        }
        .md-desc {
            color: var(--text-color);
            line-height: 1.7;
            white-space: pre-line;
        }

        .md-specs {
            list-style: none;
            display: grid;
            gap: 10px;
        }
        .md-specs li {
            display: grid;
            grid-template-columns: 120px 1fr;
            gap: 10px;
            padding: 10px;
            border-radius: 10px;
            border: 1px solid #2a2a2a;
            background: rgba(0,0,0,0.2);
        }
        .md-specs li span:first-child { color: var(--text-muted); }

        /* Responsive */
        @media (max-width: 900px) {
            .md-hero-inner { grid-template-columns: 180px 1fr; }
            .md-poster img { height: 270px; }
            .md-content { grid-template-columns: 1fr; }
        }
        @media (max-width: 560px) {
            .md-hero-inner { grid-template-columns: 1fr; }
            .md-poster img { height: 420px; }
            .kv-row { grid-template-columns: 1fr; }
        }
    </style>
</head>

<body>
    <jsp:include page="header.jsp" />

    <main class="movie-detail-page">
        <%-- Fallback ảnh --%>
        <c:set var="posterUrl" value="${empty movie.imageUrl ? 'https://via.placeholder.com/400x600?text=No+Image' : movie.imageUrl}" />

        <%-- HERO: Poster + thông tin nhanh + nút Đặt vé (chuyển sang chọn suất chiếu) --%>
        <section class="md-hero" style="background-image: linear-gradient(rgba(0,0,0,0.55), rgba(0,0,0,0.85)), url('${posterUrl}');">
            <div class="md-hero-inner">
                <div class="md-poster">
                    <img src="${posterUrl}" alt="${movie.name}" onerror="this.onerror=null; this.src='https://via.placeholder.com/400x600?text=No+Image'">
                </div>

                <div class="md-info">
                    <h1>${movie.name}</h1>

                    <div class="md-chips">
                        <span class="chip">${movie.type}</span>
                        <span class="chip">${movie.duration} phút</span>
                        <span class="chip">${movie.country}</span>
                        <span class="chip chip-status">
                            <c:choose>
                                <c:when test="${movie.movieStatus == 'NOW_SHOWING'}">Đang chiếu</c:when>
                                <c:otherwise>Sắp chiếu</c:otherwise>
                            </c:choose>
                        </span>
                    </div>

                    <div class="md-kv">
                        <div class="kv-row">
                            <span class="kv-label">Đạo diễn</span>
                            <span class="kv-value">${empty movie.directorName ? 'Đang cập nhật' : movie.directorName}</span>
                        </div>
                        <div class="kv-row">
                            <span class="kv-label">Diễn viên</span>
                            <span class="kv-value">${empty movie.actorsName ? 'Đang cập nhật' : movie.actorsName}</span>
                        </div>
                    </div>

                    <div class="md-actions">
                        <%-- Nút Đặt vé: chuyển sang trang chọn suất chiếu (book-ticket.jsp) --%>
                        <c:choose>
                            <c:when test="${movie.movieStatus == 'NOW_SHOWING'}">
                                <a class="btn" href="book-ticket?movieId=${movie.id}">Đặt vé</a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn disabled" href="#">Sắp chiếu</a>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <p class="md-note">* Nhấn “Đặt vé” để chuyển sang bước chọn ngày/rạp/suất chiếu.</p>
                </div>
            </div>
        </section>

        <%-- NỘI DUNG: Giữ phần mô tả chi tiết phim --%>
        <section class="md-content">
            <div class="md-card">
                <h2>Mô tả chi tiết</h2>
                <p class="md-desc">${empty movie.description ? 'Đang cập nhật nội dung phim.' : movie.description}</p>
            </div>

            <aside class="md-card">
                <h3>Thông tin phim</h3>
                <ul class="md-specs">
                    <li><span>Thể loại</span><span>${movie.type}</span></li>
                    <li><span>Thời lượng</span><span>${movie.duration} phút</span></li>
                    <li><span>Quốc gia</span><span>${movie.country}</span></li>
                    <li><span>Trạng thái</span>
                        <span>
                            <c:choose>
                                <c:when test="${movie.movieStatus == 'NOW_SHOWING'}">Đang chiếu</c:when>
                                 <c:when test="${movie.movieStatus == 'COMING_SOON'}">Sắp chiếu</c:when>
                                <c:otherwise>Ngừng chiếu</c:otherwise>
                            </c:choose>
                        </span>
                    </li>
                </ul>
            </aside>
        </section>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>

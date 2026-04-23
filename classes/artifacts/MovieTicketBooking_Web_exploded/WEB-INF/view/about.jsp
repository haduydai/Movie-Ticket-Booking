 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giới Thiệu - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <main>
        <section class="hero" style="height: 300px;">
            <div class="hero-banner" style="background-image: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('https://via.placeholder.com/1400x300?text=About+Us');"></div>
            <div class="hero-overlay">
                <h1>Về MyCinema</h1>
                <p>Hệ thống rạp chiếu phim hiện đại hàng đầu.</p>
            </div>
        </section>

        <section class="section" style="background: var(--bg-secondary); padding: var(--spacing-lg); border-radius: var(--border-radius);">
            <h2>Câu Chuyện Của Chúng Tôi</h2>
            <p style="margin-bottom: var(--spacing-md); color: var(--text-muted); line-height: 1.8;">
                Được thành lập vào năm 2025, MyCinema cam kết mang đến trải nghiệm điện ảnh chân thực và sống động nhất cho khán giả Việt Nam. 
                Với hệ thống phòng chiếu trang bị công nghệ Dolby Atmos, màn hình IMAX sắc nét và ghế ngồi thoải mái, chúng tôi không chỉ chiếu phim 
                mà còn tạo nên những khoảnh khắc đáng nhớ.
            </p>
            
            <h2>Tầm Nhìn & Sứ Mệnh</h2>
            <ul style="margin-left: 20px; color: var(--text-muted); line-height: 1.8; margin-bottom: var(--spacing-md);">
                <li>Trở thành cụm rạp được yêu thích nhất tại Việt Nam.</li>
                <li>Kết nối cộng đồng yêu phim ảnh.</li>
                <li>Mang điện ảnh thế giới đến gần hơn với khán giả Việt.</li>
            </ul>

            <h2>Liên Hệ Hợp Tác</h2>
            <p style="color: var(--text-muted);">
                Email: partnership@mycinema.vn<br>
                Điện thoại: 090 123 4567
            </p>
        </section>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>
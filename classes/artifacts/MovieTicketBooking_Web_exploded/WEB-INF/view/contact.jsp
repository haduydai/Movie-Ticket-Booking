<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liên Hệ - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <main>
        <section class="hero">
            <div class="hero-banner" style="background-image: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('https://via.placeholder.com/1400x500?text=Contact');"></div>
            <div class="hero-overlay">
                <h1>Liên Hệ Với Chúng Tôi</h1>
                <p>Có câu hỏi? Chúng tôi luôn sẵn sàng hỗ trợ.</p>
            </div>
        </section>

        <div style="display: flex; gap: var(--spacing-lg); padding: var(--spacing-md);">
            <section style="flex: 1; background: var(--bg-secondary); padding: var(--spacing-lg); border-radius: var(--border-radius);">
                <h2>Gửi Câu Hỏi</h2>
                <form>
                    <div class="form-group">
                        <label for="contact-name">Họ Tên</label>
                        <input type="text" id="contact-name" placeholder="Nhập họ tên" required>
                    </div>
                    <div class="form-group">
                        <label for="contact-email">Email</label>
                        <input type="email" id="contact-email" placeholder="Nhập email" required>
                    </div>
                    <div class="form-group">
                        <label for="contact-phone">Số Điện Thoại</label>
                        <input type="tel" id="contact-phone" placeholder="Nhập số điện thoại" required>
                    </div>
                    <div class="form-group">
                        <label for="contact-subject">Chủ Đề</label>
                        <select id="contact-subject">
                            <option>Hỏi Về Vé</option>
                            <option>Phàn Nàn</option>
                            <option>Hợp Tác</option>
                            <option>Khác</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="contact-message">Tin Nhắn</label>
                        <textarea id="contact-message" rows="5" placeholder="Nhập nội dung câu hỏi..." required></textarea>
                    </div>
                    <button type="submit" class="auth-btn" style="width: 100%;">Gửi</button>
                </form>
            </section>

            <section style="flex: 1; background: var(--bg-secondary); padding: var(--spacing-lg); border-radius: var(--border-radius);">
                <h2>Thông Tin Liên Hệ</h2>
                <p><strong>Email:</strong> support@mycinema.vn</p>
                <p><strong>Hotline:</strong> 1900-1234 (8:00 - 22:00)</p>
                <p><strong>Địa Chỉ:</strong> 123 Nguyễn Huệ, Q.1, TP.HCM</p>
                <p><strong>Website:</strong> mycine.vn</p>
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.325!2d106.699!3d10.776!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f38f7e!2sHo+Chi+Minh+City!5e0!3m2!1sen!2s!4v1630000000000" width="100%" height="250" style="border:0; border-radius: var(--border-radius);" allowfullscreen="" loading="lazy"></iframe>
            </section>
        </div>
    </main>

    <jsp:include page="footer.jsp" />

    <script>
        // Form submit mock (Script riêng của trang liên hệ, các script dropdown đã có trong footer.jsp)
        document.querySelector('form').addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Câu hỏi đã gửi! Chúng tôi sẽ phản hồi trong 24h.');
        });
    </script>
</body>
</html>
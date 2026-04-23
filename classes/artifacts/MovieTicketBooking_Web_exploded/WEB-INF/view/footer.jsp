<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Nhúng CSS và Font --%>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="styles.css">
<footer class="footer">
    <div style="max-width: var(--max-width); margin: 0 auto; padding: 0 var(--spacing-md);">
        <div class="footer-links">
            <a href="about">Giới Thiệu</a>
            <a href="news">Tin Tức</a>
            <a href="careers">Tuyển Dụng</a>
            <a href="contact">Liên Hệ</a>
            <a href="privacy">Chính Sách Bảo Mật</a>
        </div>
        <p>&copy; 2025 MyCinema. Hệ thống rạp chiếu phim hiện đại bậc nhất.</p>
    </div>
</footer>

<script>
    // Script xử lý Dropdown Menu
    document.addEventListener('DOMContentLoaded', function() {
        const movieLi = document.querySelector('.has-dropdown');
        const dropdown = document.querySelector('.dropdown');
        const movieLink = document.getElementById('movieLink');

        if (movieLink) {
            movieLink.addEventListener('click', function(e) {
                e.preventDefault();
                movieLi.classList.toggle('active');
            });
        }

        document.addEventListener('click', function(e) {
            if (movieLi && !movieLi.contains(e.target)) {
                movieLi.classList.remove('active');
            }
        });

        if (dropdown) {
            const dropdownLinks = dropdown.querySelectorAll('a');
            dropdownLinks.forEach(link => {
                link.addEventListener('click', function() {
                    if (movieLi) movieLi.classList.remove('active');
                    window.location.href = this.href;
                });
            });
        }
    });
</script>
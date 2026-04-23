<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch Sử Đặt Vé - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="styles-bookings.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <main class="bookings-container">
        <section class="hero">
            <div class="hero-banner" style="background-image: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('https://via.placeholder.com/1400x500?text=My+Bookings');"></div>
            <div class="hero-overlay">
                <h1>Lịch Sử Đặt Vé</h1>
                <p>Quản lý các vé đã đặt của bạn.</p>
            </div>
        </section>

        <div class="filter-bar">
            <select class="filter-select">
                <option>Tất Cả</option>
                <option>Đã Xác Nhận</option>
                <option>Đã Hủy</option>
                <option>Đang Chờ</option>
            </select>
            <select class="filter-select">
                <option>Tháng Này</option>
                <option>Tuần Này</option>
                <option>Tất Cả</option>
            </select>
            <button class="filter-btn">Lọc</button>
        </div>

        <table class="bookings-table">
            <thead>
                <tr>
                    <th>Ngày Đặt</th>
                    <th>Phim</th>
                    <th>Rạp & Suất</th>
                    <th>Ghế</th>
                    <th>Giá</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>09/11/2025</td>
                    <td>Quái Thú Vô Hình</td>
                    <td>Cinestar Q6 - 16:30</td>
                    <td>B3, B10</td>
                    <td>110,000 VNĐ</td>
                    <td><span class="status-badge status-confirmed">Đã Xác Nhận</span></td>
                    <td>
                        <a href="#" class="action-btn">Xem Vé</a>
                        <button class="action-btn">Hủy</button>
                    </td>
                </tr>
                <tr>
                    <td>05/11/2025</td>
                    <td>Avengers: Endgame</td>
                    <td>Cinestar Hai Bà Trưng - 19:00</td>
                    <td>A5, B2</td>
                    <td>90,000 VNĐ</td>
                    <td><span class="status-badge status-confirmed">Đã Xác Nhận</span></td>
                    <td>
                        <a href="#" class="action-btn">Xem Vé</a>
                        <button class="action-btn">Hủy</button>
                    </td>
                </tr>
                <tr>
                    <td>01/11/2025</td>
                    <td>Parasite</td>
                    <td>Cinestar Quốc Thanh - 14:30</td>
                    <td>C4</td>
                    <td>45,000 VNĐ</td>
                    <td><span class="status-badge status-cancelled">Đã Hủy</span></td>
                    <td>
                        <a href="#" class="action-btn">Chi Tiết</a>
                    </td>
                </tr>
                <tr>
                    <td>28/10/2025</td>
                    <td>Joker</td>
                    <td>Cinestar Hai Bà Trưng - 20:00</td>
                    <td>D2, D3</td>
                    <td>90,000 VNĐ</td>
                    <td><span class="status-badge status-confirmed">Đã Xác Nhận</span></td>
                    <td>
                        <a href="#" class="action-btn">Xem Vé</a>
                        <button class="action-btn">Hủy</button>
                    </td>
                </tr>
                <tr>
                    <td>25/10/2025</td>
                    <td>Frozen 2</td>
                    <td>Cinestar Q6 - 14:00</td>
                    <td>E6</td>
                    <td>45,000 VNĐ</td>
                    <td><span class="status-badge status-pending">Đang Chờ</span></td>
                    <td>
                        <button class="action-btn">Xác Nhận</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <div style="text-align: center; margin-top: var(--spacing-lg);">
            <a href="#" class="btn" style="width: 200px;">Tải Vé PDF</a>
            <button class="btn btn-secondary" style="width: 200px; margin-left: var(--spacing-md);">Xóa Lịch Sử</button>
        </div>
    </main>

    <jsp:include page="footer.jsp" />

    <script>
        // Mock filter
        const filterSelects = document.querySelectorAll('.filter-select');
        filterSelects.forEach(select => {
            select.addEventListener('change', function() {
                // Mock filter logic: reload table
                console.log('Filtering by:', this.value);
            });
        });
        // Mock action buttons
        document.querySelectorAll('.action-btn').forEach(btn => {
            btn.addEventListener('click', function(e) {
                e.preventDefault();
                alert('Tính năng này sẽ được triển khai sau!');
            });
        });
    </script>
</body>
</html>
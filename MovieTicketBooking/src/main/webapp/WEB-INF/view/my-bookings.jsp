<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
                <c:forEach items="${ticketList}" var="ticket">
                    <tr>
                        <td>
                            <c:out value="${ticket.createdAt}" />
                        </td>
                        <td><c:out value="${ticket.showTime.movie.name}" /></td>
                        <td>
                            <c:out value="${ticket.showTime.cinema.name}" /> - <c:out value="${ticket.showTime.room.name}" />
                            <br/>
                            <span style="font-size: 12px; color: var(--text-muted);">
                                <c:out value="${ticket.showTime.startTime}" />
                            </span>
                        </td>
                        <td><c:out value="${ticket.seats}" /></td>
                        <td>
                            <span style="font-weight: 600;">
                                <c:out value="${ticket.totalPrice}" /> đ
                            </span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${ticket.status == 'PAID'}">
                                    <span class="status-badge status-confirmed">Đã Thanh Toán</span>
                                </c:when>
                                <c:when test="${ticket.status == 'UNPAID'}">
                                    <span class="status-badge status-pending">Chờ Thanh Toán</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status-badge status-cancelled">Đã Hủy</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${ticket.status == 'UNPAID'}">
                                    <a href="${pageContext.request.contextPath}/payment-page?ticketId=${ticket.id}" class="action-btn" style="background-color: var(--primary-color); color: #fff; text-decoration: none; padding: 5px 10px; border-radius: 4px;">Thanh toán</a>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: var(--text-muted); font-size: 12px;">N/A</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty ticketList}">
                    <tr>
                        <td colspan="7" style="text-align: center; color: var(--text-muted); padding: var(--spacing-lg);">Bạn chưa có lịch sử đặt vé nào.</td>
                    </tr>
                </c:if>
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
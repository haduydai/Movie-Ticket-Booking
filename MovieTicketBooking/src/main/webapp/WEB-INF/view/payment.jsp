<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh Toán - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <main class="payment-container">
        <section class="hero">
            <div class="hero-banner" style="background-image: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('https://via.placeholder.com/1400x500?text=Payment');"></div>
            <div class="hero-overlay">
                <h1>Thanh Toán</h1>
                <p>Xác nhận và thanh toán đơn vé của bạn.</p>
            </div>
        </section>

        <div class="payment-hero">
            
            <section class="payment-summary">
                <h2>Tóm Tắt Đơn Hàng</h2>
                <div class="summary-item">
                    <span>Phim: <c:out value="${ticket.showTime.movie.name}" /></span>
                    <span><c:out value="${ticket.showTime.cinema.name}" /> - <c:out value="${ticket.showTime.room.name}" /></span>
                </div>
                <div class="summary-item">
                    <span>Ghế: <c:out value="${ticket.seats}" /></span>
                    <span>Suất: <c:out value="${ticket.showTime.startTime}" /></span>
                </div>
                <div class="summary-item">
                    <span>Phương thức thanh toán ban đầu</span>
                    <span><c:out value="${ticket.paymentMethod}" /></span>
                </div>
                <div class="summary-total">
                    <span>Tổng Tiền</span>
                    <span><c:out value="${ticket.totalPrice}" /> VNĐ</span>
                </div>
            </section>

            
            <section class="payment-methods">
                <h2>Phương Thức Thanh Toán</h2>
                <div class="method-option active">
                    <span class="method-icon">VNP</span>
                    <span>Ví Điện Tử (Momo/ZaloPay)</span>
                </div>
                <div class="method-option">
                    <span class="method-icon">CC</span>
                    <span>Thẻ Tín Dụng/Thẻ Ghi Nợ</span>
                </div>
                <div class="method-option">
                    <span class="method-icon">COD</span>
                    <span>Thanh Toán Khi Nhận Vé Tại Rạp</span>
                </div>
                <div class="method-option">
                    <span class="method-icon">BANK</span>
                    <span>Chuyển Khoản Ngân Hàng</span>
                </div>

                <div class="promo-code">
                    <input type="text" placeholder="Nhập mã ưu đãi">
                    <button type="button">Áp Dụng</button>
                </div>

                <form action ="${pageContext.request.contextPath}/payment" method ="get">
                    <input type ="hidden" name="ticketId" value="${ticket.id}">
                    <button type="submit" class="pay-btn">Thanh Toán ${ticket.totalPrice} VNĐ</button>
                </form>

                <p style="color: var(--text-muted); font-size: 12px; text-align: center; margin-top: var(--spacing-md);">* Vé không hoàn tiền. Áp dụng ưu đãi nếu hợp lệ.</p>
            </section>
        </div>
    </main>

    <jsp:include page="footer.jsp" />
</body>
</html>
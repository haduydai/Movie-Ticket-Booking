<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Chọn Ghế - ${showTime.movie.name}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <style>
        /* Ghi đè CSS .seat-grid để phù hợp với số cột động từ Database */
        .seat-grid {
            /* Cột đầu tiên 40px cho chữ cái (A,B,C...), các cột sau chia đều */
            grid-template-columns: 40px repeat(${showTime.room.numberOfColumns}, 1fr) !important;
            /* Cho phép lưới mở rộng nếu phòng quá lớn */
            max-width: fit-content !important; 
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <main>
        <%-- Movie Hero (Tái sử dụng style của trang chi tiết nhưng gọn hơn) --%>
        <section class="movie-hero" style="min-height: auto; padding: 20px 0;">
            <div class="movie-details" style="width: 100%; text-align: center; padding: 0;">
                <h1 style="font-size: 24px; margin-bottom: 5px;">${showTime.movie.name}</h1>
                <p style="font-size: 16px; color: #ddd;">
                    ${showTime.cinema.name} - ${showTime.room.name} | 
                    <span style="color: var(--secondary-color);">
                        <fmt:parseDate value="${showTime.startTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                        <fmt:formatDate value="${parsedDate}" pattern="HH:mm - dd/MM/yyyy" />
                    </span>
                </p>
            </div>
        </section>

        <%-- Seat Map Section (Copy cấu trúc từ movieDetail.jsp) --%>
        <section class="seat-map active" style="display: block; margin-top: 20px;">
            <div class="seat-map-header">
                <h3>Sơ Đồ Ghế</h3>
                <p>Giá vé: <fmt:formatNumber value="${showTime.pricePerTicket}" type="currency" currencySymbol="VNĐ"/></p>
            </div>
            
            <div class="seat-legend">
                <div class="legend-item"><div class="legend-color available"></div><span>Ghế thường</span></div>
                <div class="legend-item"><div class="legend-color selected"></div><span>Ghế chọn</span></div>
                <div class="legend-item"><div class="legend-color taken"></div><span>Ghế đã đặt</span></div>
            </div>
            
            <div class="screen">MÀN HÌNH</div>

            <div class="seat-grid">
            	<c:forEach var="seat" items="${seatList }" varStatus="vs">
            		<c:if test="${vs.index % cols == 0 }">
            			<div class="seat-cell">${alphabet[vs.index / cols] }</div>
            		</c:if>
					<div class="seat-cell ${statusClass = (seat.bookedBy != null) ? 'taken' : 'available'}"
					     data-seat="${seat.seatName}"
					     onclick="toggleSeat(this, '${seat.seatName}')">
					     ${seat.seatName}
					     
					</div>
            	</c:forEach>
			</div>
        </section>

        <%-- Booking Summary (Copy cấu trúc từ movieDetail.jsp) --%>
        <div class="booking-summary">
            <h2>Tổng Kết Đặt Vé</h2>
            <div class="total-price" id="total-price-display">Tổng Tiền: 0 VNĐ</div>
            <p id="seats-display" style="color: #bbb; margin-bottom: 15px;">Ghế đang chọn: Chưa chọn</p>
            
            <%-- Form ẩn để gửi dữ liệu đi --%>
            <form action="checkout" method="post" id="bookingForm">
                <input type="hidden" name="showtimeId" value="${showTime.id}">
                <input type="hidden" name="selectedSeats" id="inputSeats">
                <input type="hidden" name="totalPrice" id="inputTotal">
                
                <button type="button" class="book-btn" onclick="submitBooking()">Xác Nhận & Thanh Toán</button>
            </form>
            
            <p style="color: var(--text-muted); font-size: 12px; text-align: center; margin-top: var(--spacing-md);">
                * Vé không hoàn tiền. Áp dụng ưu đãi nếu đủ điều kiện.
            </p>
        </div>
    </main>

    <jsp:include page="footer.jsp" />

    <script>
        const ticketPrice = ${showTime.pricePerTicket};
        let selectedSeats = [];

        function toggleSeat(element, seatName) {
            // Không cho chọn ghế đã có người đặt (class .taken)
            if (element.classList.contains('taken')) return;

            // Toggle class .selected (đã có trong styles.css)
            element.classList.toggle('selected');

            if (element.classList.contains('selected')) {
                selectedSeats.push(seatName);
            } else {
                const index = selectedSeats.indexOf(seatName);
                if (index > -1) {
                    selectedSeats.splice(index, 1);
                }
            }
            updateSummary();
        }

        function updateSummary() {
            const count = selectedSeats.length;
            const total = count * ticketPrice;
            
            // Cập nhật text hiển thị
            document.getElementById('total-price-display').innerText = 
                `Tổng Tiền: ` + total.toLocaleString('vi-VN') + ` VNĐ (` + count + ` ghế)`;
            
            document.getElementById('seats-display').innerText = 
                `Ghế đang chọn: ` + (count > 0 ? selectedSeats.join(', ') : 'Chưa chọn');

            // Cập nhật giá trị input ẩn form
            document.getElementById('inputSeats').value = selectedSeats.join(',');
            document.getElementById('inputTotal').value = total;
        }

        function submitBooking() {
            if (selectedSeats.length === 0) {
                alert("Vui lòng chọn ít nhất 1 ghế!");
                return;
            }
            document.getElementById('bookingForm').submit();
        }
    </script>
</body>
</html>
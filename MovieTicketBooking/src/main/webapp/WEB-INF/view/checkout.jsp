<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thanh Toán - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        .timer-box {
            background: rgba(229, 9, 20, 0.1);
            border: 1px solid var(--primary-color);
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            text-align: center;
            transition: all 0.3s ease;
        }
        .timer-text {
            margin: 0;
            font-size: 16px;
            font-weight: 600;
            color: #ff4d4d;
        }
        .timer-num {
            font-size: 24px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            display: inline-block;
            min-width: 30px;
            color: #ff4d4d;
            animation: pulse 1s infinite alternate;
        }
        @keyframes pulse {
            from { transform: scale(1); opacity: 0.8; }
            to { transform: scale(1.1); opacity: 1; }
        }
        .error-alert {
            background-color: rgba(229, 9, 20, 0.15);
            color: #ff4d4d;
            border: 1px solid #ff4d4d;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <main class="auth-container">
        <div class="auth-form" style="max-width: 600px;">
            <h2 style="text-align: center; color: var(--primary-color);">XÁC NHẬN ĐẶT VÉ</h2>
            
            <c:if test="${not empty error}">
                <div class="error-alert">
                    ${error}
                </div>
            </c:if>

            <div id="timer-container" class="timer-box">
                <p class="timer-text">
                    Thời gian giữ ghế còn lại: <span id="countdown" class="timer-num">${remainingSeconds != null ? remainingSeconds : 30}</span> giây
                </p>
            </div>
            
            <div style="background: #141414; padding: 20px; border-radius: 8px; margin-bottom: 20px;">
                <p><strong>Khách hàng:</strong> ${sessionScope.user.username}</p>
                <p><strong>Ghế đã chọn:</strong> ${selectedSeats}</p>
                <p><strong>Tổng tiền:</strong> <span style="color: #e50914; font-size: 20px; font-weight: bold;">${totalPrice} đ</span></p>
            </div>

            <form action="checkout" method="post" id="paymentForm">
                <input type="hidden" name="action" value="PAY">
                <input type="hidden" name="ticketId" value="${ticketId}">
                <input type="hidden" name="showtimeId" value="${showtimeId}">
                <input type="hidden" name="selectedSeats" value="${selectedSeats}">
                <input type="hidden" name="totalPrice" value="${totalPrice}">

                <div class="form-group-auth">
                    <label>Phương thức thanh toán</label>
                    <select name="paymentMethod" style="width: 100%; padding: 10px; border-radius: 4px;">
                        <option value="ONLINE">Ví điện tử / Thẻ (Mô phỏng)</option>
                        <option value="CASH">Thanh toán tại quầy</option>
                    </select>
                </div>

                <button type="submit" id="submitBtn" class="auth-btn">THANH TOÁN & XÁC NHẬN</button>
                <a href="${pageContext.request.contextPath}/select-seat?showtimeId=${showtimeId}" style="display:block; text-align:center; color:#aaa; margin-top:10px;">Quay lại chọn ghế</a>
            </form>
        </div>
    </main>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            let timeLeft = parseInt("${remainingSeconds != null ? remainingSeconds : 30}", 10);
            const countdownEl = document.getElementById("countdown");
            const submitBtn = document.getElementById("submitBtn");
            const timerContainer = document.getElementById("timer-container");

            function updateUI() {
                if (timeLeft <= 0) {
                    countdownEl.innerText = "0";
                    submitBtn.disabled = true;
                    submitBtn.innerText = "ĐÃ HẾT HẠN GIỮ GHẾ";
                    submitBtn.style.backgroundColor = "#555";
                    submitBtn.style.cursor = "not-allowed";
                    
                    timerContainer.style.background = "rgba(85, 85, 85, 0.1)";
                    timerContainer.style.borderColor = "#555";
                    timerContainer.querySelector("p").style.color = "#aaa";
                    timerContainer.querySelector("p").innerHTML = "Thời gian giữ ghế đã hết hạn! Vui lòng quay lại chọn ghế.";
                    clearInterval(interval);
                } else {
                    countdownEl.innerText = timeLeft;
                }
            }

            updateUI();
            
            const interval = setInterval(function() {
                timeLeft--;
                updateUI();
            }, 1000);

            // Prevent double submission or submitting expired form
            document.getElementById("paymentForm").addEventListener("submit", function(e) {
                if (timeLeft <= 0) {
                    e.preventDefault();
                    alert("Thời gian giữ ghế đã hết hạn! Vui lòng quay lại chọn ghế.");
                }
            });
        });
    </script>
</body>
</html>
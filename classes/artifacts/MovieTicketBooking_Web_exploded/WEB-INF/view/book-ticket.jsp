<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Vé - ${movieName}</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        /* CSS bổ sung để xử lý ẩn/hiện Tab nội dung */
        .showtime-container {
            display: none; /* Mặc định ẩn */
            animation: fadeIn 0.5s ease-in-out;
        }
        .showtime-container.active {
            display: block; /* Hiện khi có class active */
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }
        
        /* Container riêng cho trang booking */
        .booking-wrapper {
            max-width: 1000px;
            margin: 0 auto;
            padding: var(--spacing-lg) var(--spacing-md);
            min-height: 60vh;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <main class="booking-wrapper">
        <div class="section">
            <h2 style="color: var(--primary-color); text-align: center; margin-bottom: 5px;">
                LỊCH CHIẾU
            </h2>
            <h3 style="color: var(--text-color); text-align: center; margin-bottom: 30px; font-weight: 300;">
                Phim: <span style="font-weight: bold; text-transform: uppercase;">${movieName}</span>
            </h3>

            <div class="date-tabs">
                <c:forEach items="${next7Days}" var="date" varStatus="loop">
                    <div class="date-tab ${loop.index == 0 ? 'active' : ''}" 
                         onclick="selectDate('${date}', this)">
                        <span>${date.dayOfMonth}/${date.monthValue}</span>
                    </div>
                </c:forEach>
            </div>

            <c:forEach items="${next7Days}" var="date" varStatus="loop">
			    <div id="date-${date}" class="showtime-container ${loop.index == 0 ? 'active' : ''}">
			        
			        <c:set var="hasShowTime" value="false" />
			        <c:set var="prevCinema" value="-1" />
			        <c:set var="prevRoom" value="-1" />
			
			        <c:forEach items="${allShowTimes}" var="st">
			
			            <c:if test="${st.startTime.toLocalDate().toString() == date.toString()}">
			
			                <c:set var="hasShowTime" value="true" />
			
			                <%-- Nếu chuyển sang rạp/phòng mới thì đóng list cũ (nếu list cũ đã bắt đầu) --%>
			                <c:if test="${(prevCinema ne -1 or prevRoom ne -1) and (prevCinema ne st.cinema.id or prevRoom ne st.room.id)}">
			                        </div> <!-- đóng showtime-list -->
			                    </div> <!-- đóng theater-item -->
			                </c:if>
			
			                <%-- Nếu là rạp/phòng mới thì mở block mới --%>
			                <c:if test="${prevCinema ne st.cinema.id or prevRoom ne st.room.id}">
			                    <div class="theater-item active" style="border: 1px solid #aa0000; margin-bottom:16px; padding:15px;">
			                        <div class="theater-header">
			                            <div>
			                                <span class="theater-name">${st.cinema.name}</span>
			                                <span class="theater-location" style="display:block; font-size: 12px; color: #aaa; margin-top: 4px;">
			                                    ${st.cinema.address} - Phòng: ${st.room.name}
			                                </span>
			                            </div>
			                        </div>
			
			                        <div class="showtime-list" style="display:flex; gap:8px; flex-wrap:wrap; margin-top:12px;">
			                </c:if>
			
			                <%-- Showtime in cùng block --%>
			                <a href="select-seat?showtimeId=${st.id}" class="showtime-slot" 
			                    style="background:#c40000; text-decoration:none; color:white; text-align:center; padding:10px 14px; border-radius:6px;">
			                    <span style="font-size: 18px; font-weight:bold;">
			                        ${st.startTime.toLocalTime()}
			                    </span><br>
			                    <span style="font-size: 12px; font-weight: normal; color:#fff;">
			                        <fmt:formatNumber value="${st.pricePerTicket}" type="currency" currencySymbol="₫"/>
			                    </span>
			                </a>
			
			                <%-- Lưu rạp/phòng hiện tại --%>
			                <c:set var="prevCinema" value="${st.cinema.id}" />
			                <c:set var="prevRoom" value="${st.room.id}" />
			
			            </c:if>
			
			        </c:forEach>
			
			        <%-- nếu có showtime thì đóng block cuối cùng --%>
			        <c:if test="${hasShowTime}">
			                </div> <!-- đóng showtime-list -->
			            </div> <!-- đóng theater-item -->
			        </c:if>
			
			        <%-- nếu không có showtime --%>
			        <c:if test="${not hasShowTime}">
			            <div style="text-align: center; padding: 40px; border: 1px dashed #444; border-radius: 8px; color: var(--text-muted);">
			                <p>Chưa có lịch chiếu cho ngày này.</p>
			            </div>
			        </c:if>
			
			    </div>
			</c:forEach>

        </div>
    </main>

    <jsp:include page="footer.jsp" />

    <script>
        function selectDate(dateStr, element) {
            // 1. Xóa class active ở tất cả các tab và nội dung cũ
            document.querySelectorAll('.date-tab').forEach(el => el.classList.remove('active'));
            document.querySelectorAll('.showtime-container').forEach(el => el.classList.remove('active'));

            // 2. Thêm class active cho tab vừa bấm và nội dung tương ứng
            element.classList.add('active');
            
            const content = document.getElementById('date-' + dateStr);
            if (content) {
                content.classList.add('active');
            }
        }
    </script>
</body> 
</html>
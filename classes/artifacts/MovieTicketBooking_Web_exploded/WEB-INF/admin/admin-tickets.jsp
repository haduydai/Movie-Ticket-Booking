<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
	
</head>
<body>
	<div class="container-fluid">
		<c:if test="${not empty sessionScope.ticketMessage }">
			<div id="message"
		         class="bg-success text-white px-3 py-2 rounded d-inline-flex align-items-center gap-2">
		        <span>${sessionScope.ticketMessage}</span>
		        <button type="button"
		                class="btn-close btn-close-white"
		                aria-label="Close"
		                onclick="hideMessage()">
		        </button>
	    	</div>
		</c:if>
		<h1 class="mb-0">Quản lý vé</h1>
		<br>
		<form action="${pageContext.request.contextPath }/admin/ticket/search">
			<div class="d-flex justify-content-between align-items-center mb-3">
			    <div class="input-group" style="width: 300px;">
			        <input type="text"
			               name="searchInput"
			               class="form-control"
			               placeholder="Tìm ID, người đặt">
			        <input type="submit" class="btn btn-primary" value="Tìm kiếm">
			    </div>
			</div>
		</form>
		
		
		<table class="table">
			<thread>
				<tr>
					<th style="width:15%">ID vé</th>
					<th style="width:10%">Người đặt</th>
					<th>Phòng</th>
					<th style="width:10%">Ghế</th>
					<th style="width:8%">Giá tiền</th>
					<th style="width:8%">Phương thức</th>
					<th style="width:10%">Mua vào lúc</th>
					<th style="width:10%">Trạng thái</th>
					<th style="width:8%">Hành động</th>
				</tr>
			</thread>
			<tbody>
				<c:forEach var="t" items="${listTickets}">
					<tr>
						<td>${t.uid}</td>
						<td>${t.user.username }</td>
						<td>${t.showTime.movie.name} - ${ t.showTime.cinema.name},
							 ${t.showTime.room.name} - 
							 <fmt:formatDate value="${t.showTime.startTimeAsDate }" pattern="dd/MM/yyyy HH:mm"/></td>
						<td>${t.seats}</td>
						<td>
							<fmt:formatNumber 
							        value="${t.totalPrice}" 
							        type="currency" 
							        currencySymbol="₫" 
							        maxFractionDigits="0"/>
						</td>
						<td>${t.paymentMethod }</td>
						<td>
							<fmt:formatDate 
							        value="${t.createdAtAsDate}" 
							        pattern="dd/MM/yyyy HH:mm"/>
						</td>
						<td>
							<c:choose>
						        <c:when test="${t.status == 'UNPAID'}">
						            <span class="badge bg-warning text-dark">Chưa thanh toán</span>
						        </c:when>
						
						        <c:when test="${t.status == 'PAID'}">
						            <span class="badge bg-success">Đã thanh toán</span>
						        </c:when>
						
						        <c:when test="${t.status == 'CANCELLED'}">
						            <span class="badge bg-danger">Đã huỷ</span>
						        </c:when>
						
						        <c:when test="${t.status == 'CHECKEDIN'}">
						            <span class="badge bg-primary">Đã check-in</span>
						        </c:when>
						
						        <c:otherwise>
						            
						        </c:otherwise>
						    </c:choose>
						</td>
						<td>
							 <c:choose>
						        <c:when test="${t.status == 'UNPAID'}">
						            <form action="${pageContext.request.contextPath}/admin/ticket/action" method="post">
						                <input type="hidden" name="id" value="${t.id}">
						                <input type="hidden" name="action" value="pay">
						                <button type="submit" class="btn btn-warning">
						                    Thanh toán
						                </button>
						            </form>
						        </c:when>
						        <c:when test="${t.status == 'PAID' or (t.status == 'UNPAID' and t.paymentMethod == 'CASH')}">
						            <form action="${pageContext.request.contextPath}/admin/ticket/action" method="post">
						                <input type="hidden" name="id" value="${t.id}">
						                <input type="hidden" name="action" value="checkin">
						                <button type="submit" class="btn btn-success">
						                    Check in
						                </button>
						            </form>
						        </c:when>
						        <c:otherwise>
						            
						        </c:otherwise>
						
						    </c:choose>
							
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	
	<script >
		
		function hideMessage() {
			const msg = document.getElementById("message");
			if (msg) {
				msg.classList.add("d-none");
			}
		}
	</script>
</body>
</html>
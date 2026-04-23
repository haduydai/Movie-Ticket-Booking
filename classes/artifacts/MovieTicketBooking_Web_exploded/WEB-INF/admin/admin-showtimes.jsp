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
		<c:if test="${not empty sessionScope.showtimeMessage }">
			<div id="message"
		         class="bg-success text-white px-3 py-2 rounded d-inline-flex align-items-center gap-2">
		        <span>${sessionScope.showtimeMessage}</span>
		        <button type="button"
		                class="btn-close btn-close-white"
		                aria-label="Close"
		                onclick="hideMessage()">
		        </button>
	    	</div>
		</c:if>
		
		<h1>Quản lý suất chiếu</h1>
		
		<div  class="mt-2 mb-2">
			<a href="${pageContext.request.contextPath }/admin/showtime/add" class="btn btn-primary">Tạo suất chiếu</a>
		</div>
		<table class="table">
			<thread>
				<tr>
					<th style="width:15%">Rạp</th>
					<th style="width:10%">Phòng</th>
					<th style="width:25%">Tên phim</th>
					<th style="width:10%">Giá vé</th>
					<th style="width:15%">Thời gian bắt đầu</th>
					<th style="width:15%">Tạo vào lúc</th>
					<th>Hành động</th>
				</tr>
			</thread>
			<tbody>
				<c:forEach var="st" items="${listShowTimes}">
					<tr>
						<td>${st.cinema.name }</td>
						<td>${st.room.name }</td>
						<td>${st.movie.name }</td>
						<td>
							<fmt:formatNumber 
						        value="${st.pricePerTicket}" 
						        type="currency" 
						        currencySymbol="₫" 
						        maxFractionDigits="0"/>
						</td>
						<td>
							<fmt:formatDate 
						        value="${st.startTimeAsDate}" 
						        pattern="dd/MM/yyyy HH:mm"/>
						</td>
						<td>
							<fmt:formatDate 
						        value="${st.createdAtAsDate}" 
						        pattern="dd/MM/yyyy HH:mm"/>
						</td>
						<td>
							<div class="d-flex gap-2">
								<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" data-st-id="${st.id}">
									Xoá
								</button>
								
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- Thông báo xác nhận xoá phim -->
	<div class="modal fade" id="confirmDeleteModal" tabindex="-1">
	    <div class="modal-dialog modal-dialog-centered">
	        <div class="modal-content">
	
	            <div class="modal-header">
	                <h5 class="modal-title text-danger">Xác nhận xoá</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	            </div>
	
	            <div class="modal-body text-center">
	                <p>Bạn có chắc chắn muốn xoá suất chiếu này không?</p>
	            </div>
	
	            <div class="modal-footer justify-content-center">
	                <form id="deleteForm" 
	                      action="${pageContext.request.contextPath}/admin/showtime/delete" 
	                      method="post">
	                    <input type="hidden" name="id" id="deleteShowtimeId">
	                    <button type="submit" class="btn btn-danger">Xoá</button>
	                </form>
	
	                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
	                    Không
	                </button>
	            </div>
	
	        </div>
	    </div>
	</div>
	
	<script >
		const deleteModal = document.getElementById('confirmDeleteModal');
	
		deleteModal.addEventListener('show.bs.modal', function (event) {
		    const button = event.relatedTarget;
		    const stId = button.getAttribute('data-st-id');
	
		    document.getElementById('deleteShowtimeId').value = stId;
		});
		function hideMessage() {
			const msg = document.getElementById("message");
			if (msg) {
				msg.classList.add("d-none");
			}
		}
	</script>
</body>
</html>
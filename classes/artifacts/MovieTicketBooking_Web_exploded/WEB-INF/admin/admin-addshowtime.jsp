<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
	
</head>
<body>
	<div class="container-fluid">
		<form action="${pageContext.request.contextPath }/admin/showtimes" method="post">
			<input type="submit" value="Quay lại" class="btn btn-primary">
		</form>
		<c:if test="${not empty errorMessage }">
			<div id="message"
		         class="bg-danger text text-white px-3 py-2 rounded d-inline-flex align-items-center gap-2 mt-2">
		        <span>${errorMessage}</span>
		        <button type="button"
		                class="btn-close btn-close-white"
		                aria-label="Close"
		                onclick="hideMessage()">
		        </button>
	    	</div>
		</c:if>
		
		<h1>Tạo suất chiếu</h1>
		<form action="${pageContext.request.contextPath }/admin/showtime/add" method="post">
			<table>
				<tr>
					<td><label for="cinemaId">Rạp</label></td>
					<td>
						<select name="cinemaId" id="cinemaSelect">
							<option value="">--- Chọn rạp ---</option>
							<c:forEach var="cine" items="${listCinemas }">
								<option value="${cine.id }">${cine.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label for="roomId">Phòng</label></td>
					<td>
						<select name="roomId"  id="roomSelect" >
							<option value="">--- Chọn phòng ---</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><label for="movieId">Phim</label></td>
					<td>
						<select name="movieId">
							<option value="">--- Chọn phim ---</option>
							<c:forEach var="m" items="${listMovies}">
								<option value="${m.id}">${m.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label for="price">Giá vé</label></td>
					<td><input name="price" type="number" min=0 class="form-control"></td>
				</tr>
				<tr>
					<td><label for="startTime">Thời gian bắt đầu</label></td>
					<td><input name="startTime" type="datetime-local" class="form-control"></td>
				</tr>
			</table>
			<input type="submit" value="Thêm" class="btn btn-success">
		</form>
	</div>
	<script >
		cinemaSelect.addEventListener("change", function () {
		    const cinemaId = this.value;
		    if (!cinemaId) return;
	
		    fetch(`${pageContext.request.contextPath}/admin/rooms?cinemaId=` + cinemaId)
		        .then(res => res.text())
		        .then(html => {
		            roomSelect.innerHTML =
		                '<option value="">--- Chọn phòng ---</option>' + html;
		        });
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
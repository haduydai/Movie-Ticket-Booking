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
		<form action="${pageContext.request.contextPath }/admin/movies" method="post">
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
		
		<h1>Sửa thông tin phim</h1>
		<form action="${pageContext.request.contextPath }/admin/movie/edit" method="post">
			<input type="hidden" name="id" value="${movie.id }">
			<table>
				<tr>
					<td><label for="name">Tên phim</label></td>
					<td><input name="name" type="text" style="width:400px" class="form-control" value="${movie.name }"></td>
				</tr>
				<tr>
					<td><label for="type">Thể loại</label></td>
					<td><input name="type" type="text" style="width:400px" class="form-control" value="${movie.type }"></td>
				</tr>
				<tr>
					<td><label for="directorName">Tên đạo diễn</label></td>
					<td><input name="directorName" type="text" style="width:400px" class="form-control" value="${movie.directorName}"></td>
				</tr>
				<tr>
					<td><label for="actorsName">Tên các diễn viên</label></td>
					<td><input name="actorsName" type="text" style="width:400px" class="form-control" value="${movie.actorsName}"></td>
				</tr>
				<tr>
					<td><label for="duration">Thời lượng</label></td>
					<td><input name="duration" type="number" min="0" style="width:400px" class="form-control" value="${movie.duration}"></td>
				</tr>
				<tr>
					<td><label for="country">Quốc gia</label></td>
					<td><input name="country" type="text" style="width:400px" class="form-control" value="${movie.country}"></td>
				</tr>
				<tr>
					<td><label for="imageUrl">Đường dẫn hình ảnh poster</label></td>
					<td><input name="imageUrl" type="text" style="width:1000px" class="form-control" value="${movie.imageUrl}"></td>
				</tr>
				<tr>
					<td><label for="status">Trạng thái</label></td>
					<td><select name="status">
						<option value="COMING_SOON">Sắp chiếu</option>
						<option value="NOW_SHOWING">Đang chiếu</option>
						<option value="STOPPED_SHOWING">Ngừng chiếu</option>
					</select></td>
				</tr>
			</table>
			<div class="mt-2 mb-2">
				<label class="form-label">Mô tả</label>
        		<textarea name="description" rows="4" col="8" class="form-control">${movie.description}
                 </textarea>
			</div>
			<input type="submit" value="Cập nhật" class="btn btn-success">
		</form>
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
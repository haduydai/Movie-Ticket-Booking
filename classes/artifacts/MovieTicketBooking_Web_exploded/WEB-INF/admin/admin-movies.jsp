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
		<c:if test="${not empty sessionScope.movieMessage }">
			<div id="message"
		         class="bg-success text-white px-3 py-2 rounded d-inline-flex align-items-center gap-2">
		        <span>${sessionScope.movieMessage}</span>
		        <button type="button"
		                class="btn-close btn-close-white"
		                aria-label="Close"
		                onclick="hideMessage()">
		        </button>
	    	</div>
		</c:if>
		
		<h1>Quản lý phim</h1>
		
		<div  class="mt-2 mb-2">
			<a href="${pageContext.request.contextPath }/admin/movie/add" class="btn btn-primary">Thêm phim</a>
		</div>
		<table class="table">
			<thread>
				<tr>
					<th>Tên</th>
					<th>Thể loại</th>
					<th>Đạo diễn</th>
					<th>Thời lượng</th>
					<th>Quốc gia</th>
					<th>Hình ảnh</th>
					<th>Trạng thái</th>
					<th>Hành động</th>
				</tr>
			</thread>
			<tbody>
				<c:forEach var="m" items="${listMovies}">
					<tr>
						<td>${m.name }</td>
						<td>${m.type }</td>
						<td>${m.directorName }</td>
						<td>${m.duration }</td>
						<td>${m.country }</td>
						<td><img src="${m.imageUrl }" style="width:60px"></td>
						<td>
							<c:choose>
						        <c:when test="${m.movieStatus == 'COMING_SOON'}">
						            <span class="badge bg-primary">Sắp chiếu</span>
						        </c:when>
						        <c:when test="${m.movieStatus == 'NOW_SHOWING'}">
						            <span class="badge bg-success">Đang chiếu</span>
						        </c:when>
						        <c:when test="${m.movieStatus == 'STOPPED_SHOWING'}">
						            <span class="badge bg-danger">Ngừng chiếu</span>
						        </c:when>
						        <c:otherwise>
						            Không xác định
						        </c:otherwise>
						    </c:choose>
						</td>
						<td>
							<div class="d-flex gap-2">
	    						<form action="${pageContext.request.contextPath}/admin/movie/edit" method="get">
	    							<input type="hidden" name="id" value="${m.id }">
									<input type="submit" value="Sửa" class="btn btn-warning">
								</form>
								<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" data-movie-id="${m.id}">
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
	                <p>Bạn có chắc chắn muốn xoá phim này không?</p>
	            </div>
	
	            <div class="modal-footer justify-content-center">
	                <form id="deleteForm" 
	                      action="${pageContext.request.contextPath}/admin/movie/delete" 
	                      method="post">
	                    <input type="hidden" name="id" id="deleteMovieId">
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
		    const movieId = button.getAttribute('data-movie-id');
	
		    document.getElementById('deleteMovieId').value = movieId;
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
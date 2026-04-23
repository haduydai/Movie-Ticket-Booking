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
		<c:if test="${not empty sessionScope.userMessage }">
			<div id="message"
		         class="bg-success text-white px-3 py-2 rounded d-inline-flex align-items-center gap-2">
		        <span>${sessionScope.userMessage}</span>
		        <button type="button"
		                class="btn-close btn-close-white"
		                aria-label="Close"
		                onclick="hideMessage()">
		        </button>
	    	</div>
		</c:if>
		
		<h1>Quản lý tài khoản</h1>
		
		<div  class="mt-2 mb-2">
			<a href="${pageContext.request.contextPath }/admin/user/add" class="btn btn-primary">Thêm tài khoản</a>
		</div>
		<table class="table">
			<thread>
				<tr>
					<th style="width:30%">Tên tài khoản</th>
					<th>Email</th>
					<th style="width:20%">Số điện thoại</th>
					<th style="width:10%">Vai trò</th>
					<th style="width:10%">Hành động</th>
				</tr>
			</thread>
			<tbody>
				<c:forEach var="u" items="${listUsers}">
					<tr>
						<td>${u.username }</td>
						<td>${u.email }</td>
						<td>${u.phoneNumber }</td>
						<td>${u.role }</td>
						<td>
							<div class="d-flex gap-2">
								<button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#confirmDeleteModal" data-user-id="${u.id}">
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
	                <p>Bạn có chắc chắn muốn xoá tài khoản này không?</p>
	            </div>
	
	            <div class="modal-footer justify-content-center">
	                <form id="deleteForm" 
	                      action="${pageContext.request.contextPath}/admin/user/delete" 
	                      method="post">
	                    <input type="hidden" name="id" id="deleteUserId">
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
		    const userId = button.getAttribute('data-user-id');
	
		    document.getElementById('deleteUserId').value = userId;
		});
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
	crossorigin="anonymous">

<title>ERROR 403</title>
</head>
<body>
	<div class="container-fluid min-vh-100 d-flex justify-content-center align-items-center">
	    <div class="text-center border rounded p-5 shadow">
	        <h1 class="display-1 text-danger fw-bold">Error 403 - Forbidden</h1>
	        <h4 class="mb-3">Truy cập bị từ chối</h4>
	        <p class="text-muted">
	            Bạn không có quyền truy cập vào trang này.
	        </p>
	
	        <a href="${pageContext.request.contextPath }/login-admin"
	           class="btn btn-primary mt-3">
	            Quay về trang đăng nhập admin
	        </a>
	    </div>
	</div>
</body>
</html>
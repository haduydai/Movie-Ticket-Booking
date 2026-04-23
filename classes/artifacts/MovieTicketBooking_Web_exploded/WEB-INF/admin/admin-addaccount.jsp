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
		<form action="${pageContext.request.contextPath }/admin/users" method="post">
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
		
		<h1>Thêm tài khoản mới</h1>
		<form action="${pageContext.request.contextPath }/admin/user/add" method="post">
			<table>
				<tr>
					<td><label>Username</label></td>
					<td><input name="username" type="text" style="width:500px" class="form-control"></td>
				</tr>
				<tr>
					<td><label>Password</label></td>
					<td><input name="password" type="password" style="width:500px" class="form-control"></td>
				</tr>
				<tr>
					<td><label>Email</label></td>
					<td><input name="email" type="text" class="form-control"></td>
				</tr>
				<tr>
					<td style="width:150px"><label>Phone Number</label></td>
					<td><input name="phonenumber" type="text" class="form-control"></td>
				</tr>
				<tr>
					<td><label>Role</label></td>
					<td>
						<select name="role">
							<option value="user">Tài khoản thường</option>
							<option value="admin">Admin</option>
						</select>
					</td>
				</tr>
			</table>
			<input type="submit" value="Thêm" class="btn btn-success">
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
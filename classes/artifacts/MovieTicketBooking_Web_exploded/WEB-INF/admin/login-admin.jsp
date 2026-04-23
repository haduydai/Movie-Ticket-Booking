<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container vh-100 d-flex justify-content-center align-items-center">
    <div class="card shadow p-4" style="width: 350px;">
        <h4 class="text-center mb-4">Admin Login</h4>

        <form action="${pageContext.request.contextPath }/login-admin" method="post">
            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" name="username" class="form-control">
            </div>

            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" name="password" class="form-control">
            </div>

            <c:if test="${loginError != null }">
            	<div class="alert alert-danger py-2">
                	${loginError }
            	</div>
            </c:if>
            
            

            <button type="submit" class="btn btn-dark w-100">
                Login
            </button>
        </form>
    </div>
</div>

</body>
</html>
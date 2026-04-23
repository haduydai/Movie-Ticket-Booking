<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- Nhúng CSS và Font --%>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="styles.css">


<header>
    <div class="top-nav">
        <%-- Logo --%>
        <a href="home" class="logo">MyCinema</a>

        <ul class="nav-links">
            <li><a href="home">Trang Chủ</a></li>

            <%-- Dropdown Phim --%>
            <li class="has-dropdown">
                <input type="checkbox" id="movieToggle" class="dropdown-toggle"> 
                <label for="movieToggle" id="movieLink">Phim</label>
                <ul class="dropdown">
                    <li><a href="showing">Phim Đang Chiếu</a></li>
                    <li><a href="upcoming">Phim Sắp Chiếu</a></li>
                    <li><a href="movies">Tất Cả Phim</a></li>
                </ul>
            </li>

            <li><a href="theaters">Rạp</a></li>
            
            <%-- Link Tài Khoản chỉ hiện khi đã đăng nhập (hoặc bạn có thể để luôn hiện để redirect) --%>
            <c:if test="${not empty sessionScope.user}">
                <li><a href="profile">Hồ Sơ</a></li>
            </c:if>
        </ul>

        <%-- PHẦN XỬ LÝ ẨN/HIỆN ĐĂNG NHẬP --%>
        <div class="user-actions">
    <c:choose>
        <%-- Nếu ĐÃ đăng nhập (session có user) -> Hiện Tên & nút Logout --%>
        <c:when test="${not empty sessionScope.user}">
            <span style="color: white; margin-right: 15px;">
                Xin chào, <b>${sessionScope.user.username}</b>
            </span>
            <a href="logout" class="btn btn-secondary">Đăng Xuất</a>
        </c:when>

        <%-- Nếu CHƯA đăng nhập -> Hiện nút Login/Register --%>
        <c:otherwise>
            <a href="login" class="btn btn-secondary">Đăng Nhập</a> 
            <a href="register" class="btn">Đăng Ký</a>
        </c:otherwise>
    </c:choose>
</div>
    </div>

    <div class="search-bar-header">
        <div class="search-container">
            <form action="search" method="get">
                <input type="text" name="keyword" placeholder="Tìm kiếm phim">
                <button type="submit">Tìm Kiếm</button>
            </form>
        </div>
    </div>
</header>
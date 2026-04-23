<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Sửa lại thư viện JSTL theo yêu cầu của bạn --%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Rạp - MyCinema</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        /* CSS giữ khung ảnh rạp đẹp, không bị méo */
        .cinema-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            background-color: #222;
        }
    </style>
</head>
<body>
    
    <%-- Gọi Header chung --%>
    <jsp:include page="header.jsp" />

    <main>
        <%-- Hero Section (Giữ nguyên) --%>
        <section class="hero" style="height: 200px;">
            <div class="hero-banner" style="background-image: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('https://via.placeholder.com/1400x500?text=He+Thong+Rap+MyCinema');"></div>
            <div class="hero-overlay">
                <h1>Danh Sách Rạp MyCinema</h1>
                <p>Khám phá hệ thống rạp hiện đại tại các thành phố lớn trên toàn quốc.</p>
            </div>
        </section>

        <%-- DANH SÁCH RẠP (Lấy dữ liệu động từ Database) --%>
        <section class="section">
            
            <div class="cinema-grid">
                
                <%-- Kiểm tra nếu danh sách rỗng (tránh lỗi giao diện) --%>
                <c:if test="${empty listCinemas}">
                    <p style="color:white; font-style: italic;">Đang cập nhật danh sách rạp...</p>
                </c:if>

                <%-- Vòng lặp hiển thị từng rạp --%>
                <c:forEach items="${listCinemas}" var="c">
                    <div class="cinema-card">
                        
                        <div class="cinema-info">
                            <h3>${c.name}</h3>
                            <p><strong>Địa chỉ:</strong> ${c.address}</p>
                            <p class="cinema-hours">Giờ mở cửa: 8:00 - 24:00</p>
                            
                        </div>
                    </div>
                </c:forEach>
                
            </div>
        </section>

    </main>

    <%-- Gọi Footer chung --%>
    <jsp:include page="footer.jsp" />

</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Tin tức - MyCinema</title>
  <link rel="stylesheet" href="styles.css"/>
</head>
<body>
  <jsp:include page="/WEB-INF/view/header.jsp"/>

  <main class="static-page">
    <div class="static-card">
      <h1>Tin tức</h1>
      <p class="static-muted">Cập nhật thông báo hệ thống, ưu đãi và tin điện ảnh.</p>

      <div class="static-section">
        <h2>Thông báo</h2>
        <ul>
          <li>Trang tin tức đang được cập nhật.</li>
          <li>Bạn có thể thay nội dung này bằng dữ liệu từ DB sau.</li>
        </ul>
      </div>

      <a class="static-back" href="home">← Về trang chủ</a>
    </div>
  </main>

  <jsp:include page="/WEB-INF/view/footer.jsp"/>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Tuyển dụng - MyCinema</title>
  <link rel="stylesheet" href="styles.css"/>
</head>
<body>
  <jsp:include page="/WEB-INF/view/header.jsp"/>

  <main class="static-page">
    <div class="static-card">
      <h1>Tuyển dụng</h1>
      <p class="static-muted">MyCinema chào đón các bạn yêu điện ảnh và công nghệ.</p>

      <div class="static-section">
        <h2>Vị trí đang mở</h2>
        <ul>
          <li>Thực tập sinh Java Web</li>
          <li>CTV Nội dung / Marketing</li>
          <li>CSKH Part-time</li>
        </ul>
      </div>

      <div class="static-section">
        <h2>Liên hệ ứng tuyển</h2>
        <ul>
          <li>Email: hr@mycinema.vn</li>
          <li>Tiêu đề mail: [Ứng tuyển] - Họ tên - Vị trí</li>
        </ul>
      </div>

      <a class="static-back" href="home">← Về trang chủ</a>
    </div>
  </main>

  <jsp:include page="/WEB-INF/view/footer.jsp"/>
</body>
</html>

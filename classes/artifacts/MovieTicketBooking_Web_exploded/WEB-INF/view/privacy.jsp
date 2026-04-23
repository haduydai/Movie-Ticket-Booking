<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Chính sách bảo mật</title>
  <link rel="stylesheet" href="styles.css"/>
  <style>
    .static-wrap{max-width:1000px;margin:24px auto;padding:0 16px;}
    .static-card{background:#fff;border:1px solid #eee;border-radius:12px;padding:18px;box-shadow:0 6px 18px rgba(0,0,0,.06)}
    .static-card h1{margin:0 0 10px;font-size:24px}
    .muted{color:#666;margin:0 0 16px;line-height:1.6}
    .sec{margin-top:16px}
    .sec h2{margin:0 0 10px;font-size:16px}
    .sec ul{margin:0;padding-left:18px;line-height:1.7}
    .back{display:inline-block;margin-top:16px;text-decoration:none}
  </style>
</head>
<body>
  <jsp:include page="/WEB-INF/view/header.jsp"/>

  <main class="static-wrap">
    <div class="static-card">
      <h1>Chính sách bảo mật</h1>
      <p class="muted">Tài liệu mô tả cách MyCinema thu thập, sử dụng và bảo vệ thông tin của bạn.</p>

      <div class="sec">
        <h2>1) Thông tin thu thập</h2>
        <ul>
          <li>Thông tin tài khoản: họ tên, email, số điện thoại (nếu cung cấp).</li>
          <li>Thông tin giao dịch: đặt vé, suất chiếu, ghế, thanh toán.</li>
          <li>Dữ liệu kỹ thuật: thiết bị, trình duyệt, IP (phục vụ bảo mật/vận hành).</li>
        </ul>
      </div>

      <div class="sec">
        <h2>2) Mục đích sử dụng</h2>
        <ul>
          <li>Xử lý đặt vé, thanh toán, hỗ trợ khách hàng.</li>
          <li>Gửi thông báo liên quan đơn hàng.</li>
          <li>Cải thiện dịch vụ và trải nghiệm.</li>
        </ul>
      </div>

      <div class="sec">
        <h2>3) Chia sẻ dữ liệu</h2>
        <ul>
          <li>Không bán dữ liệu cá nhân.</li>
          <li>Chỉ chia sẻ khi cần cho thanh toán/tuân thủ pháp luật.</li>
        </ul>
      </div>

      <div class="sec">
        <h2>4) Liên hệ</h2>
        <ul>
          <li>Email: support@mycinema.vn</li>
          <li>Hotline: 1900 0000</li>
        </ul>
      </div>

      <a class="back" href="home">← Về trang chủ</a>
    </div>
  </main>

  <jsp:include page="/WEB-INF/view/footer.jsp"/>
</body>
</html>

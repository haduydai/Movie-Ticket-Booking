# Kế Hoạch Phát Triển & Phân Công Nhiệm Vụ (Project Plan)
> **Hướng dẫn form commit:** 
> - feat (Feature): Khi làm xong một tính năng mới hoặc một giải pháp hoàn chỉnh (Ví dụ: Thêm AJAX, làm xong mã OTP).
> - fix: Khi sửa một lỗi hệ thống hoặc một bug nhỏ (Ví dụ: Sửa lỗi 404, sửa lỗi nhập sai pass).
> - ui: Khi chỉ căn chỉnh lại giao diện CSS, HTML, hình ảnh mà không đụng tới logic Java.
> - refactor: Khi dọn dẹp lại code cho gọn gàng hơn mà tính năng không đổi.
> - Commit theo form sau:
> - <Loại>(Issue <Số>): <Tóm tắt vấn đề con đã giải quyết>
>    - <Chi tiết 1>(đã sửa gì thì ghi dô)(có thể có hoặc hog)
>    - <Chi tiết 2>
>   
> **Hướng dẫn sử dụng :** 
> - Đánh dấu `[x]` khi hoàn thành, `[/]` khi đang làm.
> - Điền tên vào mục **Assignee** khi nhận task.
> - Ghi chú các lỗi phát sinh (nếu có) vào mục **Ghi chú**.

## 1. Authentication (Xác thực & Bảo mật)
- [x] **1. Đăng ký (Register):** Kiểm tra validation, check trùng email, giữ lại dữ liệu khi lỗi, ẩn/hiện mật khẩu.
  - *Files:* `controller/RegisterServlet.java`, `dao/UserDAO.java`, `register.jsp`
  - *Assignee:Hà Duy Đại* 
  - *Ghi chú:*
- [ ] **2. Đăng nhập (Login):** Bắt ngoại lệ, kiểm tra cú pháp, giữ lại username khi lỗi.
  - *Files:* `controller/LoginServlet.java`, `login.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **3. Xác thực Email:** Gửi mã OTP qua email khi đăng ký.
  - *Files:* `controller/VerifyOTPServlet.java`, thư viện `JavaMail`, `verify.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **4. Quên mật khẩu:** Gửi OTP qua email để cấp lại mật khẩu.
  - *Files:* `controller/ForgotPasswordServlet.java`, `controller/ResetPasswordServlet.java`, `forgot_password.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **5. Đăng nhập Google:** Tích hợp API Google OAuth2.
  - *Files:* Tạo mới `GoogleLoginServlet.java`, `login.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **6. Đổi mật khẩu:** Yêu cầu xác thực qua email trước khi đổi.
  - *Files:* `controller/ProfileServlet.java`, `profile.jsp`
  - *Assignee:* 
  - *Ghi chú:*

## 2. Search (Tìm kiếm & Lọc)
- [ ] **7. Tìm kiếm tên phim:** Tìm gần đúng (`LIKE`) tên phim.
  - *Files:* `controller/SearchMovieServlet.java`, `dao/MovieDAO.java`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **8. Tìm kiếm tên rạp:** Liệt kê chi tiết rạp và phim đang chiếu tại rạp.
  - *Files:* `controller/TheaterServlet.java`, `dao/TheaterDAO.java`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **9. Lọc theo thể loại:** Lọc phim theo Tag, Thể loại, Quốc gia.
  - *Files:* `controller/MoviesServlet.java`, `dao/MovieDAO.java`, `movies.jsp`
  - *Assignee:* 
  - *Ghi chú:*

## 3. Đặt vé (Booking)
- [ ] **10. Chọn phim:** Hiển thị chi tiết phim, thêm video Trailer youtube.
  - *Files:* `controller/MovieDetailServlet.java`, `movieDetail.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **11. Chọn ngày giờ:** Phân luồng chọn ngày -> Giờ -> Suất chiếu.
  - *Files:* `controller/BookTicketServlet.java`, `dao/ShowtimeDAO.java`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **12. Chọn suất chiếu:** Hiển thị chính xác thông tin suất chiếu dựa trên ngày.
  - *Files:* `controller/BookTicketServlet.java`, `bookTicket.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **13. Chọn ghế:** Đổi màu ghế đã đặt, giá vé linh hoạt theo vị trí (ghế VIP).
  - *Files:* `controller/SelectSeatServlet.java`, `dao/SeatDAO.java`, `seat.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **14. Tính tổng tiền:** Cập nhật giỏ hàng/tổng tiền chính xác.
  - *Files:* `controller/CheckoutServlet.java`, JS tính tiền ở frontend.
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **15. Chọn phương thức thanh toán:** UI chọn VNPay, MoMo, QR Code.
  - *Files:* `checkout.jsp`
  - *Assignee:* 
  - *Ghi chú:*

## 4. Thanh toán (Payment & Invoice)
- [ ] **16. Tích hợp thanh toán:** Code gọi API VNPay/MoMo.
  - *Files:* `controller/PaymentServlet.java` (Tạo mới).
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **17. Xác nhận thanh toán:** IPN/Return URL hứng kết quả từ ngân hàng.
  - *Files:* `controller/PaymentReturnServlet.java` (Tạo mới).
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **18. Tạo mã in vé & QR Code:** Random mã vé độc nhất, generate QR.
  - *Files:* `dao/TicketDAO.java`, thư viện ZXing (tạo QR).
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **19. Lưu hóa đơn tự động & Xuất PDF:** Lưu vào bảng Invoices, hỗ trợ xuất PDF.
  - *Files:* Thư viện `iTextPDF`, `dao/InvoiceDAO.java`.
  - *Assignee:* 
  - *Ghi chú:*

## 5. Quản lý (Admin Management)
- [ ] **20. Quản lý phim:** API cho hình ảnh (Cloudinary/Imgur), validate form.
  - *Files:* `admin/AdminMovieServlet.java`, `admin_movies.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **21. Quản lý rạp:** Thêm trạng thái Đóng/Mở cửa.
  - *Files:* `admin/AdminTheaterServlet.java`, `admin_theaters.jsp`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **22. Quản lý suất chiếu:** Validate không được trùng giờ, xử lý ngoại lệ.
  - *Files:* `admin/AdminShowtimeServlet.java`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **23. Quản lý người dùng:** Tính năng Ban/Khóa tài khoản (Status: Active/Banned).
  - *Files:* `admin/AdminUserServlet.java`, `dao/UserDAO.java`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **24. Quản lý doanh thu:** Thống kê biểu đồ (Chart.js) theo tháng/năm.
  - *Files:* `admin/AdminDashboardServlet.java`, `admin_dashboard.jsp`
  - *Assignee:* 
  - *Ghi chú:*

## 6. UI/UX & Cài đặt
- [ ] **26. Đa ngôn ngữ (i18n):** Chuyển đổi ngôn ngữ Anh/Việt.
  - *Files:* Thư mục `resources/messages.properties`, filter đa ngôn ngữ.
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **27. Chế độ Tối (Dark Mode):** Toggle sáng/tối lưu vào Cookie/LocalStorage.
  - *Files:* `style.css`, `header.jsp` (Nút chuyển đổi).
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **28. Spacing & Typography:** Chỉnh lại Padding, Margin, Size chữ.
  - *Files:* `style.css`
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **29. Màu sắc & Loading State:** Thay màu button, thêm animation Loading (Spinner).
  - *Files:* `style.css`, JavaScript global.
  - *Assignee:* 
  - *Ghi chú:*
- [ ] **31. Tối ưu UX form:** Giữ lại dữ liệu form khi nhập sai (Sticky Forms).
  - *Files:* Tất cả các file `.jsp` chứa form.
  - *Assignee:* 
  - *Ghi chú:*

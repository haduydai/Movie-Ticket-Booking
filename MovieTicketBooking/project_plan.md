# Kế Hoạch Phát Triển & Phân Công Nhiệm Vụ (Project Plan)

> **Hướng dẫn sử dụng cho team:** 
> - Đánh dấu `[x]` khi hoàn thành, `[/]` khi đang làm.
> - Điền tên vào mục **Assignee** khi nhận task.
> - Ghi chú các lỗi phát sinh (nếu có) vào mục **Ghi chú**.

## 1. Authentication (Xác thực & Bảo mật)
- [ ] **1. Đăng ký (Register):** Kiểm tra validation, check trùng email, giữ lại dữ liệu khi lỗi, ẩn/hiện mật khẩu.
  - *Files:* `controller/RegisterServlet.java`, `dao/UserDAO.java`, `register.jsp`
  - *Assignee:* 
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

---

# Kế hoạch chi tiết: Hoàn thành 5 issue trong phần `Quản lý`

Mục tiêu: Cung cấp kế hoạch chi tiết (task, files, DB, test, ước lượng thời gian) để hoàn thành các issue: `Quản lý Phim` (20), `Quản lý Rạp` (21), `Quản lý Suất Chiếu` (22), `Quản lý Doanh Thu` (24), và `Hoàn thiện Thêm/Xóa/Sửa` (25).

Checklist tổng thể
- [ ] Xác nhận scope 5 issue (phim, rạp, suất chiếu, doanh thu, CRUD hoàn chỉnh).
- [ ] Lập task nhỏ backend/frontend/DB/test cho mỗi issue.
- [ ] Chuẩn bị môi trường test (import `mySQL/Insert-Data.sql`).
- [ ] Triển khai theo thứ tự ưu tiên, commit & test từng phần.
- [ ] Viết test case thủ công và kiểm thử trên môi trường local.
- [ ] Hoàn thiện tài liệu thay đổi (SQL migration notes, README ngắn).

Ưu tiên đề xuất (lý do):
1. Issue 25 — Hoàn thiện Thêm/Xóa/Sửa (nền tảng, giảm lỗi cho phần còn lại)
2. Issue 20 — Quản lý Phim (upload poster qua API + CRUD)
3. Issue 21 — Quản lý Rạp (trạng thái mở/đóng)
4. Issue 22 — Quản lý Suất Chiếu (kiểm tra xung đột + seat init)
5. Issue 24 — Quản lý Doanh Thu (báo cáo + biểu đồ)

Chi tiết từng issue

## Issue 25 — Hoàn thiện Thêm / Xóa / Sửa (Ưu tiên: Cao)
- Mục tiêu: Chuẩn hóa các thao tác CRUD cho admin, thêm xử lý lỗi, confirm delete, và bảo vệ route admin.
- Tiêu chí nghiệm thu:
  - Các servlet `Add*`, `Edit*`, `Delete*` có try/catch và rollback khi cần.
  - Confirm dialog trước khi xóa; hiển thị thông báo success/error rõ ràng.
  - `AdminAuthFilter.java` chặn truy cập nếu chưa login hoặc không có quyền.
- Tasks (subtasks):
  - Backend: thêm try/catch, transaction rollback, chuẩn hóa response (redirect với message hoặc JSON cho AJAX).
  - Frontend: thêm confirm khi xóa, validate client-side, hiển thị toast message.
  - Test: nhập sai dữ liệu, thử xóa bản ghi có ràng buộc, kiểm tra phân quyền.
- Files chính:
  - `src/main/java/controller_admin/*` (Add/Edit/Delete servlets)
  - `src/main/java/controller/AdminAuthFilter.java`
- DB: không cần schema change; chú ý kiểm tra ràng buộc FK khi xóa.
- Ước lượng: 4–8 giờ
- Rủi ro: Xóa dữ liệu liên quan → đề xuất soft-delete hoặc check liên quan trước khi xóa.

## Issue 20 — Quản lý Phim (Ưu tiên: Cao)
- Mục tiêu: Admin có thể thêm/sửa/xóa phim (đang chiếu/sắp chiếu), upload poster qua API (không nhập URL thủ công).
- Tiêu chí nghiệm thu:
  - Form thêm/sửa hỗ trợ upload poster (multipart) và preview trên UI.
  - Poster được lưu (trong project hoặc trả về path/ID) và hiển thị ở trang user.
  - Danh sách phim admin có filter trạng thái, nút edit/delete hoạt động.
- Tasks:
  - Backend:
    - Kiểm tra/cập nhật model `Movie.java` (thêm `poster_path` nếu cần).
    - Cập nhật `IMovieDAO`/`MovieDAO` với các method insert/update/delete gồm `poster_path`.
    - Thêm hoặc chỉnh `AddMovieServlet.java`/`EditMovieServlet.java` để nhận multipart; hoặc tạo `UploadPosterServlet.java` trả path JSON.
    - Thêm validation server-side (title required, duration > 0).
  - Frontend (JSP): cập nhật form (enctype multipart/form-data), preview image, show error/success.
  - DB: thêm cột `poster_path VARCHAR(255)` nếu chưa có.
  - Security: validate file type (image/*), giới hạn kích thước, đặt tên file tránh overwrite.
- Files chính:
  - `src/main/java/model/Movie.java`
  - `src/main/java/dao/MovieDAO.java`
  - `src/main/java/controller_admin/AddMovieServlet.java`, `EditMovieServlet.java`
  - JSP quản lý phim (ví dụ `admin_movies.jsp` hoặc `admin_movie_form.jsp`)
- Storage gợi ý: lưu vào `src/main/webapp/images/posters/` hoặc dùng external API (Cloudinary).
- Ước lượng: 6–12 giờ

## Issue 21 — Quản lý Rạp (Ưu tiên: Trung)
- Mục tiêu: CRUD rạp, thêm trạng thái Open/Closed (ảnh hưởng tới việc tạo suất chiếu).
- Tiêu chí nghiệm thu:
  - Admin có thể tạo/sửa/xóa rạp; set trạng thái Open/Closed.
  - Hệ thống không cho tạo suất chiếu trong rạp có status = Closed.
- Tasks:
  - Backend: thêm trường `status`/`is_open` trong `Cinema` model và DAO; cập nhật servlet add/edit/delete.
  - Frontend: form add/edit, danh sách rạp có nút toggle status.
  - DB: migration: ALTER TABLE cinemas ADD COLUMN status TINYINT(1) DEFAULT 1;
  - Test: tạo rạp, đóng rạp, thử tạo suất (phải bị chặn).
- Files chính:
  - `src/main/java/dao/CinemaDAO.java`, `AddCinemaServlet.java`, `EditCinemaServlet.java`
- Ước lượng: 4–8 giờ

## Issue 22 — Quản lý Suất Chiếu (Ưu tiên: Trung/Cao)
- Mục tiêu: Admin tạo/sửa/xóa suất chiếu; hệ thống kiểm tra xung đột thời gian trong cùng phòng; khởi tạo trạng thái ghế cho suất mới.
- Tiêu chí nghiệm thu:
  - Kiểm tra overlap: không cho tạo suất nếu (newStart < existingEnd) AND (existingStart < newEnd) trong cùng `room_id`.
  - Khi tạo suất thành công, tạo tất cả records `show_time_seat` là available.
- Tasks:
  - Backend: cập nhật `ShowTimeDAO` để check overlap, dùng transaction để insert showtime + show_time_seat.
  - Frontend: form thêm suất (movie, room, start, end, price), hiển thị cảnh báo nếu xung đột.
  - DB: đảm bảo bảng `show_time_seat` có cấu trúc hợp lý; tạo index trên `room_id, start_time`.
  - Test: tạo suất không trùng (pass), tạo suất trùng (fail), chọn ghế người dùng cho suất mới.
- Files chính:
  - `src/main/java/dao/ShowTimeDAO.java`, `ShowTimeSeatDAO.java`, `controller_admin/AddShowTimeServlet.java`
- Ước lượng: 8–14 giờ
- Rủi ro: Race condition khi nhiều admin tạo suất cùng lúc → dùng transaction/lock.

## Issue 24 — Quản lý Doanh Thu (Ưu tiên: Thấp/Trung)
- Mục tiêu: Báo cáo doanh thu theo tháng/năm/phim/rạp, hiển thị bảng + biểu đồ, hỗ trợ export CSV.
- Tiêu chí nghiệm thu:
  - Admin có trang report với filter thời gian (date range / preset month/year), hiển thị tổng doanh thu, số vé, biểu đồ cột/đường.
  - Có button export CSV.
- Tasks:
  - Backend: tạo `RevenueReportServlet.java` trả JSON/CSV; viết các query aggregate (GROUP BY YEAR/MONTH, GROUP BY movie_id).
  - Frontend: trang báo cáo admin (`admin_revenue.jsp`) + Chart.js để vẽ biểu đồ.
  - DB: index `purchase_date`, `movie_id` để tối ưu query.
  - Test: nạp dữ liệu sample, kiểm tra số liệu aggregate khớp với SUM(price).
- Files chính:
  - `src/main/java/dao/TicketDAO.java`, `controller_admin/RevenueReportServlet.java`, `admin_revenue.jsp`
- Ước lượng: 6–12 giờ

Kế hoạch triển khai (gợi ý theo ngày)
- Ngày 1 (4–8h): Issue 25 — ổn định CRUD cơ bản, confirm delete, chuẩn hóa phản hồi, bảo vệ route.
- Ngày 2 (6–12h): Issue 20 — upload poster + CRUD phim.
- Ngày 3 (4–8h): Issue 21 — CRUD rạp và toggle trạng thái.
- Ngày 4–5 (8–14h): Issue 22 — kiểm tra xung đột suất chiếu, khởi tạo seat.
- Ngày 6 (6–12h): Issue 24 — báo cáo doanh thu + biểu đồ + export.

Deliverables cho mỗi issue
- Code: servlet/DAO/model thay đổi, JSP/JS cập nhật.
- DB: SQL migration notes (ví dụ `ALTER TABLE ...`), và optional seed data.
- Test: checklist test cases + hướng dẫn kiểm thử thủ công.
- Docs: cập nhật `project_plan.md` (file này) và thêm migration notes vào thư mục `mySQL/`.

Lệnh nạp dữ liệu mẫu (PowerShell, ví dụ database `moviedb`):
```powershell
mysql -u root -p moviedb < "C:\Users\Admin\Desktop\123\Movie-Ticket-Booking\MovieTicketBooking\mySQL\Insert-Data.sql"
```

Gợi ý commit/PR messages
- [MNG-25-1] Fix: standardize CRUD responses, add delete confirm
- [MNG-20-1] Feature: Movie poster upload API + Movie.poster_path
- [MNG-21-1] Feature: Add cinema status open/closed
- [MNG-22-1] Feature: Add showtime overlap check + seat initialization
- [MNG-24-1] Feature: Revenue report API + admin chart

Test cases tóm tắt
- CRUD Phim: Thêm (kèm poster), Sửa (thay poster), Xóa (confirm), kiểm tra hiển thị user.
- Rạp: Thêm, Toggle Close -> Tạo suất bị chặn.
- Suất: Tạo 10:00-12:00, cố gắng tạo 11:00-13:00 -> nhận lỗi.
- Doanh thu: tạo vé sample across months, kiểm tra báo cáo tổng khớp SUM.

Rủi ro chung & mitigation
- Upload file: validate MIME type & size; lưu tên file an toàn.
- Xóa dữ liệu quan trọng: soft-delete hoặc check ràng buộc trước khi xóa.
- Race condition: dùng transaction và kiểm tra trước commit.

Next steps tôi có thể làm cho bạn (chọn 1):
- A: Tôi tạo sẵn các SQL migration snippets và mẫu servlet/JSP scaffold cho Issue 25 (các thay đổi nhỏ) — tôi sẽ commit file vào workspace.
- B: Tôi bắt đầu implement Issue 25 trực tiếp (chỉnh file `controller_admin/*`) và chạy check lỗi.

Ghi chú: Nếu bạn muốn tôi thực hiện bước A, tôi sẽ tạo các file migration và scaffold; nếu B, hãy xác nhận để tôi bắt đầu sửa code.



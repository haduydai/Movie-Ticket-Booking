USE movie_ticket_booking;

-- ======================================================================================
-- BƯỚC 1: LÀM SẠCH DỮ LIỆU CŨ (Để tránh trùng lặp hoặc lỗi khóa ngoại)
-- ======================================================================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE showtimeseats;
TRUNCATE TABLE tickets;
TRUNCATE TABLE cinema_movies;
TRUNCATE TABLE showtimes;
TRUNCATE TABLE rooms;
TRUNCATE TABLE movies;
TRUNCATE TABLE cinemas;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS = 1;

-- ======================================================================================
-- BƯỚC 2: THÊM DỮ LIỆU USERS
-- ======================================================================================
-- INSERT INTO users (username, password, email, phonenumber, role) VALUES 
-- ('admin', '$2a$10$Q7.u.3.r.t.y.u.i.o.p.1', 'admin@cinestar.com.vn', '0909123456', 'ADMIN'),
-- ('minhkhang', '$2a$10$Q7.u.3.r.t.y.u.i.o.p.1', 'khang.nguyen@gmail.com', '0912345678', 'USER'),
-- ('thaovy', '$2a$10$Q7.u.3.r.t.y.u.i.o.p.1', 'vy.tran@gmail.com', '0987654321', 'USER');

-- ======================================================================================
-- BƯỚC 3: THÊM DỮ LIỆU CINEMAS
-- ======================================================================================
INSERT INTO cinemas (cinema_name, cinema_address) VALUES 
('Cinestar Quốc Thanh', '271 Nguyễn Trãi, Phường Nguyễn Cư Trinh, Quận 1, TP.HCM'), -- ID: 1
('Cinestar Hai Bà Trưng', '135 Hai Bà Trưng, Phường Bến Nghé, Quận 1, TP.HCM'), -- ID: 2
('Cinestar Sinh Viên', 'Nhà Văn hóa Sinh viên ĐHQG TP.HCM, Dĩ An, Bình Dương'), -- ID: 3
('Cinestar Mỹ Tho', '52 Đinh Bộ Lĩnh, Phường 3, TP. Mỹ Tho, Tiền Giang'), -- ID: 4
('Cinestar Đà Lạt', 'Quảng trường Lâm Viên, Phường 10, TP. Đà Lạt, Lâm Đồng'), -- ID: 5
('Cinestar Huế', '25 Hai Bà Trưng, Phường Vĩnh Ninh, TP. Huế'), -- ID: 6
('Cinestar Kiên Giang', 'TTTM Rạch Sỏi, Nguyễn Chí Thanh, TP. Rạch Giá, Kiên Giang'), -- ID: 7
('Cinestar ParkCity Hà Nội', 'TTTM The LINC, KĐT ParkCity, Lê Trọng Tấn, Hà Đông, Hà Nội'); -- ID: 8

-- ======================================================================================
-- BƯỚC 4: THÊM DỮ LIỆU ROOMS
-- ======================================================================================
-- Rạp 1: Quốc Thanh (ID 1) -> Room ID: 1, 2, 3
INSERT INTO rooms (room_name, number_of_columns, number_of_rows, cinema_id) VALUES 
('Screen 01', 15, 12, 1), ('Screen 02', 12, 10, 1), ('Screen 03', 10, 8, 1);

-- Rạp 2: Hai Bà Trưng (ID 2) -> Room ID: 4, 5
INSERT INTO rooms (room_name, number_of_columns, number_of_rows, cinema_id) VALUES 
('Screen 01', 14, 12, 2), ('Screen 02', 12, 10, 2);

-- Rạp 3: Sinh Viên (ID 3) -> Room ID: 6, 7
INSERT INTO rooms (room_name, number_of_columns, number_of_rows, cinema_id) VALUES 
('Screen 01', 18, 14, 3), ('Screen 02', 18, 14, 3);

-- Các rạp tỉnh
INSERT INTO rooms (room_name, number_of_columns, number_of_rows, cinema_id) VALUES 
('Screen 01', 12, 10, 4), ('Screen 02', 10, 8, 4), -- Mỹ Tho (ID 4) -> Room 8, 9
('Screen 01', 12, 10, 5), ('Screen 02', 10, 8, 5), -- Đà Lạt (ID 5) -> Room 10, 11
('Screen 01', 14, 12, 6), -- Huế (ID 6) -> Room 12
('Screen 01', 12, 10, 7), -- Kiên Giang (ID 7) -> Room 13
('Screen 01', 15, 12, 8), ('Screen 02', 12, 10, 8); -- Hà Nội (ID 8) -> Room 14, 15

-- ======================================================================================
-- BƯỚC 5: THÊM DỮ LIỆU PHIM (Đã sửa lỗi thiếu cột diễn viên và lỗi dấu chấm phẩy)
-- ======================================================================================
INSERT INTO movies (movie_name, movie_type, director_name, names_of_actors, movie_description, movie_duration, movie_country, movie_image_url, movie_status) VALUES 
-- [PHIM ĐANG CHIẾU]
	('Avatar 3: Fire and Ash', 'Khoa Học Viễn Tưởng/Hành Động', 'James Cameron', 'Sam Worthington, Zoe Saldana', 'Jake Sully và Neytiri đối mặt với tộc người Ash People - những Na\'vi sử dụng lửa tại Pandora. Cuộc chiến khốc liệt nhất bắt đầu.', 190, 'Mỹ', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F12-2025%2Favatar-3-poster.jpg&w=1920&q=75', 'NOW_SHOWING'),

('Zootopia 2', 'Hoạt Hình/Hài', 'Byron Howard', 'Ginnifer Goodwin, Jason Bateman', 'Judy Hopps và Nick Wilde trở lại với một vụ án bí ẩn mới đe dọa sự bình yên của thành phố Zootopia.', 105, 'Mỹ', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F11-2025%2Fzootopia-2_1.jpg&w=1080&q=75', 'NOW_SHOWING'),

('Hoàng Tử Quỷ', 'Kinh Dị/Cổ Trang', 'Trần Hữu Tấn', 'Anh Tú Atus, Rima Thanh Vy', 'Một bộ phim kinh dị cổ trang Việt Nam lấy bối cảnh thời Lê Trung Hưng, nơi những bí mật đen tối của hoàng tộc dần được hé lộ.', 117, 'Việt Nam', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F12-2025%2Fhoang-tu-quy-main.png&w=1080&q=75', 'NOW_SHOWING'),

('Vua các vua', 'Hoạt hình', ' Seong-ho Jang', 'Mark Hamill, Pierce Brosnan, Roman Griffin Davis', 'Vua của Các Vua - Phim hoạt hình về cuộc đời Chúa Giê-su đầu tiên.', 130, 'Hàn Quốc', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F12-2025%2Fvua-cua-cac-vua.png&w=1080&q=75', 'NOW_SHOWING'),

('Spongebob: Lời nguyền hải tặc', 'Hoạt Hình', 'Derek Drymon', 'Tom Kenny, Bill Fagerbakke', 'SpongeBob phiêu lưu xuống đáy đại dương để đối mặt với hồn ma của Người Hà Lan bay, vượt qua thử thách và khám phá những bí ẩn dưới biển.', 130, 'Hàn Quốc', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F12-2025%2Fspongebob-poster.png&w=1080&q=75', 'NOW_SHOWING'),

('Chợ đen thời tận thế', 'Hồi hộp/Hành Động', 'HONG KI-WON', 'LEE JAE-IN, HONG KYUNG', 'Sau đại địa chấn xóa sổ thế giới, hy vọng mong manh len lỏi giữa toà chung cư cuối cùng biến thành “chợ đen”- Nơi mạng đổi mạng và niềm tin là món đồ xa xỉ.', 130, 'Hàn Quốc', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F12-2025%2Fcho-den-thoi-tan-the-poster.jpg&w=1080&q=75', 'NOW_SHOWING'),

-- [PHIM SẮP CHIẾU] (Sửa lỗi: Thay dấu chấm phẩy bằng dấu phẩy để nối tiếp danh sách hoặc tách lệnh INSERT mới)
('Đại thoại tây du kí ' , 'Hài/Hành Động', 'Lưu Trấn Vĩ', 'Châu Tinh Trì, Chu Ân', 'Phiên bản "độc" nhất của Tây Du Ký. Câu chuyện được xem như tinh hoa của "vua hài" Châu Tinh Trì, khai mở quá khứ chấn động của Tề thiên Đại Thánh Tôn Ngộ Không. Khởi chiếu: Thứ Sáu, 09/01/2026.', 115, 'Khác', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F01-2026%2Fdai-thoai-tay-du-2.jpg&w=1080&q=75', 'COMING_SOON'),

('Xác mẹ hồn quỷ', 'Kinh Dị', 'Randolph Zaini', 'Artika Sari, DeviAurora Ribero', 'Vira, người mẹ đơn thân vừa thực hiện được ước mơ mở tiệm làm tóc để lo cho 2 đứa con thơ. Hạnh phúc giản đơn tưởng mới bắt đầu đã hóa thành nỗi ám ảnh. Khởi chiếu: Thứ Sáu, 09/01/2026.', 119, 'Khác', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F01-2026%2Fxac-me-hon-quy.jpg&w=1920&q=75', 'COMING_SOON'),

('Phim điện ảnh thám tử lừng danh Connan: quả bom chọc trời', 'Hoạt Hình', 'Kenji Kodama', 'Minami Takayama, Kappei Yamaguchi', 'Hành trình của Conan bước lên màn ảnh rộng. Khởi chiếu: Thứ Sáu, 23/01/2026', 99, 'Nhật Bản', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F01-2026%2Fconan-qua-bom-choc-troi.jpg&w=1080&q=75', 'COMING_SOON'),

('Ác linh trùng tang', 'Kinh Dị', 'Ivan Bandhito', 'Joshua Suherman, Egi Fedly', 'Sau nhiều năm xa cách, bốn anh chị em Jojo, Angga, Nindy và Momo trở về ngôi nhà cũ để gặp cha già và tưởng niệm em út Sofi. Khởi chiếu: Thứ Sáu, 09/01/2026', 120, 'Mỹ', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F01-2026%2Fac-linh-trung-tang.jpg&w=1080&q=75', 'COMING_SOON'),

-- (Sửa lỗi: Đã thêm 'Chưa cập nhật' vào cột diễn viên để không bị lệch cột)
('Linh trưởng','Hồi Hộp/Kinh Dị', 'Johannes Roberts', 'Chưa cập nhật', 'Khi kì nghỉ của nhóm bạn bỗng hóa cơn ác mộng kinh hoàng về nỗi sợ và bản năng sinh tồn. Khởi chiếu: Thứ Sáu, 16/01/2026' , 99, 'Khác', 'https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F01-2026%2Flinh-truong.png&w=1080&q=75','COMING_SOON' ),

-- (Sửa lỗi: Đã thêm 'Chưa cập nhật' vào cột diễn viên)
('Vạn dặm yêu em', 'Tình cảm', 'Rahhat Shah Kazmi', 'Chưa cập nhật', 'Nhạc sĩ Ấn Độ - Manav đến Việt Nam du học và yêu cô vũ công tên Linh. Khởi chiếu: Thứ Sáu, 09/01/2026', 122, 'Khác','https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F01-2026%2Fvan-dam-yeu-em.jpg&w=1080&q=75', 'COMING_SOON' );

-- ======================================================================================
-- BƯỚC 6: PHÂN BỔ PHIM VÀO RẠP
-- ======================================================================================
INSERT INTO cinema_movies (cinema_id, movie_id)
SELECT c.cinema_id, m.movie_id 
FROM cinemas c CROSS JOIN movies m 
WHERE m.movie_status = 'NOW_SHOWING';

-- ======================================================================================
-- BƯỚC 7: TẠO LỊCH CHIẾU
-- ======================================================================================

-- Avatar 3 (ID 1) - Chiếu rạp 1 (Quốc Thanh) và 2 (Hai Bà Trưng)
-- INSERT INTO showtimes (showtime_price, start_time, movie_id, cinema_id, room_id) VALUES
-- (105000, CONCAT(CURDATE(), ' 18:00:00'), 1, 1, 1),
-- (105000, CONCAT(CURDATE(), ' 21:00:00'), 1, 1, 1),
-- (105000, CONCAT(DATE_ADD(CURDATE(), INTERVAL 1 DAY), ' 19:00:00'), 1, 2, 4);

-- Zootopia 2 (ID 2) - Chiếu rạp 3 (Sinh Viên) giá rẻ
-- SỬA LỖI: Rạp 3 (Sinh Viên) dùng Room ID 6, 7 (không phải 5)
-- INSERT INTO showtimes (showtime_price, start_time, movie_id, cinema_id, room_id) VALUES
-- (45000, CONCAT(CURDATE(), ' 10:00:00'), 2, 3, 6), -- Sửa room_id thành 6
-- (45000, CONCAT(CURDATE(), ' 14:00:00'), 2, 3, 6), -- Sửa room_id thành 6
-- (45000, CONCAT(DATE_ADD(CURDATE(), INTERVAL 1 DAY), ' 10:00:00'), 2, 3, 6); -- Sửa room_id thành 6

-- Hoàng Tử Quỷ (ID 3) - Suất chiếu khuya
-- INSERT INTO showtimes (showtime_price, start_time, movie_id, cinema_id, room_id) VALUES
-- (85000, CONCAT(CURDATE(), ' 23:00:00'), 3, 1, 2),
-- (85000, CONCAT(DATE_ADD(CURDATE(), INTERVAL 1 DAY), ' 22:30:00'), 3, 2, 5);

-- ======================================================================================
-- BƯỚC 8: TẠO VÉ GIẢ LẬP
-- ======================================================================================

-- User 'thaovy' đặt vé xem Zootopia 2
-- INSERT INTO tickets (ticket_uid, ticket_price, payment_method, ticket_status, user_id, showtime_id) VALUES
-- ('TICKET-ZOO-001', 75000, 'CASH', 'PAID', 3, 6); -- ID 6 là suất chiếu Zootopia

-- INSERT INTO showtimeseats (seat_name, user_id, showtime_id, room_id) VALUES
-- ('E5', 3, 6, 7); -- Lưu ý: room_id ở đây là 7 (Rạp Sinh Viên phòng 2), nhưng vé suất 6 dùng phòng 6. Để đồng bộ, nên sửa thành 6.
-- Sửa lại cho đúng logic:
-- ('E5', 3, 6, 6);

-- User 'minhkhang' đặt vé xem Avatar 3
-- INSERT INTO tickets (ticket_uid, ticket_price, payment_method, ticket_status, user_id, showtime_id) VALUES
-- ('TICKET-AVA-001', 95000, 'ONLINE', 'PAID', 2, 1),
-- ('TICKET-AVA-002', 95000, 'ONLINE', 'PAID', 2, 1);

-- INSERT INTO showtimeseats (seat_name, user_id, showtime_id, room_id) VALUES
-- ('H10', 2, 1, 1), 
-- ('H11', 2, 1, 1);

-- SELECT 'Cập nhật dữ liệu phim Tháng 1/2026 thành công!' AS Status;
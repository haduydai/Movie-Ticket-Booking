USE movie_ticket_booking;

-- Bổ sung cột cinema_status (từ Issue 21) nếu database của bạn chưa có
ALTER TABLE cinemas ADD COLUMN cinema_status ENUM('OPEN','CLOSED') DEFAULT 'OPEN';
UPDATE cinemas SET cinema_status = 'OPEN' WHERE cinema_status IS NULL;

-- Thêm dữ liệu Rạp
INSERT INTO cinemas (cinema_name, cinema_address, cinema_status) VALUES 
('CGV Vincom', 'Vincom Center, Q1, TP.HCM', 'OPEN'),
('Galaxy Nguyễn Du', '116 Nguyễn Du, Q1, TP.HCM', 'OPEN');

-- Thêm dữ liệu Phòng chiếu
INSERT INTO rooms (room_name, number_of_columns, number_of_rows, cinema_id) VALUES
('R1', 10, 10, (SELECT MAX(cinema_id) FROM cinemas WHERE cinema_name='CGV Vincom')),
('R2', 15, 10, (SELECT MAX(cinema_id) FROM cinemas WHERE cinema_name='Galaxy Nguyễn Du'));

-- Thêm dữ liệu Phim
INSERT INTO movies (movie_name, movie_type, director_name, names_of_actors, movie_description, movie_duration, movie_country, movie_image_url, movie_status) VALUES
('Avenger: End Game', 'Action', 'Anthony Russo', 'Robert Downey Jr.', 'Marvel Movie', 180, 'USA', 'url', 'NOW_SHOWING'),
('Lật Mặt 7', 'Drama', 'Lý Hải', 'Tiết Cương', 'Phim VN', 120, 'VN', 'url', 'NOW_SHOWING');

-- Thêm dữ liệu Người dùng
INSERT INTO users (username, password, email, phonenumber, role) VALUES
('testuser1', '123456', 'test1@gmail.com', '0123456789', 'USER'),
('testuser2', '123456', 'test2@gmail.com', '0987654321', 'USER');

-- Thêm dữ liệu Suất chiếu (Thời gian: cách đây 2 ngày, 1 ngày, và hôm nay)
INSERT INTO showtimes (showtime_price, start_time, created_at, movie_id, cinema_id, room_id) VALUES
(100000.00, DATE_SUB(NOW(), INTERVAL 2 DAY), NOW(), (SELECT MAX(movie_id) FROM movies WHERE movie_name='Avenger: End Game'), (SELECT MAX(cinema_id) FROM cinemas WHERE cinema_name='CGV Vincom'), (SELECT MAX(room_id) FROM rooms WHERE room_name='R1')),
(80000.00, DATE_SUB(NOW(), INTERVAL 1 DAY), NOW(), (SELECT MAX(movie_id) FROM movies WHERE movie_name='Lật Mặt 7'), (SELECT MAX(cinema_id) FROM cinemas WHERE cinema_name='Galaxy Nguyễn Du'), (SELECT MAX(room_id) FROM rooms WHERE room_name='R2')),
(120000.00, NOW(), NOW(), (SELECT MAX(movie_id) FROM movies WHERE movie_name='Avenger: End Game'), (SELECT MAX(cinema_id) FROM cinemas WHERE cinema_name='Galaxy Nguyễn Du'), (SELECT MAX(room_id) FROM rooms WHERE room_name='R2'));

-- Thêm dữ liệu Vé (Doanh thu)
INSERT INTO tickets (ticket_uid, ticket_price, payment_method, ticket_status, created_at, updated_at, user_id, showtime_id) VALUES
('TICK01', 100000.00, 'ONLINE', 'PAID', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), (SELECT MAX(user_id) FROM users WHERE username='testuser1'), (SELECT MAX(showtime_id) FROM showtimes WHERE showtime_price=100000.00)),
('TICK02', 100000.00, 'CASH', 'PAID', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), (SELECT MAX(user_id) FROM users WHERE username='testuser2'), (SELECT MAX(showtime_id) FROM showtimes WHERE showtime_price=100000.00)),
('TICK03', 80000.00, 'ONLINE', 'PAID', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY), (SELECT MAX(user_id) FROM users WHERE username='testuser1'), (SELECT MAX(showtime_id) FROM showtimes WHERE showtime_price=80000.00)),
('TICK04', 120000.00, 'CASH', 'PAID', NOW(), NOW(), (SELECT MAX(user_id) FROM users WHERE username='testuser2'), (SELECT MAX(showtime_id) FROM showtimes WHERE showtime_price=120000.00));

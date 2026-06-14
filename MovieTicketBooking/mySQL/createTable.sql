-- ==========================================================
-- SCRIPT TẠO CƠ SỞ DỮ LIỆU ĐẶT VÉ XEM PHIM (MOVIE_TICKET_BOOKING)
-- Hỗ trợ đầy đủ hệ thống cũ (tickets, showtimeseats) & cấu hình mới (Soft Delete, Carts, OAuth, Transactions, Promos, Combos)
-- ==========================================================

CREATE DATABASE IF NOT EXISTS movie_ticket_booking CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE movie_ticket_booking;

-- ==========================================
-- 1. Bảng USERS (Người dùng)
-- ==========================================
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phonenumber VARCHAR(20),
    role ENUM('ADMIN', 'USER') DEFAULT 'USER',
    avatar_url VARCHAR(255) DEFAULT NULL,
    avatar_public_id VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

-- ==========================================
-- 2. Bảng OAUTH_ACCOUNTS (Đăng nhập MXH Google)
-- ==========================================
CREATE TABLE IF NOT EXISTS oauth_accounts (
    oauth_id INT AUTO_INCREMENT PRIMARY KEY,
    provider VARCHAR(50) NOT NULL,             -- 'GOOGLE'
    provider_user_id VARCHAR(255) NOT NULL,    -- Google User ID nhận từ API
    provider_email VARCHAR(255),
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY unique_provider_user (provider, provider_user_id)
);

-- ==========================================
-- 3. Bảng CINEMAS (Rạp phim)
-- ==========================================
CREATE TABLE IF NOT EXISTS cinemas (
    cinema_id INT AUTO_INCREMENT PRIMARY KEY,
    cinema_name VARCHAR(150) NOT NULL,
    cinema_address VARCHAR(255) NOT NULL,
    cinema_status ENUM('OPEN', 'CLOSED') DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

-- ==========================================
-- 4. Bảng ROOMS (Phòng chiếu)
-- ==========================================
CREATE TABLE IF NOT EXISTS rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(100) NOT NULL,
    number_of_columns INT,
    number_of_rows INT,
    cinema_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL,
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id) ON DELETE CASCADE
);

-- ==========================================
-- 5. Bảng MOVIES (Phim)
-- ==========================================
CREATE TABLE IF NOT EXISTS movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_name VARCHAR(150) NOT NULL,
    movie_type VARCHAR(50),
    director_name VARCHAR(100),
    names_of_actors VARCHAR(255),
    movie_description TEXT,
    movie_duration INT,
    movie_country VARCHAR(20),
    movie_image_url VARCHAR(255),
    movie_image_public_id VARCHAR(255) DEFAULT NULL,
    movie_status ENUM('COMING_SOON', 'NOW_SHOWING', 'STOPPED_SHOWING') DEFAULT 'COMING_SOON',
    movie_tag VARCHAR(100),
    trailer_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

-- ==========================================
-- 6. Bảng CINEMA_MOVIES (Phim chiếu tại rạp nào)
-- ==========================================
CREATE TABLE IF NOT EXISTS cinema_movies (
    cinema_id INT,
    movie_id INT,
    PRIMARY KEY (cinema_id, movie_id),
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE
);

-- ==========================================
-- 7. Bảng SHOWTIMES (Suất chiếu)
-- ==========================================
CREATE TABLE IF NOT EXISTS showtimes (
    showtime_id INT AUTO_INCREMENT PRIMARY KEY,
    showtime_price DECIMAL(10,2),
    start_time DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    movie_id INT,
    cinema_id INT,
    room_id INT,
    deleted_at DATETIME DEFAULT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- ==========================================
-- 8. Bảng TICKETS (Vé xem phim - Logic cũ)
-- ==========================================
CREATE TABLE IF NOT EXISTS tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_uid VARCHAR(100) UNIQUE,
    ticket_price DECIMAL(10,2),
    payment_method ENUM('CASH', 'CARD', 'ONLINE') DEFAULT 'CASH',
    ticket_status ENUM('PAID', 'UNPAID', 'CANCELLED') DEFAULT 'UNPAID',
    ticket_seats VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id INT,
    showtime_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (showtime_id) REFERENCES showtimes(showtime_id) ON DELETE CASCADE
);

-- ==========================================
-- 9. Bảng SHOWTIMESEATS (Ghế của Suất chiếu - Logic cũ)
-- ==========================================
CREATE TABLE IF NOT EXISTS showtimeseats (
    showtimeseat_id INT AUTO_INCREMENT PRIMARY KEY,
    seat_name VARCHAR(5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id INT,
    showtime_id INT,
    room_id INT,
    ticket_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (showtime_id) REFERENCES showtimes(showtime_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id) ON DELETE SET NULL
);

-- ==========================================
-- 10. Bảng PROMOTIONS (Khuyến mãi - Thiết kế mới)
-- ==========================================
CREATE TABLE IF NOT EXISTS promotions (
    promo_id INT AUTO_INCREMENT PRIMARY KEY,
    promo_code VARCHAR(50) UNIQUE NOT NULL,
    discount_percentage INT CHECK (discount_percentage BETWEEN 0 AND 100),
    max_discount_amount DECIMAL(10,2),
    expiry_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

-- ==========================================
-- 11. Bảng COMBOS (Snack & Bắp nước - Thiết kế mới)
-- ==========================================
CREATE TABLE IF NOT EXISTS combos (
    combo_id INT AUTO_INCREMENT PRIMARY KEY,
    combo_name VARCHAR(150) NOT NULL,
    combo_description VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME DEFAULT NULL
);

-- ==========================================
-- 12. Bảng SEATS (Ghế vật lý phòng chiếu)
-- ==========================================
CREATE TABLE IF NOT EXISTS seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT,
    row_label CHAR(2) NOT NULL,
    column_number INT NOT NULL,
    seat_type ENUM('REGULAR', 'VIP', 'SWEETBOX') DEFAULT 'REGULAR',
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- ==========================================
-- 13. Bảng BOOKINGS (Hóa đơn đặt đơn mới)
-- ==========================================
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2) NOT NULL,
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    final_price DECIMAL(10,2) NOT NULL,
    booking_status ENUM('PAID', 'UNPAID', 'CANCELLED') DEFAULT 'UNPAID',
    promo_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (promo_id) REFERENCES promotions(promo_id) ON DELETE SET NULL
);

-- ==========================================
-- 14. Bảng PAYMENT_TRANSACTIONS (Giao dịch cổng thanh toán VNPAY/Momo)
-- ==========================================
CREATE TABLE IF NOT EXISTS payment_transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method ENUM('VNPAY', 'MOMO', 'CASH') NOT NULL,
    provider_transaction_ref VARCHAR(100),
    payment_url VARCHAR(1000),
    status ENUM('PENDING', 'PAID', 'FAILED', 'CANCELLED') DEFAULT 'PENDING',
    paid_at DATETIME DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
);

-- ==========================================
-- 15. Bảng BOOKING_AUDIT_LOGS (Lịch sử biến động đơn)
-- ==========================================
CREATE TABLE IF NOT EXISTS booking_audit_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    actor_id INT,
    action_type VARCHAR(100) NOT NULL,
    old_value VARCHAR(100),
    new_value VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (actor_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- ==========================================
-- 16. Bảng CARTS (Giỏ hàng của User)
-- ==========================================
CREATE TABLE IF NOT EXISTS carts (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- ==========================================
-- 17. Bảng CART_ITEMS (Sản phẩm trong Giỏ hàng)
-- ==========================================
CREATE TABLE IF NOT EXISTS cart_items (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    item_type ENUM('TICKET', 'COMBO') NOT NULL,
    showtime_id INT DEFAULT NULL,
    seat_id INT DEFAULT NULL,
    combo_id INT DEFAULT NULL,
    quantity INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES carts(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (showtime_id) REFERENCES showtimes(showtime_id) ON DELETE CASCADE,
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id) ON DELETE CASCADE,
    FOREIGN KEY (combo_id) REFERENCES combos(combo_id) ON DELETE CASCADE,
    UNIQUE KEY unique_cart_seat (cart_id, showtime_id, seat_id)
);

-- Tạo Database và Chọn nó để sử dụng
CREATE DATABASE IF NOT EXISTS movie_ticket_booking;
USE movie_ticket_booking;
-- ==========================
-- 1. Bảng USERS
-- ==========================
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(150) NOT NULL,
    phonenumber VARCHAR(20),
    role ENUM('ADMIN', 'USER') DEFAULT 'USER'
);

-- ==========================
-- 2. Bảng CINEMAS
-- ==========================
CREATE TABLE cinemas (
    cinema_id INT AUTO_INCREMENT PRIMARY KEY,
    cinema_name VARCHAR(150) NOT NULL,
    cinema_address VARCHAR(255) NOT NULL
);

-- ==========================
-- 3. Bảng ROOMS
-- ==========================
CREATE TABLE rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(100) NOT NULL,
    number_of_columns INT,
    number_of_rows INT,
    cinema_id INT,
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ==========================
-- 4. Bảng MOVIES
-- ==========================
CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_name VARCHAR(150) NOT NULL,
    movie_type VARCHAR(50),
    director_name VARCHAR(100),
    names_of_actors VARCHAR(255),
    movie_description TEXT,
    movie_duration INT,
    movie_country VARCHAR(20),
    movie_image_url VARCHAR(255),
    movie_status ENUM('COMING_SOON', 'NOW_SHOWING', 'STOPPED_SHOWING') DEFAULT 'COMING_SOON'
);

-- ==========================
-- 5. Bảng SHOWTIMES
-- ==========================
CREATE TABLE showtimes (
    showtime_id INT AUTO_INCREMENT PRIMARY KEY,
    showtime_price DECIMAL(10,2),
    start_time DATETIME NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    movie_id INT,
    cinema_id INT,
    room_id INT,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ==========================
-- 6. Bảng TICKETS
-- ==========================
CREATE TABLE tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_uid VARCHAR(100) UNIQUE,
    ticket_price DECIMAL(10,2),
    payment_method ENUM('CASH', 'CARD', 'ONLINE') DEFAULT 'CASH',
    ticket_status ENUM('PAID', 'UNPAID', 'CANCELLED') DEFAULT 'UNPAID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id INT,
    showtime_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (showtime_id) REFERENCES showtimes(showtime_id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ==========================
-- 7. Bảng SHOWTIMESEATS
-- ==========================
CREATE TABLE showtimeseats (
    showtimeseat_id INT AUTO_INCREMENT PRIMARY KEY,
    seat_name VARCHAR(5),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id INT,
    showtime_id INT,
    room_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (showtime_id) REFERENCES showtimes(showtime_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- ==========================
-- 8. Bảng cho biết phim nào đang có ở rạp nào
-- ==========================

CREATE TABLE IF NOT EXISTS cinema_movies (
    cinema_id INT,
    movie_id INT,
    PRIMARY KEY (cinema_id, movie_id),
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE
);
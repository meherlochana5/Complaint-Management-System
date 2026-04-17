CREATE DATABASE IF NOT EXISTS complaint_db;
USE complaint_db;

-- USERS TABLE
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- ADMIN TABLE
CREATE TABLE admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- COMPLAINTS TABLE
CREATE TABLE complaints (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    subject VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- DEFAULT ADMIN
INSERT INTO admin(username, password)
VALUES ('admin', 'admin123');

-- SAMPLE USER
INSERT INTO users(name, email, password)
VALUES ('Rahul', 'rahul@gmail.com', '1234');

-- SAMPLE COMPLAINT
INSERT INTO complaints(user_id, subject, description)
VALUES (1, 'Electricity Issue', 'Power supply not working in hostel block.');

-- VIEW DATA
SELECT * FROM users;
SELECT * FROM admin;
SELECT * FROM complaints;

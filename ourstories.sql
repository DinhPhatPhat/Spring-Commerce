DROP DATABASE IF EXISTS ourstories;

CREATE DATABASE ourstories;
USE ourstories;


-- USERS TABLE --
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) DEFAULT 'A friend',
    phone_number CHAR(10),
    date_of_birth DATE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    bio TEXT DEFAULT NULL,
    image_path VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role TINYINT DEFAULT 0 CHECK (role IN (0, 1)), -- 0: User, 1: Admin
    is_active BIT DEFAULT 0
);

-- STORIES TABLE --
CREATE TABLE stories (
    id  INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title   VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    image_path  VARCHAR(255) DEFAULT NULL,
    is_approved BIT DEFAULT 0,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_stories_to_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- COMMENTS TABLE --
CREATE TABLE comments (
    id  INT AUTO_INCREMENT PRIMARY KEY,
    story_id    INT NOT NULL,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comments_to_stories FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE,
    CONSTRAINT fk_comments_to_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- LIKES TABLE --
CREATE TABLE likes (
    id  INT AUTO_INCREMENT PRIMARY KEY,
    story_id    INT NOT NULL,
    user_id INT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE  (story_id, user_id),
    CONSTRAINT fk_likes_to_stories FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE,
    CONSTRAINT fk_likes_to_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- TOKENS TABLE --
CREATE TABLE tokens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(36) NOT NULL,
    user_id INT NOT NULL,
    type TINYINT NOT NULL, -- 0: REGISTER, 1: CHANGE PASSWORD
    expired_at TIMESTAMP DEFAULT (CURRENT_TIMESTAMP + INTERVAL 20 MINUTE),
    CONSTRAINT fk_tokens_to_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)

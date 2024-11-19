-- levels 테이블 생성
CREATE TABLE IF NOT EXISTS levels (
    id BIGINT NOT NULL PRIMARY KEY,
    value INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    mascot_image_url VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

-- stadiums 테이블 생성
CREATE TABLE IF NOT EXISTS stadiums (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    main_image VARCHAR(255),
    seating_chart_image VARCHAR(255),
    labeled_seating_chart_image VARCHAR(255),
    is_active BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- baseball_teams 테이블 생성
CREATE TABLE IF NOT EXISTS baseball_teams (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    alias VARCHAR(255),
    logo VARCHAR(255),
    label_font_color VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- sections 테이블 생성
CREATE TABLE IF NOT EXISTS sections (
    id BIGINT NOT NULL PRIMARY KEY,
    stadium_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    alias VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- blocks 테이블 생성
CREATE TABLE IF NOT EXISTS blocks (
    id BIGINT NOT NULL PRIMARY KEY,
    stadium_id BIGINT NOT NULL,
    section_id BIGINT NOT NULL,
    code VARCHAR(255) NOT NULL,
    max_rows INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- block_rows 테이블 생성
CREATE TABLE IF NOT EXISTS block_rows (
    id BIGINT NOT NULL PRIMARY KEY,
    block_id BIGINT NOT NULL,
    number INT NOT NULL,
    max_seats INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- seats 테이블 생성
CREATE TABLE IF NOT EXISTS seats (
    id BIGINT NOT NULL PRIMARY KEY,
    stadium_id BIGINT NOT NULL,
    section_id BIGINT NOT NULL,
    block_id BIGINT NOT NULL,
    row_id BIGINT NOT NULL,
    seat_number INT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- reviews 테이블 생성
CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT NOT NULL PRIMARY KEY,
    member_id BIGINT NOT NULL,
    stadium_id BIGINT NOT NULL,
    section_id BIGINT NOT NULL,
    block_id BIGINT NOT NULL,
    row_id BIGINT,
    seat_id BIGINT,
    date_time TIMESTAMP NOT NULL,
    content VARCHAR(300),
    likes_count INT DEFAULT 0,
    scraps_count INT DEFAULT 0,
    review_type VARCHAR(20),
    version BIGINT DEFAULT 0,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- review_likes 테이블 생성
CREATE TABLE IF NOT EXISTS review_likes (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    review_id BIGINT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    UNIQUE KEY uk_review_likes (member_id, review_id)
);

-- levels 데이터 삽입
INSERT INTO levels (id, value, title, mascot_image_url, created_at, updated_at, deleted_at)
VALUES (1, 0, '직관 꿈나무', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (2, 1, '직관 첫 걸음', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (3, 2, '경기장 탐험가', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (4, 3, '직관의 여유', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (5, 4, '응원 단장', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (6, 5, '야구장 VIP', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null),
       (7, 6, '전설의 직관러', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null);

-- stadiums 데이터 삽입
INSERT INTO stadiums (id, name, main_image, seating_chart_image, labeled_seating_chart_image, is_active, created_at, updated_at)
VALUES (1, '잠실 야구 경기장', 'main_image_a.jpg', 'seating_chart_a.jpg', 'labeled_seating_chart_a.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- baseball_teams 데이터 삽입
INSERT INTO baseball_teams (id, name, alias, logo, label_font_color, created_at, updated_at)
VALUES (1, 'Team A', 'A', 'logo_a.png', '#FFFFFF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- sections 데이터 삽입
INSERT INTO sections (id, stadium_id, name, alias, created_at, updated_at)
VALUES (1, 1, '오렌지석', '응원석', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- blocks 데이터 삽입
INSERT INTO blocks (id, stadium_id, section_id, code, max_rows, created_at, updated_at)
VALUES (1, 1, 1, '207', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- block_rows 데이터 삽입
INSERT INTO block_rows (id, block_id, number, max_seats, created_at, updated_at)
VALUES (1, 1, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- seats 데이터 삽입
INSERT INTO seats (id, stadium_id, section_id, block_id, row_id, seat_number, created_at, updated_at)
VALUES (1, 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- reviews 데이터 삽입
INSERT INTO reviews (id, member_id, stadium_id, section_id, block_id, row_id, seat_id, date_time, content, likes_count, scraps_count, review_type, version, created_at, updated_at)
VALUES
    (1, 1, 1, 1, 1, 1, 1, '2023-06-01 19:00:00', '좋은 경기였습니다!', 0, 0, 'VIEW', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
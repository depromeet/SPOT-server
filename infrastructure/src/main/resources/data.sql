-- Stadiums
INSERT INTO stadiums (id, name, main_image, seating_chart_image, labeled_seating_chart_image,
                      is_active)
VALUES (1, '잠실 야구 경기장', 'main_image_a.jpg', 'seating_chart_a.jpg', 'labeled_seating_chart_a.jpg',
        1),
       (2, '김포 야구 경기장', 'main_image_b.jpg', 'seating_chart_b.jpg', 'labeled_seating_chart_b.jpg',
        1),
       (3, '부산 야구 경기장', 'main_image_c.jpg', 'seating_chart_c.jpg', 'labeled_seating_chart_c.jpg',
        0);

-- Baseball Teams
INSERT INTO baseball_teams (id, name, alias, logo, label_background_color, label_font_color)
VALUES (1, 'Team A', 'A', 'logo_a.png', '#1F1F52', '#FFFFFF'),
       (2, 'Team B', 'B', 'logo_b.png', '#1E4D9C', '#FFFFFF'),
       (3, 'Team C', 'C', 'logo_c.png', '#D72E34', '#FFFFFF');

-- Stadium Home Teams
INSERT INTO stadium_home_teams (id, stadium_id, team_id)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 1, 2);

-- Stadium Sections
INSERT INTO sections (id, stadium_id, name, alias, label_color)
VALUES (1, 1, '오렌지석', '응원석', '#FFFFFF'),
       (2, 1, '네이비석', '프리미엄석', '#B3B248'),
       (3, 1, '레드석', null, '#CA53A9');

-- Block
INSERT INTO blocks (id, stadium_id, section_id, code, max_rows)
VALUES (1, 1, 1, "207", 3),
       (2, 1, 1, "208", 2),
       (3, 1, 1, "209", 1);

-- Row
INSERT INTO block_rows (id, block_id, number, max_seats)
VALUES (1, 1, 1, 3),
       (2, 1, 2, 5),
       (3, 1, 3, 10),
       (4, 2, 1, 13),
       (5, 2, 2, 20),
       (6, 3, 1, 15);

-- Seats
INSERT INTO seats (id, stadium_id, section_id, block_id, row_id, seat_number)
VALUES (1, 1, 1, 1, 1, 1),
       (2, 1, 1, 1, 1, 2),
       (3, 1, 1, 1, 1, 3),
       (4, 1, 1, 1, 2, 1),
       (5, 1, 1, 1, 2, 2),
       (6, 1, 1, 1, 2, 3),
       (7, 1, 1, 1, 2, 4),
       (8, 1, 1, 1, 2, 5),
       (9, 1, 1, 1, 3, 1),
       (10, 1, 1, 1, 3, 2),
       (11, 1, 1, 1, 3, 3),
       (12, 1, 1, 1, 3, 4),
       (13, 1, 1, 1, 3, 5),
       (14, 1, 1, 1, 3, 6),
       (15, 1, 1, 1, 3, 7),
       (16, 1, 1, 1, 3, 8),
       (17, 1, 1, 1, 3, 9),
       (18, 1, 1, 1, 3, 10),
       (19, 1, 1, 2, 4, 1),
       (20, 1, 1, 2, 4, 2),
       (21, 1, 1, 2, 4, 3),
       (22, 1, 1, 2, 4, 4),
       (23, 1, 1, 2, 4, 5),
       (24, 1, 1, 2, 4, 6),
       (25, 1, 1, 2, 4, 7),
       (26, 1, 1, 2, 4, 8),
       (27, 1, 1, 2, 4, 9),
       (28, 1, 1, 2, 4, 10),
       (29, 1, 1, 2, 4, 11),
       (30, 1, 1, 2, 4, 12),
       (31, 1, 1, 2, 4, 13),
       (32, 1, 1, 2, 5, 1),
       (33, 1, 1, 2, 5, 2),
       (34, 1, 1, 2, 5, 3),
       (35, 1, 1, 2, 5, 4),
       (36, 1, 1, 2, 5, 5),
       (37, 1, 1, 2, 5, 6),
       (38, 1, 1, 2, 5, 7),
       (39, 1, 1, 2, 5, 8),
       (40, 1, 1, 2, 5, 9),
       (41, 1, 1, 2, 5, 10),
       (42, 1, 1, 2, 5, 11),
       (43, 1, 1, 2, 5, 12),
       (44, 1, 1, 2, 5, 13),
       (45, 1, 1, 2, 5, 14),
       (46, 1, 1, 2, 5, 15),
       (47, 1, 1, 2, 5, 16),
       (48, 1, 1, 2, 5, 17),
       (49, 1, 1, 2, 5, 18),
       (50, 1, 1, 2, 5, 19),
       (51, 1, 1, 2, 5, 20),
       (52, 1, 1, 3, 6, 1),
       (53, 1, 1, 3, 6, 2),
       (54, 1, 1, 3, 6, 3),
       (55, 1, 1, 3, 6, 4),
       (56, 1, 1, 3, 6, 5),
       (57, 1, 1, 3, 6, 6),
       (58, 1, 1, 3, 6, 7),
       (59, 1, 1, 3, 6, 8),
       (60, 1, 1, 3, 6, 9),
       (61, 1, 1, 3, 6, 10),
       (62, 1, 1, 3, 6, 11),
       (63, 1, 1, 3, 6, 12),
       (64, 1, 1, 3, 6, 13),
       (65, 1, 1, 3, 6, 14),
       (66, 1, 1, 3, 6, 15);

-- keywords
INSERT INTO keywords (id, content, is_positive, created_at, updated_at)
VALUES (1, '좋아요', true, '2023-06-01 22:00:00', '2023-06-01 22:00:00'),
       (2, '재미있어요', true, '2023-06-01 22:00:00', '2023-06-01 22:00:00'),
       (3, '시야좋음', true, '2023-06-02 22:00:00', '2023-06-02 22:00:00'),
       (4, '편안해요', true, '2023-06-03 22:00:00', '2023-06-03 22:00:00'),
       (5, '아쉬워요', false, '2023-06-04 22:00:00', '2023-06-04 22:00:00'),
       (6, '멋져요', true, '2023-06-05 22:00:00', '2023-06-05 22:00:00');

-- reviews
INSERT INTO reviews (id, member_id, stadium_id, section_id, block_id, row_id, seat_id, date_time,
                     content, deleted_at, created_at, updated_at)
VALUES (1, 1, 1, 1, 1, 1, 1, '2023-06-01 19:00:00', '좋은 경기였습니다!', NULL, '2023-06-01 22:00:00',
        '2023-06-01 22:00:00'),
       (2, 2, 1, 1, 1, 2, 2, '2023-06-02 19:00:00', '시야가 좋았어요', NULL, '2023-06-02 22:00:00',
        '2023-06-02 22:00:00'),
       (3, 3, 1, 2, 2, 3, 3, '2023-06-03 19:00:00', '다음에 또 오고 싶어요', NULL, '2023-06-03 22:00:00',
        '2023-06-03 22:00:00'),
       (4, 1, 2, 3, 3, 4, 4, '2023-06-04 19:00:00', '아쉬운 경기였습니다', NULL, '2023-06-04 22:00:00',
        '2023-06-04 22:00:00'),
       (5, 2, 2, 3, 3, 5, 5, '2023-06-05 19:00:00', '멋진 경기였어요!', NULL, '2023-06-05 22:00:00',
        '2023-06-05 22:00:00');

-- review_images
INSERT INTO review_images (id, review_id, url, created_at, updated_at)
VALUES (1, 1, 'http://example.com/image1.jpg', '2023-06-01 22:00:00', '2023-06-01 22:00:00'),
       (2, 1, 'http://example.com/image2.jpg', '2023-06-01 22:00:00', '2023-06-01 22:00:00'),
       (3, 2, 'http://example.com/image3.jpg', '2023-06-02 22:00:00', '2023-06-02 22:00:00'),
       (4, 3, 'http://example.com/image4.jpg', '2023-06-03 22:00:00', '2023-06-03 22:00:00'),
       (5, 4, 'http://example.com/image5.jpg', '2023-06-04 22:00:00', '2023-06-04 22:00:00');

-- review_keywords (중간 테이블로 변경)
INSERT INTO review_keywords (id, review_id, keyword_id, created_at, updated_at)
VALUES (1, 1, 1, '2023-06-01 22:00:00', '2023-06-01 22:00:00'),
       (2, 1, 2, '2023-06-01 22:00:00', '2023-06-01 22:00:00'),
       (3, 2, 3, '2023-06-02 22:00:00', '2023-06-02 22:00:00'),
       (4, 3, 4, '2023-06-03 22:00:00', '2023-06-03 22:00:00'),
       (5, 4, 5, '2023-06-04 22:00:00', '2023-06-04 22:00:00'),
       (6, 5, 6, '2023-06-05 22:00:00', '2023-06-05 22:00:00');

-- block_top_keywords
INSERT INTO block_top_keywords (id, block_id, keyword_id, count, created_at, updated_at)
VALUES (1, 1, 1, 10, '2023-06-01 22:00:00', '2023-06-01 22:00:00'),
       (2, 1, 2, 8, '2023-06-01 22:00:00', '2023-06-01 22:00:00'),
       (3, 2, 3, 15, '2023-06-02 22:00:00', '2023-06-02 22:00:00'),
       (4, 3, 4, 12, '2023-06-03 22:00:00', '2023-06-03 22:00:00'),
       (5, 3, 5, 5, '2023-06-04 22:00:00', '2023-06-04 22:00:00');

-- levels
INSERT INTO levels (id, value, title, mascot_image_url, created_at, updated_at, deleted_at)
VALUES (1, 0, '직관 꿈나무', null, null, null, null),
       (2, 1, '직관 첫 걸음', null, null, null, null),
       (3, 2, '경기장 탐험가', null, null, null, null),
       (4, 3, '직관의 여유', null, null, null, null),
       (5, 4, '응원 단장', null, null, null, null),
       (6, 5, '야구장 VIP', null, null, null, null),
       (7, 6, '전설의 직관러', null, null, null, null);


-- members
INSERT INTO members (id, email, name, nickname, phone_number, level_id, profile_image, sns_provider,
                     id_token, team_id, role, created_at, updated_at)
VALUES (1, 'user1@example.com', '김철수', '축구팬1', '010-1234-5678', 1,
        'http://example.com/profile1.jpg', 'KAKAO', 'idtoken1', 1, 'ROLE_USER',
        '2023-06-01 10:00:00', '2023-06-01 10:00:00'),
       (2, 'user2@example.com', '이영희', '야구매니아', '010-2345-6789', 1,
        'http://example.com/profile2.jpg', 'KAKAO', 'idtoken2', 2, 'ROLE_USER',
        '2023-06-02 11:00:00', '2023-06-02 11:00:00');
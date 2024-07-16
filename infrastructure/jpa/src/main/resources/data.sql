-- Stadiums
INSERT INTO stadiums (id, name, main_image, seating_chart_image, labeled_seating_chart_image, is_active)
VALUES (1, '잠실 야구 경기장', 'main_image_a.jpg', 'seating_chart_a.jpg', 'labeled_seating_chart_a.jpg', 1),
       (2, '김포 야구 경기장', 'main_image_b.jpg', 'seating_chart_b.jpg', 'labeled_seating_chart_b.jpg', 1),
       (3, '부산 야구 경기장', 'main_image_c.jpg', 'seating_chart_c.jpg', 'labeled_seating_chart_c.jpg', 0);

-- Baseball Teams
INSERT INTO baseball_teams (id, name, alias, logo, red, green, blue)
VALUES (1, 'Team A', 'A', 'logo_a.png', 255, 0, 0),
       (2, 'Team B', 'B', 'logo_b.png', 0, 255, 0),
       (3, 'Team C', 'C', 'logo_c.png', 0, 0, 255);

-- Stadium Home Teams
INSERT INTO stadium_home_teams (id, stadium_id, team_id)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 1, 2);

-- Stadium Sections
INSERT INTO sections (id, stadium_id, name, alias, red, green, blue)
VALUES (1, 1, '오렌지석', '응원석', 255, 255, 255),
       (2, 1, '네이비석', '프리미엄석', 100, 100, 100),
       (3, 1, '레드석', null, 100, 0, 0);

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

-- Reviews
INSERT INTO reviews (id, user_id, stadium_id, block_id, row_id, seat_number, date_time, content, created_at, updated_at)
VALUES
(1, 1, 1, 1, 1, 10, '2023-07-15 14:00:00', '좋은 경기였습니다!', '2023-07-15 18:00:00', '2023-07-15 18:00:00'),
(2, 2, 1, 1, 2, 15, '2023-07-16 15:00:00', '시야가 좋았어요', '2023-07-16 19:00:00', '2023-07-16 19:00:00'),
(3, 3, 1, 2, 1, 5, '2023-07-17 16:00:00', '다음에 또 오고 싶어요', '2023-07-17 20:00:00', '2023-07-17 20:00:00');

-- Review Images
INSERT INTO review_images (id, review_id, url, created_at)
VALUES (1, 1, 'review1_image1.jpg', '2023-07-15 18:00:00'),
       (2, 1, 'review1_image2.jpg', '2023-07-15 18:00:00'),
       (3, 2, 'review2_image1.jpg', '2023-07-16 19:00:00'),
       (4, 3, 'review3_image1.jpg', '2023-07-17 20:00:00');

-- Keywords
INSERT INTO keywords (id, content)
VALUES (1, '좋아요'),
       (2, '시야 좋음'),
       (3, '재방문 의사'),
       (4, '편안함');

-- Review Keywords
INSERT INTO review_keywords (id, review_id, keyword_id, is_positive)
VALUES (1, 1, 1, 1),
       (2, 1, 4, 1),
       (3, 2, 2, 1),
       (4, 3, 3, 1);

-- Block Top Keywords
INSERT INTO block_top_keywords (id, block_id, keyword_id, count)
VALUES (1, 1, 1, 10),
       (2, 1, 2, 8),
       (3, 2, 3, 5),
       (4, 2, 4, 3);
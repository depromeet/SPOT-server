-- levels
INSERT INTO levels (id, value, title, mascot_image_url, created_at, updated_at, deleted_at)
VALUES (1, 0, '직관 꿈나무', null, null, null, null),
       (2, 1, '직관 첫 걸음', null, null, null, null),
       (3, 2, '경기장 탐험가', null, null, null, null),
       (4, 3, '직관의 여유', null, null, null, null),
       (5, 4, '응원 단장', null, null, null, null),
       (6, 5, '야구장 VIP', null, null, null, null),
       (7, 6, '전설의 직관러', null, null, null, null);

-- Stadiums
INSERT INTO stadiums (id, name, main_image, seating_chart_image, labeled_seating_chart_image,
                      is_active)
VALUES (1, '잠실 야구 경기장', 'main_image_a.jpg', 'seating_chart_a.jpg', 'labeled_seating_chart_a.jpg',
        1);

-- Baseball Teams
INSERT INTO baseball_teams (id, name, alias, logo, label_font_color)
VALUES (1, 'Team A', 'A', 'logo_a.png', '#FFFFFF');

-- Stadium Sections
INSERT INTO sections (id, stadium_id, name, alias)
VALUES (1, 1, '오렌지석', '응원석');

-- Block
INSERT INTO blocks (id, stadium_id, section_id, code, max_rows)
VALUES (1, 1, 1, "207", 3);

-- Row
INSERT INTO block_rows (id, block_id, number, max_seats)
VALUES (1, 1, 1, 3);

-- Seats
INSERT INTO seats (id, stadium_id, section_id, block_id, row_id, seat_number)
VALUES (1, 1, 1, 1, 1, 1);

-- reviews
INSERT INTO reviews (id, member_id, stadium_id, section_id, block_id, row_id, seat_id, date_time, content, likes_count, scraps_count, review_type)
VALUES
    (1, 1, 1, 1, 1, 1, 1, '2023-06-01 19:00:00', '좋은 경기였습니다!', 0, 0, 'VIEW'),
    (2, 1, 1, 1, 1, 1, 1, '2023-06-01 19:00:00', '좋은 경기였습니다!', 0, 0, 'VIEW');
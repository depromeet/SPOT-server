-- Stadiums
INSERT INTO stadiums (id, name, main_image, seating_chart_image, labeled_seating_chart_image, is_active)
VALUES (1, '잠실 야구 경기장', 'main_image_a.jpg', 'seating_chart_a.jpg', 'labeled_seating_chart_a.jpg', true),
       (2, '김포 야구 경기장', 'main_image_b.jpg', 'seating_chart_b.jpg', 'labeled_seating_chart_b.jpg', true),
       (3, '부산 야구 경기장', 'main_image_c.jpg', 'seating_chart_c.jpg', 'labeled_seating_chart_c.jpg', false);

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

-- Demo map 187 - Khu luyen tap Miku
-- Layout/tile/background duoc clone tu map 1 (Doi hoa cuc).
-- Script co the chay lai: neu map 187 da ton tai thi metadata se duoc cap nhat.

INSERT INTO `map_template`
    (`id`, `NAME`, `zones`, `max_player`, `data`, `type`, `planet_id`,
     `bg_type`, `tile_id`, `bg_id`, `waypoints`, `mobs`, `npcs`, `is_map_double`)
SELECT
    187,
    'Khu luyện tập Miku',
    5,
    20,
    `data`,
    `type`,
    `planet_id`,
    `bg_type`,
    `tile_id`,
    `bg_id`,
    '[["Làng Aru",0,360,24,384,0,0,0,1188,432]]',
    '[[0,5,5000,348,384],[0,5,5000,540,408],[1,7,8000,804,408],[1,7,8000,996,360]]',
    '[]',
    0
FROM `map_template`
WHERE `id` = 1
ON DUPLICATE KEY UPDATE
    `NAME` = 'Khu luyện tập Miku',
    `zones` = 5,
    `max_player` = 20,
    `waypoints` = '[["Làng Aru",0,360,24,384,0,0,0,1188,432]]',
    `mobs` = '[[0,5,5000,348,384],[0,5,5000,540,408],[1,7,8000,804,408],[1,7,8000,996,360]]',
    `npcs` = '[]';


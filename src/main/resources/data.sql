INSERT INTO `workstation_type` (`id`, `name`)
VALUES (1, 'Farm'),
       (2, 'Pen/Pasture'),
       (3, 'Beehive'),
       (4, 'Butcher'),
       (5, 'Fishery'),

       (13, 'Brewery'),
       (14, 'Distillery'),
       (7, 'Millstone'),
       (10, 'Oven'),
       (11, 'Stove');

INSERT INTO `resource` (`id`, `saturation`, `name`)
VALUES (1, NULL, 'chicken'),
       (2, NULL, 'sheep'),
       (3, NULL, 'goat'),
       (4, NULL, 'pig'),
       (5, NULL, 'cow'),
       (6, -10, 'raw chicken'),
       (7, -5, 'raw mutton'),
       (8, -5, 'raw pork'),
       (9, 100, 'legendary meat'),
       (10, 5, 'honey'),
       (11, -10, 'raw egg'),
       (12, 1, 'milk');

INSERT INTO `job` (`id`, `workstation_type_id`, `name`)
VALUES (1, 2, 'raise chicken'),
       (2, 2, 'raise sheep'),
       (3, 2, 'raise goat'),
       (4, 2, 'raise pig'),
       (5, 2, 'raise cow'),
       (6, 2, 'collect eggs'),
       (7, 2, 'milk cow'),
       (8, 3, 'collect honey'),
       (9, 4, 'slaughter chicken'),
       (10, 4, 'slaughter sheep'),
       (11, 4, 'slaughter goat'),
       (12, 4, 'slaughter pig'),
       (13, 4, 'slaughter cow');

INSERT INTO `job_input` (`amount`, `id`, `job_id`, `resource_id`)
VALUES (1, 1, 7, 1),
       (1, 2, 8, 2),
       (1, 3, 9, 3),
       (1, 4, 10, 4),
       (1, 5, 11, 5),
       (1, 6, 12, 1),
       (1, 7, 13, 5);

INSERT INTO `job_product` (`amount`, `id`, `job_id`, `resource_id`)
VALUES (2, 1, 1, 1),
       (1, 2, 2, 2),
       (1, 3, 3, 3),
       (1, 4, 4, 4),
       (1, 5, 5, 5),
       (3, 6, 6, 10),
       (2, 7, 7, 6),
       (4, 8, 8, 7),
       (3, 9, 9, 7),
       (7, 10, 10, 8),
       (6, 11, 11, 9),
       (6, 12, 12, 11),
       (12, 13, 13, 12);
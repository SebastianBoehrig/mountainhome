INSERT INTO `workstation_type` (`name`)
VALUES ('Farm');

INSERT INTO `resource` (`id`, `name`)
VALUES (1, 'Egg');

INSERT INTO `job` (`name`, `workstation_type_name`)
VALUES ('Collect Eggs', 'Farm');

INSERT INTO `job_product` (`job_id`, `resource_id`, `amount`)
VALUES ( 1, 1, 12);
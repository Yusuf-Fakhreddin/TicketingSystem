DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `active` tinyint NOT NULL,
  ADD COLUMN `team_id` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`team_id`) REFERENCES `team` (`id`);
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Default passwords here are: fun123 using bcrypt calculator

INSERT INTO `users`
VALUES
(1,'john','{noop}fun123',1,1),
(2,'mary','{noop}fun123',1,2),
(3,'susan','{noop}fun123',1,3);

CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `authorities5_idx_1` (`user_id`,`role`),
  CONSTRAINT `authorities5_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `roles`
VALUES
('john','ROLE_EMPLOYEE'),
('mary','ROLE_EMPLOYEE'),
('mary','ROLE_MANAGER'),
('susan','ROLE_EMPLOYEE'),
('susan','ROLE_MANAGER'),
('susan','ROLE_ADMIN');
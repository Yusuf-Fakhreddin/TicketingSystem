DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `status` VARCHAR(255),
  `priority` VARCHAR(255),
  `type` VARCHAR(255),
  `date` VARCHAR(255),
  `time` VARCHAR(255),
  `owner_id` VARCHAR(255),
  `owner_team_id` VARCHAR(255)
  `assigned_user` VARCHAR(255),
  `assigned_team` VARCHAR(255),
  FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`),
  FOREIGN KEY (`owner_team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `ticket`
VALUES
(1,'This is a title.','This is a description.','Open','High','Task','2022-12-01','10:00:00',1,1,2,2),
(2,'This is another title.','This is another description.','Open','High','Task','2022-12-02','11:00:00',2,2,3,3),
(3,'This is a third title.','This is a third description.','Open','High','Task','2022-12-03','12:00:00',3,3,1,1);
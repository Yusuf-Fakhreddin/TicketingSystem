CREATE TABLE `tickets` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `status` VARCHAR(255),
  `priority` VARCHAR(255),
  `type` VARCHAR(255),
  `date` VARCHAR(255),
  `time` VARCHAR(255),
  `owner`  varchar(50) ,
  `owner_team_id`  int ,
  `assigned_user`  varchar(50) ,
  `assigned_team`  varchar(50) ,
  FOREIGN KEY (`owner`) REFERENCES `users` (`username`),
  FOREIGN KEY (`owner_team_id`) REFERENCES `teams` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--insert 3 tickets
INSERT INTO `tickets` (`title`, `description`, `status`, `priority`, `type`, `date`, `time`, `owner`, `owner_team_id`, `assigned_user`, `assigned_team`) VALUES ('Ticket 1', 'This is ticket 1', 'Open', 'High', 'Bug', '2019-12-01', '12:00', 'admin', 1, 'admin', 'admin');
INSERT INTO `tickets` (`title`, `description`, `status`, `priority`, `type`, `date`, `time`, `owner`, `owner_team_id`, `assigned_user`, `assigned_team`) VALUES ('Ticket 2', 'This is ticket 2', 'Open', 'High', 'Bug', '2019-12-01', '12:00', 'admin', 1, 'admin', 'admin');
INSERT INTO `tickets` (`title`, `description`, `status`, `priority`, `type`, `date`, `time`, `owner`, `owner_team_id`, `assigned_user`, `assigned_team`) VALUES ('Ticket 3', 'This is ticket 3', 'Open', 'High', 'Bug', '2019-12-01', '12:00', 'admin', 1, 'admin', 'admin');


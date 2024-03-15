CREATE TABLE `user_teams` (
  `username` varchar(50) NOT NULL,
  `team_id` int(11) NOT NULL,
  PRIMARY KEY (`username`, `team_id`),
  FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
);

INSERT INTO `user_teams` (`username`, `team_id`) VALUES ('john', 1);
INSERT INTO `user_teams` (`username`, `team_id`) VALUES ('susan', 1);
INSERT INTO `user_teams` (`username`, `team_id`) VALUES ('mary', 2);

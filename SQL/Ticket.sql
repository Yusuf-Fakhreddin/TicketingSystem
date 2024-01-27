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
  `owner` VARCHAR(255),
  `assigned_user` VARCHAR(255),
  `assigned_team` VARCHAR(255)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `ticket` (`title`, `description`, `status`, `priority`, `type`, `date`, `time`, `owner`, `assigned_user`, `assigned_team`)
VALUES 
('Ticket 1', 'Description for ticket 1', 'Open', 'High', 'Bug', '2023-01-01', '10:00:00', 'Owner 1', 'User 1', 'Team 1'),
('Ticket 2', 'Description for ticket 2', 'Closed', 'Medium', 'Feature', '2023-01-02', '11:00:00', 'Owner 2', 'User 2', 'Team 2'),
('Ticket 3', 'Description for ticket 3', 'In Progress', 'Low', 'Improvement', '2023-01-03', '12:00:00', 'Owner 3', 'User 3', 'Team 3'),
('Ticket 4', 'Description for ticket 4', 'Open', 'High', 'Bug', '2023-01-04', '13:00:00', 'Owner 4', 'User 4', 'Team 4'),
('Ticket 5', 'Description for ticket 5', 'Closed', 'Medium', 'Feature', '2023-01-05', '14:00:00', 'Owner 5', 'User 5', 'Team 5');


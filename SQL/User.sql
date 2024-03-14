-- Drop the existing tables
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `tickets`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `Team`;

-- Drop the existing tables
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `tickets`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `user_teams`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `teams`;

-- Create a new Team table
CREATE TABLE `teams` (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
    -- Add other columns as needed
);

-- Insert data into the Team table
INSERT INTO `teams` (id, name) VALUES (1, 'DEVELOPMENT');
INSERT INTO `teams` (id, name) VALUES (2, 'DEVOPS');
INSERT INTO `teams` (id, name) VALUES (3, 'QA');
INSERT INTO `teams` (id, name) VALUES (4, 'SUPPORT');

-- Create a new Users table
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`)
);

-- Insert data into the Users table
INSERT INTO `users`
VALUES
('john','{noop}fun123',1),
('mary','{noop}fun123',1),
('susan','{noop}fun123',1);

-- Create a new User_Teams table
CREATE TABLE `user_teams` (
  `username` varchar(50) NOT NULL,
  `team_id` int(11) NOT NULL,
  PRIMARY KEY (`username`, `team_id`),
  FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
);

-- Insert data into the User_Teams table
INSERT INTO `user_teams`
VALUES
('john',1),
('mary',2),
('susan',3),
('susan',4);

-- Create a new Roles table
CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `authorities5_idx_1` (`username`,`role`),
  CONSTRAINT `authorities5_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
);

-- Insert data into the Roles table
INSERT INTO `roles`
VALUES
('john','ROLE_EMPLOYEE'),
('mary','ROLE_EMPLOYEE'),
('mary','ROLE_MANAGER'),
('susan','ROLE_EMPLOYEE'),
('susan','ROLE_MANAGER'),
('susan','ROLE_ADMIN');

-- Create a new Tickets table
CREATE TABLE `tickets` (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status varchar(255) NOT NULL,
    priority varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    resolution VARCHAR(255),
    owner VARCHAR(255) NOT NULL,
    owner_team_id INT NOT NULL,
    assigned_user VARCHAR(255) NOT NULL,
    assigned_team VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_team_id) REFERENCES `teams`(id)
);

-- Insert data into the Tickets table
INSERT INTO `tickets` (title, description, status, priority, type, date, time, resolution, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is a title', 'This is a description', 'OPEN', 'HIGH', 'BUG', '2022-12-01', '10:00:00', 'This is a resolution', 'John Doe', 1, 'Jane Doe', 'DEVELOPMENT');
INSERT INTO `tickets` (title, description, status, priority, type, date, time, resolution, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is another title', 'This is another description', 'CLOSED', 'LOW', 'FEATURE', '2022-12-02', '11:00:00', 'This is another resolution', 'Jane Doe', 2, 'John Doe', 'DEVOPS');
INSERT INTO `tickets` (title, description, status, priority, type, date, time, resolution, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is a third title', 'This is a third description', 'IN PROGRESS', 'MEDIUM', 'ENHANCEMENT', '2022-12-03', '12:00:00', 'This is a third resolution', 'John Doe', 3, 'Jane Doe', 'QA');

-- Create a new Comment table
CREATE TABLE `comment` (
    id INT NOT NULL AUTO_INCREMENT,
    ticket_id INT,
    comment VARCHAR(255),
    date VARCHAR(255),
    time VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES `tickets`(id)
);

-- Insert data into the Comment table
INSERT INTO `comment` (ticket_id, comment, date, time) VALUES (1, 'This is a comment.', '2022-12-01', '10:00:00');
INSERT INTO `comment` (ticket_id, comment, date, time) VALUES (1, 'This is another comment.', '2022-12-02', '11:00:00');
INSERT INTO `comment` (ticket_id, comment, date, time) VALUES (2, 'This is a comment for a different ticket.', '2022-12-03', '12:00:00');
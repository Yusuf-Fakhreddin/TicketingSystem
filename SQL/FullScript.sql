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
    `name` VARCHAR(255) NOT NULL
    -- Add other columns as needed
);

-- Insert data into the Team table
INSERT INTO `teams` (id, name) VALUES (1, 'DEVELOPMENT');
INSERT INTO `teams` (id, name) VALUES (2, 'DEVOPS');
INSERT INTO `teams` (id, name) VALUES (3, 'QA');
INSERT INTO `teams` (id, name) VALUES (4, 'SUPPORT');

-- Create a new Users table
CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Insert data into the Users table
INSERT INTO `users`
VALUES
('john','{noop}fun123',1),
('mary','{noop}fun123',1),
('susan','{noop}fun123',1);

-- Create a new Roles table
CREATE TABLE `roles` (
  `username` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `authorities5_idx_1` (`username`,`role`),
  CONSTRAINT `authorities5_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

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
    assigned_team_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES users(username),
    FOREIGN KEY (owner_team_id) REFERENCES `teams`(id),
    FOREIGN KEY (assigned_user) REFERENCES users(username),
    FOREIGN KEY (assigned_team_id) REFERENCES teams(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Insert data into the Tickets table
INSERT INTO tickets (title, description, status, priority, type, date, time, owner, owner_team_id, assigned_user, assigned_team_id) VALUES ('This is a title', 'This is a description', 'QUEUED', 'NORMAL', 'ENHANCEMENT', '2020-12-12', '12:00', 'john', 1, 'susan', 2);
INSERT INTO tickets (title, description, status, priority, type, date, time, owner, owner_team_id, assigned_user, assigned_team_id) VALUES ('This is another title', 'This is another description', 'IN_PROGRESS', 'HIGH', 'ENHANCEMENT', '2020-12-12', '12:00', 'susan', 1, 'john', 2);
INSERT INTO tickets (title, description, status, priority, type, date, time, owner, owner_team_id, assigned_user, assigned_team_id) VALUES ('This is a third title', 'This is a third description', 'QUEUED', 'HIGH', 'BUG', '2020-12-12', '12:00', 'susan', 1, 'john', 2);
-- Create a new Comment table
DROP TABLE IF EXISTS `comment`;

CREATE TABLE comment (
    id INT NOT NULL AUTO_INCREMENT,
    ticket_id INT,
    comment VARCHAR(255),
    date VARCHAR(255),
    time VARCHAR(255),
	author_username VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES tickets(id),
    FOREIGN KEY (author_username) REFERENCES users(username)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- insert statements
INSERT INTO comment (ticket_id, comment, date, time, author_username) VALUES (1, 'This is a comment', '2020-12-12', '12:00', 'john');
INSERT INTO comment (ticket_id, comment, date, time, author_username) VALUES (1, 'This is another comment', '2020-12-12', '12:00', 'susan');
INSERT INTO comment (ticket_id, comment, date, time, author_username) VALUES (2, 'This is a third comment', '2020-12-12', '12:00', 'susan');


CREATE TABLE `user_teams` (
  `username` varchar(255) NOT NULL,
  `team_id` INT NOT NULL,
  PRIMARY KEY (`username`, `team_id`),
  FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user_teams` (`username`, `team_id`) VALUES ('john', 1);
INSERT INTO `user_teams` (`username`, `team_id`) VALUES ('susan', 1);
INSERT INTO `user_teams` (`username`, `team_id`) VALUES ('mary', 2);

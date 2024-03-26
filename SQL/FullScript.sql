-- Drop the existing tables
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `roles`;
DROP Table if exists `attachment`;
DROP TABLE IF EXISTS `user_teams`;
DROP TABLE IF EXISTS `tickets`;
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
    created_at DATETIME,
    updated_at DATETIME,
    resolution VARCHAR(255),
    owner VARCHAR(255) NULL,
    owner_team_id INT NOT NULL,
    assigned_user VARCHAR(255) NULL,
    assigned_team_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES users(username),
    FOREIGN KEY (owner_team_id) REFERENCES `teams`(id),
    FOREIGN KEY (assigned_user) REFERENCES users(username),
    FOREIGN KEY (assigned_team_id) REFERENCES teams(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Insert data into the Tickets table
INSERT INTO `tickets` ( title, description, status, priority, type, created_at, updated_at, resolution, owner, owner_team_id, assigned_user, assigned_team_id) VALUES ( 'Title 1', 'Description 1', 'QUEUED', 'URGENT', 'BUG', '2020-12-12 12:00:00', '2020-12-12 12:00:00', 'RESOLVED', 'john', 1, 'susan', 1);
INSERT INTO `tickets` (title, description, status, priority, type, created_at, updated_at, resolution, owner, owner_team_id, assigned_user, assigned_team_id) VALUES ( 'Title 2', 'Description 2', 'QUEUED', 'NORMAL', 'BUG', '2020-12-12 12:00:00', '2020-12-12 12:00:00', 'RESOLVED', 'mary', 2, 'susan', 1);
INSERT INTO `tickets` (title, description, status, priority, type, created_at, updated_at, resolution, owner, owner_team_id, assigned_user, assigned_team_id) VALUES ( 'Title 3', 'Description 3', 'QUEUED', 'NORMAL', 'ENHANCEMENT', '2020-12-12 12:00:00', '2020-12-12 12:00:00', 'RESOLVED', 'susan', 1, 'mary', 2);
-- Create a new Comment table
DROP TABLE IF EXISTS `comment`;

CREATE TABLE comment (
    id INT NOT NULL AUTO_INCREMENT,
    ticket_id INT,
    comment VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME,
	author_username VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES tickets(id),
    FOREIGN KEY (author_username) REFERENCES users(username)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- insert statements
INSERT INTO `comment` (ticket_id, comment, created_at, updated_at, author_username) VALUES (1, 'Comment 1', '2020-12-12 12:00:00', '2020-12-12 12:00:00', 'john');
INSERT INTO `comment` (ticket_id, comment, created_at, updated_at, author_username) VALUES (1, 'Comment 2', '2020-12-12 12:00:00', '2020-12-12 12:00:00', 'susan');
INSERT INTO `comment` (ticket_id, comment, created_at, updated_at, author_username) VALUES (2, 'Comment 3', '2020-12-12 12:00:00', '2020-12-12 12:00:00', 'mary');

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


CREATE TABLE attachment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(255) NOT NULL,
    data BLOB NOT NULL,
    ticket_id INT,
    FOREIGN KEY (ticket_id) REFERENCES tickets(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

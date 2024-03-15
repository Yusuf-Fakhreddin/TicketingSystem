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
    FOREIGN KEY (owner_team_id) REFERENCES `teams`(id),
    FOREIGN KEY (assigned_user) REFERENCES users(username),
    FOREIGN KEY (assigned_team) REFERENCES teams(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO tickets (title, description, status, priority, type, date, time, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is a title', 'This is a description', 'QUEUED', 'NORMAL', 'ENHANCEMENT', '2020-12-12', '12:00', 'john', 1, 'susan', 2);
INSERT INTO tickets (title, description, status, priority, type, date, time, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is another title', 'This is another description', 'IN_PROGRESS', 'HIGH', 'ENHANCEMENT', '2020-12-12', '12:00', 'susan', 1, 'john', 2);
INSERT INTO tickets (title, description, status, priority, type, date, time, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is a third title', 'This is a third description', 'QUEUED', 'HIGH', 'BUG', '2020-12-12', '12:00', 'susan', 1, 'john', 2);
-- Purpose: Create a table for tickets
-- Create a sql script to drop table and recreate it and insert data based on my ticket entity
-- Drop the existing tickets table
DROP TABLE IF EXISTS tickets;
-- Create a new tickets table
CREATE TABLE tickets (
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
    FOREIGN KEY (owner_team_id) REFERENCES Team(id)

--insert 3 tickets using my enum values
INSERT INTO tickets (title, description, status, priority, type, date, time, resolution, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is a title', 'This is a description', 'OPEN', 'HIGH', 'BUG', '2022-12-01', '10:00:00', 'This is a resolution', 'John Doe', 1, 'Jane Doe', 'DEVELOPMENT');
INSERT INTO tickets (title, description, status, priority, type, date, time, resolution, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is another title', 'This is another description', 'CLOSED', 'LOW', 'FEATURE', '2022-12-02', '11:00:00', 'This is another resolution', 'Jane Doe', 2, 'John Doe', 'DEVOPS');
INSERT INTO tickets (title, description, status, priority, type, date, time, resolution, owner, owner_team_id, assigned_user, assigned_team) VALUES ('This is a third title', 'This is a third description', 'IN PROGRESS', 'MEDIUM', 'ENHANCEMENT', '2022-12-03', '12:00:00', 'This is a third resolution', 'John Doe', 3, 'Jane Doe', 'QA');

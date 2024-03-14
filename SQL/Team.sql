-- create a sql script to drop table and recreate it and insert data based on my team entity
-- Drop the existing Team table
DROP TABLE IF EXISTS Team;

-- Create a new Team table
CREATE TABLE Team (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
    -- Add other columns as needed
);

-- Insert data into the Team table
INSERT INTO Team (id, name) VALUES (1, 'DEVELOPMENT');
INSERT INTO Team (id, name) VALUES (2, 'DEVOPS');
Insert INTO Team (id, name) VALUES (3, 'QA');
Insert INTO Team (id, name) VALUES (4, 'SUPPORT');

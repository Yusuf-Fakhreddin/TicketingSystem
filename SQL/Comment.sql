DROP TABLE IF EXISTS `comment`;

CREATE TABLE comment (
    id INT NOT NULL AUTO_INCREMENT,
    ticket_id INT,
    comment VARCHAR(255),
    date VARCHAR(255),
    time VARCHAR(255),
author_username VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES ticket(id),
    FOREIGN KEY (author_username) REFERENCES users(username)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- insert statements
INSERT INTO comment (ticket_id, comment, date, time, author_username) VALUES (1, 'This is a comment', '2020-12-12', '12:00', 'john');
INSERT INTO comment (ticket_id, comment, date, time, author_username) VALUES (1, 'This is another comment', '2020-12-12', '12:00', 'susan');
INSERT INTO comment (ticket_id, comment, date, time, author_username) VALUES (2, 'This is a third comment', '2020-12-12', '12:00', 'susan');
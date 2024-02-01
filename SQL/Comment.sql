DROP TABLE IF EXISTS `comment`;

CREATE TABLE comment (
    id INT NOT NULL AUTO_INCREMENT,
    ticket_id INT,
    comment VARCHAR(255),
    date VARCHAR(255),
    time VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES ticket(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO comment (ticket_id, comment, date, time) VALUES (1, 'This is a comment.', '2022-12-01', '10:00:00');
INSERT INTO comment (ticket_id, comment, date, time) VALUES (1, 'This is another comment.', '2022-12-02', '11:00:00');
INSERT INTO comment (ticket_id, comment, date, time) VALUES (2, 'This is a comment for a different ticket.', '2022-12-03', '12:00:00');
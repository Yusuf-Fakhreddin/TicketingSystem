CREATE TABLE attachment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(255) NOT NULL,
    data BLOB NOT NULL,
    ticket_id VARCHAR(255),
    FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);
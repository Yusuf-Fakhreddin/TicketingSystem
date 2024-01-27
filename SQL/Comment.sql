CREATE TABLE `ticket_comments` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `ticket_id` INT,
  `comment` VARCHAR(255),
  FOREIGN KEY (`ticket_id`) REFERENCES `ticket`(`id`)
);
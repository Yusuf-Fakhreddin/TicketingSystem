CREATE TABLE `team` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `team`
VALUES
(1,'Development'),
(2,'Testing'),
(3,'Operations');

CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `adress` varchar(100) NOT NULL,
  `gender` varchar(7) NOT NULL,
  PRIMARY KEY (`id`)
);

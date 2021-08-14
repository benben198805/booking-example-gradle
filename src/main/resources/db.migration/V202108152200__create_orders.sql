CREATE TABLE IF NOT EXISTS `booking_ticket_database`.`orders`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` nvarchar(20) NULL,
  `created_time` TIMESTAMP NULL,
  `updated_time` TIMESTAMP NULL,
  PRIMARY KEY (`id`)
);

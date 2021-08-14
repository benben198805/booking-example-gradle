CREATE TABLE IF NOT EXISTS `booking_ticket_database`.`e_ticket`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(10) NULL,
  `status` nvarchar(20) NULL,
  `callback_id` nvarchar(20) NULL,
  `created_time` TIMESTAMP NULL,
  `updated_time` TIMESTAMP NULL,
  PRIMARY KEY (`id`)
);

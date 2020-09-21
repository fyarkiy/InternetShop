CREATE SCHEMA `internet_store` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet_store`.`products` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(225) NOT NULL,
  `price` DECIMAL(11) NULL DEFAULT 1000,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`));

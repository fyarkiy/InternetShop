CREATE SCHEMA `internet_store` DEFAULT CHARACTER SET utf8;
CREATE TABLE `products` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `product_name` varchar(225) NOT NULL,
                            `price` decimal(11,0) NOT NULL,
                            `deleted` tinyint NOT NULL DEFAULT '0',
                            PRIMARY KEY (`id`)
) ;
CREATE TABLE `roles` (
                         `role_id` bigint NOT NULL AUTO_INCREMENT,
                         `role_name` varchar(45) NOT NULL DEFAULT 'USER',
                         PRIMARY KEY (`role_id`)
) ;
CREATE TABLE `users` (
                         `user_id` bigint NOT NULL AUTO_INCREMENT,
                         `user_name` varchar(45) NOT NULL,
                         `login` varchar(45) NOT NULL,
                         `password` varchar(45) NOT NULL,
                         `deleted` tinyint NOT NULL DEFAULT '0',
                         PRIMARY KEY (`user_id`)
) ;
CREATE TABLE `users_roles` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `user_id` bigint NOT NULL,
                               `role_id` bigint NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `user_id_idx` (`user_id`),
                               KEY `role_id_idx` (`role_id`),
                               CONSTRAINT `role_id_fq` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
                               CONSTRAINT `user_id_fq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ;
CREATE TABLE `orders` (
                          `order_id` bigint NOT NULL AUTO_INCREMENT,
                          `user_id` bigint NOT NULL,
                          `deleted` tinyint NOT NULL DEFAULT '0',
                          PRIMARY KEY (`order_id`),
                          KEY `user_fq_idx` (`user_id`),
                          CONSTRAINT `user_order_fq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ;
CREATE TABLE `orders_products` (
                                   `orders_products_id` bigint NOT NULL AUTO_INCREMENT,
                                   `order_id` bigint NOT NULL,
                                   `product_id` bigint NOT NULL,
                                   PRIMARY KEY (`orders_products_id`),
                                   KEY `order_prod_fq_idx` (`order_id`),
                                   KEY `prod_order_fq_idx` (`product_id`),
                                   CONSTRAINT `order_prod_fq` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
                                   CONSTRAINT `prod_order_fq` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ;
CREATE TABLE `shopping_cart` (
                                 `cart_id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL,
                                 `deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`cart_id`),
                                 UNIQUE KEY `user_id_UNIQUE` (`user_id`),
                                 CONSTRAINT `user_FQ` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ;
CREATE TABLE `shopping_cart_product` (
                                         `shopping_cart_product_id` bigint NOT NULL AUTO_INCREMENT,
                                         `cart_id` bigint NOT NULL,
                                         `product_id` bigint NOT NULL,
                                         PRIMARY KEY (`shopping_cart_product_id`),
                                         KEY `rpoduct_fq_idx` (`product_id`),
                                         KEY `shop_cart_fq` (`cart_id`),
                                         CONSTRAINT `product_fq` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                                         CONSTRAINT `shop_cart_fq` FOREIGN KEY (`cart_id`) REFERENCES `shopping_cart` (`cart_id`)
) ;
INSERT INTO `roles` (`role_name`) VALUES ('ADMIN');
INSERT INTO `roles` (`role_name`) VALUES ('USER');
INSERT INTO `users` (`user_name`, `login`, `password`) VALUES ('Admin', 'admin', '1');
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES ('1', '1');

CREATE DATABASE IF NOT EXISTS `test`;

CREATE TABLE IF NOT EXISTS `test`.`user` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
    );

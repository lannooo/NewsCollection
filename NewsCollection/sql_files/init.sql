DROP DATABASE IF EXISTS news;

CREATE DATABASE news DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE news;

CREATE TABLE `news_ifeng` (
  `url` VARCHAR(120) PRIMARY KEY,
  `source` VARCHAR(30),
  `title` VARCHAR(100) NOT NULL,
  `time` VARCHAR(30),
  `content` TEXT NOT NULL,
  `types` VARCHAR(20)
)ENGINE=innodb DEFAULT CHARSET=utf8;


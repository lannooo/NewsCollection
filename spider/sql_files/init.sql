DROP DATABASE IF EXISTS news;

CREATE DATABASE news DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE news;

ALTER TABLE news
modify column news_content text;

ALTER TABLE raw_news
modify column content text;


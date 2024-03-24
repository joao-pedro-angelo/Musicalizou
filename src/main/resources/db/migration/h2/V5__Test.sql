-- V5__Create_Artists_Table.sql

CREATE TABLE IF NOT EXISTS artists (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    r__year varchar(255) not null,
    country varchar(255) not null,
    bio text
);

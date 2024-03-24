-- V1__Create_Users_Table.sql

CREATE TABLE IF NOT EXISTS users (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    email varchar(255) not null UNIQUE,
    password varchar(500) not null
);

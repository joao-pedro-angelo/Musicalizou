-- V1__Create_Users_Table.sql

CREATE TABLE IF NOT EXISTS users (
    id bigint not null auto_increment,
    email varchar(255) not null unique,
    password varchar(500) not null,

    primary key(id)
);

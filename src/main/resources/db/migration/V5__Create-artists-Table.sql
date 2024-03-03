-- V4__Create_Artists_Table.sql

CREATE TABLE IF NOT EXISTS artists (
        id bigint not null auto_increment,
        name varchar(255) not null,
        year varchar(255) not null,
        country varchar(255) not null,
        bio text,

        primary key(id)
);

-- V2__Create_Musics_Table.sql

CREATE TABLE IF NOT EXISTS musics (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name_music varchar(255) not null,
    music_gen varchar(255) not null
);

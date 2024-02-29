-- V3__Create_Musics_Table.sql

CREATE TABLE IF NOT EXISTS musics (
    id bigint not null auto_increment,
    name_music varchar(255) not null,
    music_gen varchar(255) not null,

    primary key(id)
);

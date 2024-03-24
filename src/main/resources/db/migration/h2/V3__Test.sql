-- V3__Create_Reviews_Table.sql

CREATE TABLE IF NOT EXISTS reviews (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    comment varchar(500) not null,
    music_id bigint not null,

    FOREIGN KEY (music_id) REFERENCES musics(id) ON DELETE CASCADE
);

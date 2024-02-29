-- V2__Create_Reviews_Table.sql

CREATE TABLE IF NOT EXISTS reviews (
        id bigint not null auto_increment,
        comment varchar(500) not null,
        music_id bigint not null,

        primary key(id),
        foreign key (music_id) references musics(id) on delete cascade
);

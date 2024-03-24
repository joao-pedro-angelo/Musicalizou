-- V6__Add_Artist_Column_To_Musics_Table.sql

ALTER TABLE musics
    ADD COLUMN artist_id bigint,
    ADD CONSTRAINT fk_artist_id
        FOREIGN KEY (artist_id)
            REFERENCES artists(id);

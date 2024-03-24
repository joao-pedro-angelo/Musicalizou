-- V6__Add_Artist_Column_To_Musics_Table.sql

-- Primeiro, adicionamos a coluna artist_id à tabela musics
ALTER TABLE musics
    ADD COLUMN artist_id bigint;

-- Em seguida, adicionamos a restrição de chave estrangeira (foreign key constraint)
-- Aqui, criamos a restrição de chave estrangeira sem nome. No H2, o nome da restrição é opcional.
-- A chave estrangeira é vinculada à coluna artist_id da tabela musics
-- e referencia a coluna id da tabela artists
ALTER TABLE musics
    ADD FOREIGN KEY (artist_id)
        REFERENCES artists(id);

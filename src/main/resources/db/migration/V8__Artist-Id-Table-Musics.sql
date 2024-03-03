-- Atualiza o ID do artista para cada música
UPDATE musics SET artist_id =
                      CASE
                          WHEN name_music = 'Teus Sinais' THEN 1
                          WHEN name_music = 'Morreu de Ana Castela' THEN 2
                          WHEN name_music = 'Despacito' THEN 3
                          WHEN name_music = 'Let It Be' THEN 4
                          WHEN name_music = 'Shape Of You' THEN 5
                          WHEN name_music = 'Canudinho' THEN 6
                          END;
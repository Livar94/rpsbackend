
**

Ny
OBS: Du kan ändra TEXT till VARCHAR(255) om TEXT inte funkar.


CREATE TABLE player_entity (
player_id UUID PRIMARY KEY,
player_name TEXT
);

CREATE TABLE game_entity (
game_id UUID PRIMARY KEY,
player_one UUID,
player_two UUID,
player_one_move TEXT,
player_two_move TEXT,
status TEXT,
last_updated TIMESTAMP,
player_one_wins int NOT NULL DEFAULT 0,
player_two_wins int NOT NULL DEFAULT 0,
FOREIGN KEY (player_one) REFERENCES player_entity(player_id)
ON UPDATE CASCADE
ON DELETE CASCADE,
FOREIGN KEY (player_two) REFERENCES player_entity(player_id)
ON UPDATE CASCADE
ON DELETE CASCADE
);


CREATE TABLE user_entity (
id SERIAL PRIMARY KEY,
username TEXT NOT NULL,
password TEXT NOT NULL
);











Gammal: 
CREATE TABLE game_entity (
game_id UUID PRIMARY KEY,
player_one_name UUID,
player_two_name UUID,
player_one_move VARCHAR(255),
player_two_move VARCHAR(255),
status VARCHAR(255),
player_one_wins int,
player_two_wins int
);

CREATE TABLE player_entity (
player_id UUID PRIMARY KEY,
player_name VARCHAR(255),
player_one_game UUID,
player_two_game UUID
);

Sista två kolumnerna ovan raderas på grund av att player startar först innan gameentityn.
Använd följande för uppdatering av tabellen:

ALTER TABLE player_entity
ADD COLUMN player_one_game UUID,
ADD COLUMN player_two_game UUID;

ALTER TABLE game_entity
ADD COLUMN last_updated TIMESTAMP;


ALTER TABLE game_entity
ADD FOREIGN KEY (player_one_name) REFERENCES player_entity(player_id)
ON UPDATE CASCADE
ON DELETE CASCADE;

ALTER TABLE game_entity
ADD FOREIGN KEY (player_two_name) REFERENCES player_entity(player_id)
ON UPDATE CASCADE
ON DELETE CASCADE;

CREATE TABLE user_entity (
id SERIAL PRIMARY KEY,
username VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL
);

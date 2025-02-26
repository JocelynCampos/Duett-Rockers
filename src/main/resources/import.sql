USE pipergamesdb;
 
-- Lägg till spel
INSERT INTO Games (name, player_count) VALUES ('League of Legends', 10);
INSERT INTO Games (name, player_count) VALUES ('Counter-Strike: Global Offensive', 10);
INSERT INTO Games (name, player_count) VALUES ('Valorant', 10);
INSERT INTO Games (name, player_count) VALUES ('Dota 2', 10);
INSERT INTO Games (name, player_count) VALUES ('Overwatch', 12);

-- Lägg till personer
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Alice', 'Johnson', 'ShadowQueen', 'Street 123', '12345', 'Stockholm', 'Sweden', 'alice@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Bob', 'Smith', 'SniperBob', 'Street 456', '54321', 'Göteborg', 'Sweden', 'bob@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Charlie', 'Brown', 'TankMaster', 'Main Road 789', '11223', 'Malmö', 'Sweden', 'charlie@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Diana', 'Ross', 'SupporterD', 'Side Lane 12', '33445', 'Uppsala', 'Sweden', 'diana@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Eve', 'Adams', 'HealerEve', 'Park Ave 101', '55667', 'Lund', 'Sweden', 'eve@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Fred', 'Wilson', 'SilentFred', 'Baker Street 221B', '11111', 'London', 'UK', 'fred@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Grace', 'Hopper', 'CodeQueen', 'Innovation St 42', '22222', 'Berlin', 'Germany', 'grace@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Henry', 'Carter', 'HeadshotHenry', 'Elm Street 5', '33333', 'Oslo', 'Norway', 'henry@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Isabelle', 'Thomas', 'IsabelleTheBrave', 'King St 12', '44444', 'Copenhagen', 'Denmark', 'isabelle@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('James', 'Bond', 'Agent007', 'Secret HQ', '55555', 'London', 'UK', 'james@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Kara', 'Danvers', 'SuperKara', 'Daily News Rd 8', '66666', 'New York', 'USA', 'kara@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Leo', 'Messi', 'DribbleKing', 'Victory Lane 10', '77777', 'Barcelona', 'Spain', 'leo@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Maria', 'Garcia', 'QuickMaria', 'Ocean Blvd 7', '88888', 'Lisbon', 'Portugal', 'maria@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Nathan', 'Drake', 'TreasureHunter', 'Adventure Ave 9', '99999', 'Sydney', 'Australia', 'nathan@example.com', 1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Olivia', 'Brown', 'SniperQueen', 'Hunter Rd 3', '12121', 'Edinburgh', 'Scotland', 'olivia@example.com', 1);

-- Lägg till lag
INSERT INTO teams (name, game_id) VALUES ('LoL Legends', 1);
INSERT INTO teams (name, game_id) VALUES  ('CSGO Snipers', 2);
INSERT INTO teams (name, game_id) VALUES  ('Valorant Heroes', 3);
INSERT INTO teams (name, game_id) VALUES  ('Dota Warriors', 4);
INSERT INTO teams (name, game_id) VALUES  ('Overwatch Masters', 5);
INSERT INTO teams (name, game_id) VALUES  ('Ninjas In Pajamas', 2);
INSERT INTO teams (name, game_id) VALUES  ('Complexity Gaming', 2);
INSERT INTO teams (name, game_id) VALUES  ('NaVi', 2);
INSERT INTO teams (name, game_id) VALUES  ('Faze', 2);
INSERT INTO teams (name, game_id) VALUES  ('Team Liquid', 2);

-- Koppla spelare till spel och lag
INSERT INTO players (person_id, team_id) VALUES (1, 1);
INSERT INTO players (person_id, team_id) VALUES (2, 2);
INSERT INTO players (person_id, team_id) VALUES (3, 3);
INSERT INTO players (person_id, team_id) VALUES (4, 4);
INSERT INTO players (person_id, team_id) VALUES (6, 1);
INSERT INTO players (person_id, team_id) VALUES (7, 2);
INSERT INTO players (person_id, team_id) VALUES (8, 3);
INSERT INTO players (person_id, team_id) VALUES (9, 4);
INSERT INTO players (person_id, team_id) VALUES (12, 6);
INSERT INTO players (person_id, team_id) VALUES (13, 7);
INSERT INTO players (person_id, team_id) VALUES (14, 8);
INSERT INTO players (person_id, team_id) VALUES (15, 9);


-- Koppla personal till Person-tabellen
INSERT INTO Staff (person_id) VALUES (5);
INSERT INTO Staff (person_id) VALUES (10);
INSERT INTO Staff (person_id) VALUES (11);

-- Lägg till individuella matcher
INSERT INTO PlayerMatches (game_id, player1_id, player2_id, match_date, completed, winner_id) VALUES (1, 1, 7, '2024-02-01 14:00:00', TRUE, 1);
INSERT INTO PlayerMatches (game_id, player1_id, player2_id, match_date, completed, winner_id) VALUES (2, 2, 9, '2024-02-02 16:00:00', TRUE, 9);
INSERT INTO PlayerMatches (game_id, player1_id, player2_id, match_date, completed, winner_id) VALUES (3, 3, 12, '2024-02-03 18:00:00', FALSE, NULL);
INSERT INTO PlayerMatches (game_id, player1_id, player2_id, match_date, completed, winner_id) VALUES (4, 4, 6, '2024-02-04 20:00:00', TRUE, 6);
INSERT INTO PlayerMatches (game_id, player1_id, player2_id, match_date, completed, winner_id) VALUES (5, 7, 8, '2024-02-05 14:00:00', TRUE, 7);

-- Lägg till lagmatcher
INSERT INTO teammatches (game_id, team1_id, team2_id, matchDate, completed, winner_id) VALUES (1, 1, 6, '2024-03-01 15:00:00', TRUE, 1);
INSERT INTO teammatches (game_id, team1_id, team2_id, matchDate, completed, winner_id) VALUES (2, 2, 7, '2024-03-02 16:00:00', FALSE, NULL);
INSERT INTO teammatches (game_id, team1_id, team2_id, matchDate, completed, winner_id) VALUES (3, 3, 8, '2024-03-03 17:00:00', TRUE, 8);
INSERT INTO teammatches (game_id, team1_id, team2_id, matchDate, completed, winner_id) VALUES (4, 4, 9, '2024-03-04 18:00:00', TRUE, 4);
INSERT INTO teammatches (game_id, team1_id, team2_id, matchDate, completed, winner_id) VALUES (5, 5, 10, '2024-03-05 19:00:00', TRUE, 5);

INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Kamran','Akbari','K-man','Glaciärvägen 15','80621','Malung','Sverige','akbarikamran4@gmail.com',0);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Erik','Edman','Rikki','Sicksackvägen 13','40232','Gävle','Sverige','erik.edman1996@gmail.com',0);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Hugo','Ransvi','Huggster','Blåbärsvägen 2','80212','Norrköping','Sverige','ho.ransvi@gmail.com',0);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Jocelyn','Campos','Jocy','Köpmangatan 42','60452','Göteborg','Sverige','aracely.campos@hotmail.com',0);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Michael','Granbäck','Micke','Staketgatan 44','30150','Haparanda','Sverige','michael.granbäck@edugrade.se',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Jessica','Niord','Jessie','Kungsgatan 40','86392','Uddevalla','Sverige','jessica.niord@edugrade.se',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('James','Gosling','Dr.Java','Lönngatan 7','08421','Stockholm','Sverige', 'gosling@java.com',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Michael','Widenius','Monty','Mariavägen 10','54065','Helsingfors','Finland','mymariasql@sql.se',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Allan', 'Larsson','Pallan','Medgrundarvägen 3','40213','Uppsala','Sverige','medgrundare@sql.se',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('David', 'Axmark','Davve','Axmarvägen 21','10212','Uppsala','Sverige','also_medgrundare@sql.se',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Linus', 'Torvalds','Linux','Saunavägen 65','33012','Helsingfors','Finland','penguin@tux.fin',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Bjarne','Stroustrup','Father of C++','Danskagatan 1','90145','Köpenhamn','Danmark','cplusplus@sharp.se',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Guido','Van Rossum','BDFL','Tulpanvägen 23','40341','Amsterdam','Nederländerna','tulips@python.nl',0);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Arvid','Nordquist','Bönan','Bryggargatan','70621','Solna','Sverige','arabia.nordquist@mörkrost.se',1);
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES ('Victor','Engwall','Gevalia','Bönagatan 57','80310','Gävle','Sverige','halmbocken@mellanrost.se',1);

INSERT INTO Staff(person_id) VALUES (16);
INSERT INTO Staff(person_id) VALUES (17);
INSERT INTO Staff(person_id) VALUES (18);
INSERT INTO Staff(person_id) VALUES (19);
INSERT INTO Staff(person_id) VALUES (28);

INSERT INTO players(person_id, team_id) VALUES (20, 6);
INSERT INTO players(person_id, team_id) VALUES (21, 10);
INSERT INTO players(person_id, team_id) VALUES (22, 8);
INSERT INTO players(person_id, team_id) VALUES (23, 9);
INSERT INTO players(person_id, team_id) VALUES (24, 2);
INSERT INTO players(person_id, team_id) VALUES (25, 7);
INSERT INTO players(person_id, team_id) VALUES (26, 5);
INSERT INTO players(person_id, team_id) VALUES (27, 3);
INSERT INTO players(person_id, team_id) VALUES (29, 3);
INSERT INTO players(person_id, team_id) VALUES (30, 1);

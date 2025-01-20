USE pipergamesdb;
 
-- Lägg till spel
INSERT INTO Games (name, player_count) VALUES
('League of Legends', 10),
('Counter-Strike: Global Offensive', 10),
('Valorant', 10),
('Dota 2', 10),
('Overwatch', 12);
 
-- Lägg till personer
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role) VALUES
('Alice', 'Johnson', 'ShadowQueen', 'Street 123', '12345', 'Stockholm', 'Sweden', 'alice@example.com', 'PLAYER'),
('Bob', 'Smith', 'SniperBob', 'Street 456', '54321', 'Göteborg', 'Sweden', 'bob@example.com', 'PLAYER'),
('Charlie', 'Brown', 'TankMaster', 'Main Road 789', '11223', 'Malmö', 'Sweden', 'charlie@example.com', 'PLAYER'),
('Diana', 'Ross', 'SupporterD', 'Side Lane 12', '33445', 'Uppsala', 'Sweden', 'diana@example.com', 'PLAYER'),
('Eve', 'Adams', 'HealerEve', 'Park Ave 101', '55667', 'Lund', 'Sweden', 'eve@example.com', 'STAFF'),
('Fred', 'Wilson', 'SilentFred', 'Baker Street 221B', '11111', 'London', 'UK', 'fred@example.com', 'PLAYER'),
('Grace', 'Hopper', 'CodeQueen', 'Innovation St 42', '22222', 'Berlin', 'Germany', 'grace@example.com', 'PLAYER'),
('Henry', 'Carter', 'HeadshotHenry', 'Elm Street 5', '33333', 'Oslo', 'Norway', 'henry@example.com', 'PLAYER'),
('Isabelle', 'Thomas', 'IsabelleTheBrave', 'King St 12', '44444', 'Copenhagen', 'Denmark', 'isabelle@example.com', 'PLAYER'),
('James', 'Bond', 'Agent007', 'Secret HQ', '55555', 'London', 'UK', 'james@example.com', 'STAFF'),
('Kara', 'Danvers', 'SuperKara', 'Daily News Rd 8', '66666', 'New York', 'USA', 'kara@example.com', 'STAFF'),
('Leo', 'Messi', 'DribbleKing', 'Victory Lane 10', '77777', 'Barcelona', 'Spain', 'leo@example.com', 'PLAYER'),
('Maria', 'Garcia', 'QuickMaria', 'Ocean Blvd 7', '88888', 'Lisbon', 'Portugal', 'maria@example.com', 'PLAYER'),
('Nathan', 'Drake', 'TreasureHunter', 'Adventure Ave 9', '99999', 'Sydney', 'Australia', 'nathan@example.com', 'PLAYER'),
('Olivia', 'Brown', 'SniperQueen', 'Hunter Rd 3', '12121', 'Edinburgh', 'Scotland', 'olivia@example.com', 'PLAYER');
 
-- Lägg till lag
INSERT INTO Teams (name, game_id) VALUES
('LoL Legends', 1),
('CSGO Snipers', 2),
('Valorant Heroes', 3),
('Dota Warriors', 4),
('Overwatch Masters', 5),
('Ninjas In Pajamas', 2),
('Complexity Gaming', 2),
('NaVi', 2),
('Faze', 2),
('Team Liquid', 2);
 
-- Koppla spelare till spel och lag
INSERT INTO Players (person_id, team_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(6, 1),
(7, 2),
(8, 3),
(9, 4),
(12, 6),
(13, 7),
(14, 8),
(15, 9);
 
 
-- Koppla personal till Person-tabellen
INSERT INTO Staff (person_id) VALUES
(5),
(10),
(11);
 
-- Lägg till individuella matcher
INSERT INTO PlayerMatches (game_id, player1_id, player2_id, match_date, completed, winner_id) VALUES
(1, 1, 7, '2024-02-01 14:00:00', TRUE, 1),
(2, 2, 9, '2024-02-02 16:00:00', TRUE, 9),
(3, 3, 12, '2024-02-03 18:00:00', FALSE, NULL),
(4, 4, 6, '2024-02-04 20:00:00', TRUE, 6),
(5, 7, 8, '2024-02-05 14:00:00', TRUE, 7);
 
-- Lägg till lagmatcher
INSERT INTO TeamMatches (game_id, team1_id, team2_id, match_date, completed, winner_id) VALUES
(1, 1, 6, '2024-03-01 15:00:00', TRUE, 1),
(2, 2, 7, '2024-03-02 16:00:00', FALSE, NULL),
(3, 3, 8, '2024-03-03 17:00:00', TRUE, 8),
(4, 4, 9, '2024-03-04 18:00:00', TRUE, 4),
(5, 5, 10, '2024-03-05 19:00:00', TRUE, 5);
 
INSERT INTO Persons (first_name, last_name, nickname, street, postal_code, city, country, email, role)
VALUES
('Kamran','Akbari','K-man','Glaciärvägen 15','80621','Malung','Sverige','akbarikamran4@gmail.com','Staff'),
('Erik','Edman','Rikki','Sicksackvägen 13','40232','Gävle','Sverige','erik.edman1996@gmail.com','Staff'),
('Hugo','Ransvi','Huggster','Blåbärsvägen 2','80212','Norrköping','Sverige','ho.ransvi@gmail.com','Staff'),
('Jocelyn','Campos','Jocy','Köpmangatan 42','60452','Göteborg','Sverige','aracely.campos@hotmail.com','Staff'),
('Michael','Granbäck','Micke','Staketgatan 44','30150','Haparanda','Sverige','michael.granbäck@edugrade.se','Player'),
('Jessica','Niord','Jessie','Kungsgatan 40','86392','Uddevalla','Sverige','jessica.niord@edugrade.se','Player'),
('James','Gosling','Dr.Java','Lönngatan 7','08421','Stockholm','Sverige', 'gosling@java.com','Player'),
('Michael','Widenius','Monty','Mariavägen 10','54065','Helsingfors','Finland','mymariasql@sql.se','Player'),
('Allan', 'Larsson','Pallan','Medgrundarvägen 3','40213','Uppsala','Sverige','medgrundare@sql.se','Player'),
('David', 'Axmark','Davve','Axmarvägen 21','10212','Uppsala','Sverige','also_medgrundare@sql.se','Player'),
('Linus', 'Torvalds','Linux','Saunavägen 65','33012','Helsingfors','Finland','penguin@tux.fin','Player'),
('Bjarne','Stroustrup','Father of C++','Danskagatan 1','90145','Köpenhamn','Danmark','cplusplus@sharp.se','Player'),
('Guido','Van Rossum','BDFL','Tulpanvägen 23','40341','Amsterdam','Nederländerna','tulips@python.nl','Staff'),
('Arvid','Nordquist','Bönan','Bryggargatan','70621','Solna','Sverige','arabia.nordquist@mörkrost.se','Player'),
('Victor','Engwall','Gevalia','Bönagatan 57','80310','Gävle','Sverige','halmbocken@mellanrost.se','Player');
 
INSERT INTO Staff(person_id) VALUES
(16),
(17),
(18),
(19),
(28);
 
INSERT INTO Players(person_id, team_id) VALUES
(20, 6),
(21, 10),
(22, 8),
(23, 9),
(24, 2),
(25, 7),
(26, 5),
(27, 3),
(29, 3),
(30, 1);
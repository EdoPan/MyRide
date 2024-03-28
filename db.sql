-- Queries SQL
CREATE TABLE IF NOT EXISTS  users (username text not null primary key, password text, isMaintainer boolean)
CREATE TABLE IF NOT EXISTS rides (id INTEGER PRIMARY KEY autoincrement, startTime text, endTime text, bikeID INTEGER, username text)
CREATE TABLE IF NOT EXISTS stations (id INTEGER PRIMARY KEY autoincrement, address text)
CREATE TABLE IF NOT EXISTS bikes (id INTEGER PRIMARY KEY autoincrement, type text, price double, stationID INTEGER)

INSERT INTO users(username, password, isMaintainer) VALUES ('maintainer', 'maintainer', true);

INSERT INTO stations(address) VALUES ('Via Diotisalvi 2');
INSERT INTO stations(address) VALUES ('Largo Bruno Pontecorvo 3');
INSERT INTO stations(address) VALUES ('Corso Italia 60');
INSERT INTO stations(address) VALUES ('Via di Gello 138');
INSERT INTO stations(address) VALUES ('Piazza Martiri della Libertà 33');
INSERT INTO stations(address) VALUES ('Via S. Marco 15');

INSERT INTO bikes(type, price, stationID) VALUES ('city', 0.25, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('city', 0.25, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('city', 0.25, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('city', 0.25, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('mountain', 0.50, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('mountain', 0.50, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('mountain', 0.50, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('mountain', 0.50, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('road', 0.75, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('road', 0.75, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('road', 0.75, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('road', 0.75, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('tandem', 1.00, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('tandem', 1.00, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('tandem', 1.00, 1);
INSERT INTO bikes(type, price, stationID) VALUES ('tandem', 1.00, 1);
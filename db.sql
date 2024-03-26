-- sqlite3 myRide.db
-- sqlite> .read db.sql

-- Queries SQL

CREATE TABLE users (
    username varchar(255) PRIMARY KEY,
    password varchar(255),
    isMaintainer boolean
);

CREATE TABLE bikes (
    id INTEGER PRIMARY KEY,
    type varchar(255),
  	price double,
  	stationID int
);

CREATE TABLE rides (
    id INTEGER PRIMARY KEY,
    startTime TEXT,
  	endTime TEXT,
    bikeID int,
  	username varchar(255)
);

CREATE TABLE stations (
    id INTEGER PRIMARY KEY,
    address varchar(255)
);

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
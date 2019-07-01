-- CREATE MASTER TABLES
CREATE TABLE RoomType (
roomCode VARCHAR(3) PRIMARY KEY,
roomName VARCHAR(50) UNIQUE NOT NULL,
adults INTEGER NOT NULL,
juniors INTEGER NOT NULL,
babies INTEGER NOT NULL
);

CREATE TABLE Rooms (
id SERIAL PRIMARY KEY,
roomCode VARCHAR(3) NOT NULL,
FOREIGN KEY (roomCode) REFERENCES RoomType (roomCode)
);

-- This could also be a transaction table as the prices may vary with the season and occupancy.
-- Keeping it simple for this exercise with fixed prices.
CREATE TABLE RoomPrice (
roomCode VARCHAR(3) NOT NULL,
price DOUBLE PRECISION NOT NULL,
FOREIGN KEY (roomCode) REFERENCES RoomType (roomCode)
);

-- CREATE TRANSACTION TABLES
CREATE TABLE Customer (
id SERIAL PRIMARY KEY,
customerName VARCHAR NOT NULL,
customerMail VARCHAR NOT NULL
);

CREATE TABLE Reservation (
id SERIAL PRIMARY KEY,
customerId INTEGER NOT NULL,
roomId INTEGER NOT NULL,
startDate DATE NOT NULL,
endDate DATE NOT NULL,
adults INTEGER,
juniors INTEGER,
babies INTEGER,
bookingTime TIMESTAMP NOT NULL,
reference VARCHAR(6) NOT NULL,
FOREIGN KEY (customerId) REFERENCES Customer (id),
FOREIGN KEY (roomId) REFERENCES Rooms (id)
);

-- INSERT MASTER DATA
-- Add room types
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('DST','Double Standard', 2, 0, 0);
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('DSP','Double Superior', 2, 0, 1);
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('FMS','Family Small', 2, 1, 1);
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('FML','Family Large', 2, 2, 1);
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('JST','Junior Suite', 2, 0, 0);
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('APT','Apartment', 4, 0, 0);

-- Add rooms to hotel
-- 6 Double Standard
INSERT INTO Rooms (roomCode) VALUES ('DST');
INSERT INTO Rooms (roomCode) VALUES ('DST');
INSERT INTO Rooms (roomCode) VALUES ('DST');
INSERT INTO Rooms (roomCode) VALUES ('DST');
INSERT INTO Rooms (roomCode) VALUES ('DST');
INSERT INTO Rooms (roomCode) VALUES ('DST');
-- 4 Double Superior
INSERT INTO Rooms (roomCode) VALUES ('DSP');
INSERT INTO Rooms (roomCode) VALUES ('DSP');
INSERT INTO Rooms (roomCode) VALUES ('DSP');
INSERT INTO Rooms (roomCode) VALUES ('DSP');
-- 2 Family Small
INSERT INTO Rooms (roomCode) VALUES ('FMS');
INSERT INTO Rooms (roomCode) VALUES ('FMS');
INSERT INTO Rooms (roomCode) VALUES ('FMS');
-- 2 Family Large
INSERT INTO Rooms (roomCode) VALUES ('FML');
INSERT INTO Rooms (roomCode) VALUES ('FML');
-- 2 Junior Suites
INSERT INTO Rooms (roomCode) VALUES ('JST');
INSERT INTO Rooms (roomCode) VALUES ('JST');
INSERT INTO Rooms (roomCode) VALUES ('JST');
-- 2 Apartments
INSERT INTO Rooms (roomCode) VALUES ('APT');
INSERT INTO Rooms (roomCode) VALUES ('APT');

-- Add room prices
-- Double Standard
INSERT INTO RoomPrice (roomCode, price) VALUES ('DST', 99.0);
-- Double Superior
INSERT INTO RoomPrice (roomCode, price) VALUES ('DSP', 119.0);
-- Family Small
INSERT INTO RoomPrice (roomCode, price) VALUES ('FMS', 149.0);
-- Family Large
INSERT INTO RoomPrice (roomCode, price) VALUES ('FML', 169.0);
-- Junior Suites
INSERT INTO RoomPrice (roomCode, price) VALUES ('JST', 159.0);
-- Apartments
INSERT INTO RoomPrice (roomCode, price) VALUES ('APT', 199.0);

COMMIT;





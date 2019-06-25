-- CREATE MASTER TABLES
CREATE TABLE RoomType (
roomCode VARCHAR(3) PRIMARY KEY,
roomName VARCHAR(50) UNIQUE NOT NULL,
adults INTEGER NOT NULL,
juniors INTEGER NOT NULL,
babies INTEGER NOT NULL
)

CREATE TABLE Rooms (
id SERIAL PRIMARY KEY,
roomCode VARCHAR(3) NOT NULL,
FOREIGN KEY (roomCode) REFERENCES RoomType (roomCode)
)

-- This could also be a transaction table as the prices may vary with the season and occupancy.
-- Keeping it simple for this exercise with fixed prices.
CREATE TABLE RoomPrice (
roomId INTEGER NOT NULL,
price DOUBLE NOT NULL,
currency VARCHAR(3),
FOREIGN KEY (roomId) REFERENCES Rooms (id)
)

-- CREATE TRANSACTION TABLES
CREATE TABLE Guest (
id SERIAL PRIMARY KEY,
customerName VARCHAR NOT NULL,
customerMail VARCHAR NOT NULL,
)

CREATE TABLE Reservation (
id SERIAL PRIMARY KEY,
guestId INTEGER NOT NULL,
roomId INTEGER NOT NULL,
startDate DATE NOT NULL,
endDate DATE NOT NULL,
bookingTime TIMESTAMP NOT NULL,
FOREIGN KEY (guestId) REFERENCES Guest (id)
FOREIGN KEY (roomId) REFERENCES Rooms (id)
)

-- INSERT MASTER DATA
-- Add room types
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('DST','Double Standard', 2, 0, 0)
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('DSP','Double Superior', 2, 0, 1)
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('FMS','Family Small', 2, 1, 1)
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('FML','Family Large', 2, 2, 1)
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('JST','Junior Suite', 2, 0, 0)
INSERT INTO RoomType (roomCode, roomName, adults, juniors, babies) VALUES ('APT','Apartment', 4, 0, 0)

-- Add rooms to hotel
-- 4 Double Standard
INSERT INTO Rooms (roomCode) VALUES ('DST')
INSERT INTO Rooms (roomCode) VALUES ('DST')
INSERT INTO Rooms (roomCode) VALUES ('DST')
INSERT INTO Rooms (roomCode) VALUES ('DST')
-- 4 Double Superior
INSERT INTO Rooms (roomCode) VALUES ('DSP')
INSERT INTO Rooms (roomCode) VALUES ('DSP')
INSERT INTO Rooms (roomCode) VALUES ('DSP')
INSERT INTO Rooms (roomCode) VALUES ('DSP')
-- 2 Family Small
INSERT INTO Rooms (roomCode) VALUES ('FMS')
INSERT INTO Rooms (roomCode) VALUES ('FMS')
-- 2 Family Large
INSERT INTO Rooms (roomCode) VALUES ('FML')
INSERT INTO Rooms (roomCode) VALUES ('FML')
-- 2 Junior Suites
INSERT INTO Rooms (roomCode) VALUES ('JST')
INSERT INTO Rooms (roomCode) VALUES ('JST')
-- 2 Apartments
INSERT INTO Rooms (roomCode) VALUES ('APT')
INSERT INTO Rooms (roomCode) VALUES ('APT')

-- Add room prices
-- Double Standard
INSERT INTO RoomPrice (roomId, price, currency) VALUES (1, 99.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (2, 99.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (3, 109.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (4, 109.0, 'EUR')
-- Double Superior
INSERT INTO RoomPrice (roomId, price, currency) VALUES (5, 119.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (6, 119.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (7, 139.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (8, 139.0, 'EUR')
-- Family Small
INSERT INTO RoomPrice (roomId, price, currency) VALUES (9, 149.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (10, 149.0, 'EUR')
-- Family Large
INSERT INTO RoomPrice (roomId, price, currency) VALUES (11, 169.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (12, 169.0, 'EUR')
-- Junior Suites
INSERT INTO RoomPrice (roomId, price, currency) VALUES (13, 159.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (14, 159.0, 'EUR')
-- Apartments
INSERT INTO RoomPrice (roomId, price, currency) VALUES (15, 199.0, 'EUR')
INSERT INTO RoomPrice (roomId, price, currency) VALUES (16, 199.0, 'EUR')





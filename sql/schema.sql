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
INSEERT INTO Rooms (roomCode) VALUES ('DST')
INSEERT INTO Rooms (roomCode) VALUES ('DST')
INSEERT INTO Rooms (roomCode) VALUES ('DST')
INSEERT INTO Rooms (roomCode) VALUES ('DST')
-- 4 Double Superior
INSEERT INTO Rooms (roomCode) VALUES ('DSP')
INSEERT INTO Rooms (roomCode) VALUES ('DSP')
INSEERT INTO Rooms (roomCode) VALUES ('DSP')
INSEERT INTO Rooms (roomCode) VALUES ('DSP')
-- 2 Family Small
INSEERT INTO Rooms (roomCode) VALUES ('FMS')
INSEERT INTO Rooms (roomCode) VALUES ('FMS')
-- 2 Family Large
INSEERT INTO Rooms (roomCode) VALUES ('FML')
INSEERT INTO Rooms (roomCode) VALUES ('FML')
-- 2 Junior Suites
INSEERT INTO Rooms (roomCode) VALUES ('JST')
INSEERT INTO Rooms (roomCode) VALUES ('JST')
-- 2 Apartments
INSEERT INTO Rooms (roomCode) VALUES ('APT')
INSEERT INTO Rooms (roomCode) VALUES ('APT')






-- schema-javadb.sql

CREATE TABLE rooms (
  id     INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  floor  INT,
  number INT,
  capacity INT);

CREATE TABLE guests (
  id       INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(50),
  address  VARCHAR(150),
  phone    INT,
  birthDate   VARCHAR(50));

CREATE TABLE registrations (
  id          INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  roomId      INT REFERENCES rooms (id) ON DELETE CASCADE,
  guestId  INT REFERENCES guests(id) ON DELETE CASCADE,
  startDate   varchar(50),
  endDate     varchar(50),
  price       FLOAT);
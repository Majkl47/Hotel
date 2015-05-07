  -- schema-psql.sql
  DROP TABLE IF EXISTS registrations;
  DROP TABLE IF EXISTS guests;
  DROP TABLE IF EXISTS rooms;

 CREATE TABLE rooms (
   id     SERIAL PRIMARY KEY,
   floor   VARCHAR,
   number  VARCHAR,
   capacity VARCHAR);

 CREATE TABLE guests(
   id       SERIAL PRIMARY KEY,
   name VARCHAR,
   address  VARCHAR,
   phone    INT,
   birthDate VARCHAR);

 CREATE TABLE registrations (
   id          SERIAL PRIMARY KEY,
   roomId      INT REFERENCES rooms(id) ON DELETE CASCADE ON UPDATE CASCADE,
   guetsId     INT REFERENCES guests(id) ON DELETE CASCADE ON UPDATE CASCADE,
   startDate   VARCHAR,
   endDate VARCHAR,
   price     FLOAT);
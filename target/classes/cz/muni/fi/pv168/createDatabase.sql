CREATE TABLE "ROOM" (
    "ID" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "FLOOR" INTEGER NOT NULL,
    "NUMBER" INTEGER NOT NULL,
    "CAPACITY" INTEGER NOT NULL    
);

CREATE TABLE "GUEST" (
    "ID" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "NAME" VARCHAR(255),
    "ADDRESS" VARCHAR(255),
    "PHONE" INTEGER NOT NULL,
    "BIRTHDATE" VARCHAR(255)  
);

CREATE TABLE "REGISTRATION" (
    "ID" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "STARTDATE" VARCHAR(255),
    "ENDDATE" VARCHAR(255),
    "PRICE" INTEGER NOT NULL,
    "GUESTID" BIGINT REFERENCES GUEST (ID), 
    "ROOMID" BIGINT REFERENCES ROOM (ID)
);
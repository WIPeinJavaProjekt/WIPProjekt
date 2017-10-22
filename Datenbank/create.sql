create table USERTYPES (
    utid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Type VARCHAR(50),
    TechIsActive bit DEFAULT 1,
    TechIsDeleted bit DEFAULT 0,
    TechIsModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO USERTYPES(type) VALUES('Administrator');
INSERT INTO USERTYPES(type) VALUES('Kunde');
INSERT INTO USERTYPES(type) VALUES('Mitarbeiter');

create table SAFETYQUESTION (
    sqid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    SafetyQuestion VARCHAR(255),   
    TechIsActive bit DEFAULT 1,
    TechIsDeleted bit DEFAULT 0,
    TechIsModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Wie lautet der Vorname ihrer Mutter?');
INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Wie lautet der Name ihres Haustiers?');
INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Wie viele Geschwister haben Sie?');
INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Welches Land möchten Sie auf keinen Fall bereisen?');
INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Welcher Shop ist besser als amazon? ;)');


create table USERDATA (
    udid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255),
    Surname VARCHAR(255),
    Email VARCHAR(255),
    Postal VARCHAR(10),
    Street VARCHAR(255),
    CITY VARCHAR(255),
    Phone VARCHAR(255),
    TechIsActive bit DEFAULT 1,
    TechIsDeleted bit DEFAULT 0,
    TechIsModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP        
    );


create table USERLOGIN (
    ulid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Login VARCHAR(50) UNIQUE,
    Password VARCHAR(50),
    SafetyAnswer VARCHAR(255),
    TechIsActive bit DEFAULT 1,
    TechIsDeleted bit DEFAULT 0,
    TechIsModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP    
);
ALTER TABLE USERLOGIN ADD COLUMN sqid int;
ALTER TABLE USERLOGIN ADD FOREIGN KEY(sqid) REFERENCES SAFETYQUESTION(sqid);
ALTER TABLE USERLOGIN ADD COLUMN utid int;
ALTER TABLE USERLOGIN ADD FOREIGN KEY(utid) REFERENCES USERTYPES(utid);
ALTER TABLE USERLOGIN ADD COLUMN udid int;
ALTER TABLE USERLOGIN ADD FOREIGN KEY(udid) REFERENCES USERDATA(udid);


create table CATEGORIES (
    acid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    categorie VARCHAR(255)
);

INSERT INTO CATEGORIES(categorie) VALUES('Kleidung');
INSERT INTO CATEGORIES(categorie) VALUES('Ski-Equipment');
INSERT INTO CATEGORIES(categorie) VALUES('Outdoor-Ausrüstung');



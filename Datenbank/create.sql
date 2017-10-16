create table USERTYPES {
    'utid' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'Type' VARCHAR(50)
    'TechIsActive' bit DEFAULT 1,
    'TechIsDeleted' bit DEFAULT 0,
    'TechIsCreated' TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    'TechIsModified' TIMESTAMP DEFAULT CURRENT_TIMESTAMP
};

INSERT INTO USERTYPES(type) VALUES('Administrator');
INSERT INTO USERTYPES(type) VALUES('Kunde');
INSERT INTO USERTYPES(type) VALUES('Mitarbeiter');

create table SAFETYQUESTION {
    'sqid' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'SafetyQuestion' VARCHAR(255),   
    'TechIsActive' bit DEFAULT 1,
    'TechIsDeleted' bit DEFAULT 0,
    'TechIsCreated' TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    'TechIsModified' TIMESTAMP DEFAULT CURRENT_TIMESTAMP
};

INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Wie lautet der Vorname ihrer Mutter?');
INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Wie lautet der Name ihres Haustiers?');
INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Wie viele Geschwister haben Sie?');
INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Welches Land möchten Sie auf keinen Fall bereisen?');
INSERT INTO SAFETYQUESTION(SafetyQuestion) VALUES('Welcher Shop ist besser als amazon? ;)');


create table USERLOGIN {
    'ulid' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'Login' VARCHAR(50) UNIQUE,
    'Password' VARCHAR(50),
    'SafetyAnswer' VARCHAR(255),
    FOREIGN KEY('sqid') REFERENCES SAFETYQUESTION(sqid),
    FOREIGN KEY('utid') REFERENCES USERTYPES(utid),
    'TechIsActive' bit DEFAULT 1,
    'TechIsDeleted' bit DEFAULT 0,
    'TechIsCreated' TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    'TechIsModified' TIMESTAMP DEFAULT CURRENT_TIMESTAMP    
};


create table USERDATA {
    'udid' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ''
    'TechIsActive' bit DEFAULT 1,
    'TechIsDeleted' bit DEFAULT 0,
    'TechIsCreated' TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    'TechIsModified' TIMESTAMP DEFAULT CURRENT_TIMESTAMP        
    };


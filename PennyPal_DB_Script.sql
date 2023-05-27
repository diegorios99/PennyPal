DROP DATABASE IF EXISTS pennypal;
CREATE DATABASE pennypal;
USE pennypal;

CREATE TABLE accounts(
	accountID char(10)	NOT NULL,
    balance decimal(32,2) NOT NULL,
    CONSTRAINT accPK
		PRIMARY KEY (accountID),
	CONSTRAINT accUK
		UNIQUE (accountID)
);

CREATE TABLE users(
	first_name	VARCHAR(45)		NOT NULL,
    last_name  	VARCHAR(45)		NOT NULL,
    phone_num	CHAR(10)		NOT NULL,
    user_name	VARCHAR(20)		NOT NULL,
    street		VARCHAR(45)		NOT NULL,
    city		VARCHAR(25)		NOT NULL,
    state		CHAR(3)			NOT NULL,
    zip			CHAR(5)			NOT NULL,
    email		VARCHAR(45)		NOT NULL,
	CONSTRAINT userPK
        PRIMARY KEY (user_name),
	CONSTRAINT usersUK
		UNIQUE (user_name)
);

CREATE TABLE customerAcc(
	accountID	CHAR(10)	NOT NULL,
    user_name	VARCHAR(20)	NOT	NULL,
    
    CONSTRAINT custAccPK
		PRIMARY KEY (accountID, user_name),
	CONSTRAINT custAccFKaccounts
		FOREIGN KEY (accountID)
        REFERENCES accounts (accountID),
	CONSTRAINT custAccFKusers
		FOREIGN KEY (user_name)
        REFERENCES users (user_name)
        
);
SET FOREIGN_KEY_CHECKS = 1;
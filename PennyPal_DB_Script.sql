DROP DATABASE IF EXISTS pennypal;
CREATE DATABASE pennypal;
USE pennypal;

CREATE TABLE accounts(
	accountID CHAR(10)	NOT NULL,
    balance DECIMAL(16,2) NOT NULL,
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
CREATE TABLE budgets(
	user_name CHAR(10)	NOT NULL,
    budget_id CHAR(4)	NOT NULL,
    budget_name VARCHAR(25)	NOT NULL,
    budget_limit DECIMAL(16,2) NOT NULL,
    amount_spent DOUBLE(16,2) NOT NULL,
    CONSTRAINT budgetsPK
		PRIMARY KEY (user_name, budget_id),
	CONSTRAINT budgetsFKusers
		FOREIGN KEY (user_name)
        REFERENCES users (user_name)
);
SET FOREIGN_KEY_CHECKS = 1;
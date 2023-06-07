DROP DATABASE IF EXISTS pennypal;
CREATE DATABASE pennypal;
USE pennypal;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL
);

CREATE TABLE years (
  id INT AUTO_INCREMENT PRIMARY KEY,
  year_number INT NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE months (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  year_id INT NOT NULL,
  user_id INT NOT NULL,
  monthly_expenses INT NOT NULL DEFAULT 0,
  FOREIGN KEY (year_id) REFERENCES years(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE categories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  year_id INT NOT NULL,
  month_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  budget INT NOT NULL,
  spending INT NOT NULL DEFAULT 0,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (year_id) REFERENCES years(id),
  FOREIGN KEY (month_id) REFERENCES months(id)
);

CREATE TABLE expenses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  category_id INT NOT NULL,
  month_id INT NOT NULL,
  year_id INT NOT NULL,
  amount INT NOT NULL,
  date DATE NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (month_id) REFERENCES months(id),
  FOREIGN KEY (year_id) REFERENCES years(id)
);


INSERT INTO users (username, password) VALUES ('Admin', 'Pass');
INSERT INTO users (username, password) VALUES ('Test', 'Pass');
INSERT INTO users (username, password) VALUES ('Test2', 'Pass');
INSERT INTO users (username, password) VALUES ('Test3', 'Pass');

-- Insert user
INSERT INTO users (username, password)
VALUES ('john_doe', 'password123');

-- Insert years
INSERT INTO years (year_number, user_id)
VALUES (2022, 1), (2023, 1);

-- Insert months
INSERT INTO months (name, year_id, user_id)
VALUES ('January', 2, 1), ('February', 2, 1);

-- Insert categories
INSERT INTO categories (user_id, year_id, month_id, name, budget)
VALUES (1, 2, 2, 'Category 1', 500), (1, 2, 2, 'Category 2', 300), (1, 2, 1, 'Category 3', 400);

-- Insert expenses
INSERT INTO expenses (user_id, category_id, month_id, year_id, amount, date)
VALUES (1, 1, 2, 2, 250, '2023-02-01'), (1, 3, 1, 2, 300, '2023-01-01');

-- Update monthly expenses in months table
UPDATE months
SET monthly_expenses = (
    SELECT SUM(amount)
    FROM expenses
    WHERE month_id = months.id
)
WHERE user_id = 1;
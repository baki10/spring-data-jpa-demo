DROP TABLE IF EXISTS students;
CREATE TABLE students (id integer, name varchar(50));

DROP TABLE IF EXISTS customers;
CREATE TABLE customers (id integer, name varchar(50), created_at timestamp);

DROP TABLE IF EXISTS users;
CREATE TABLE users (id integer, name varchar(50), followers integer);
INSERT INTO users VALUES (1, 'John Snow', 10000), (2, 'Night King', 8000), (3, 'Arya Stark', 20000);
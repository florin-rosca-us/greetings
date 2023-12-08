-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE greetings (id INT, name VARCHAR, time TIMESTAMP, PRIMARY KEY (id))
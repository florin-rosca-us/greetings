-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE greeting (id SERIAL, name VARCHAR, time TIMESTAMP, PRIMARY KEY (id));
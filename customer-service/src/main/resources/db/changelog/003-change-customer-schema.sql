--liquibase formatted sql

--changeset author dmp:003
--comment: replace name with username, first_name, last_name

ALTER TABLE customers DROP COLUMN IF EXISTS name;
ALTER TABLE customers ADD COLUMN username VARCHAR(100) NOT NULL UNIQUE;
ALTER TABLE customers ADD COLUMN first_name VARCHAR(100) NOT NULL;
ALTER TABLE customers ADD COLUMN last_name VARCHAR(100) NOT NULL;

--rollback ALTER TABLE customers ADD COLUMN name VARCHAR(255);
--rollback ALTER TABLE customers DROP COLUMN username, DROP COLUMN first_name, DROP COLUMN last_name;
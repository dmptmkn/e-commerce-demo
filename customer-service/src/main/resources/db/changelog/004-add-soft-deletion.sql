--liquibase formatted sql

--changeset author dmp:004
--comment: add soft deletion

ALTER TABLE customers ADD COLUMN deleted BOOLEAN NOT NULL DEFAULT FALSE;
--rollback ALTER TABLE customers DROP COLUMN deleted;
--liquibase formatted sql

--changeset author dmp:005
--comment: adapt outbox table to Debezium Outbox EventRouter schema
DROP TABLE IF EXISTS outbox;

CREATE TABLE outbox (
    id UUID PRIMARY KEY,
    aggregatetype VARCHAR(100) NOT NULL,
    aggregateid VARCHAR(36) NOT NULL,
    type VARCHAR(100) NOT NULL,
    payload TEXT NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    sent BOOLEAN NOT NULL DEFAULT FALSE
);
--rollback DROP TABLE outbox;
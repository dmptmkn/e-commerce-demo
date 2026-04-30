--liquibase formatted sql

--changeset author dmp:002
CREATE TABLE outbox (
    id BIGSERIAL PRIMARY KEY,
    aggregate_id VARCHAR(255) NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    sent BOOLEAN NOT NULL DEFAULT FALSE
);
--rollback DROP TABLE outbox;
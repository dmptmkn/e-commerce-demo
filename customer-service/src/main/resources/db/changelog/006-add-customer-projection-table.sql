--liquibase formatted sql

--changeset dmp:006
--comment: add customer_projection table
CREATE TABLE customer_projection (
    id UUID PRIMARY KEY NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    zipcode VARCHAR(20) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    building VARCHAR(20) NOT NULL,
    apartment VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    loyalty_points INTEGER NOT NULL DEFAULT 0,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
--rollback DROP TABLE customer
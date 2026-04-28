--liquibase formatted sql

--changeset author dmp:001
CREATE TABLE customers (
    customer_id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    address_country VARCHAR(100) NOT NULL,
    address_zipcode VARCHAR(20) NOT NULL,
    address_city VARCHAR(255) NOT NULL,
    address_street VARCHAR(255) NOT NULL,
    address_building VARCHAR(20) NOT NULL,
    address_apartment VARCHAR(20),
    loyalty_points INTEGER NOT NULL DEFAULT 0,
    version INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL
);
--rollback DROP TABLE customers;
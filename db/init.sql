CREATE TABLE IF NOT EXISTS product_type
(
    product_type_id   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(255),
    updated_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product
(
    product_id        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    price             NUMERIC(8, 2),
    quantity_in_stock INTEGER,
    updated_timestamp TIMESTAMP,
    description       VARCHAR(255),
    image_src         VARCHAR(255),
    name              VARCHAR(255),
    product_type_id   BIGINT REFERENCES product_type (product_type_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS review
(
    review_id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    rating            DOUBLE PRECISION,
    description       VARCHAR(255),
    header            VARCHAR(255),
    updated_timestamp TIMESTAMP,
    product_id        BIGINT REFERENCES product (product_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_account
(
    user_id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    password          VARCHAR(255),
    role              VARCHAR(255) CHECK ((role)::text = ANY
                                          ((ARRAY ['ADMIN'::character varying, 'MANAGER'::character varying, 'USER'::character varying])::text[])),
    username          VARCHAR(255),
    updated_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shop_order
(
    order_id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    updated_timestamp TIMESTAMP,
    address           VARCHAR(255),
    order_status      VARCHAR(255) CHECK ((order_status)::text = ANY
                                          ((ARRAY ['PENDING'::character varying, 'COMPLETED'::character varying, 'CANCELED'::character varying])::text[])),
    user_id           BIGINT REFERENCES user_account (user_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS order_record
(
    order_record_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    quantity        INT DEFAULT 1,
    order_id        BIGINT REFERENCES shop_order (order_id) ON DELETE CASCADE,
    product_id      BIGINT REFERENCES product (product_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS support_request
(
    support_request_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    updated_timestamp  TIMESTAMP,
    description        VARCHAR(255),
    theme              VARCHAR(255),
    user_id            BIGINT REFERENCES user_account (user_id) ON DELETE CASCADE
);

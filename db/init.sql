CREATE TABLE IF NOT EXISTS product_type
(
    product_type_id   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(255),
    updated_timestamp TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS product
(
    product_id        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    price             NUMERIC(8, 2),
    quantity_in_stock INTEGER,
    updated_timestamp TIMESTAMP DEFAULT NOW(),
    description       VARCHAR(255),
    image_src         VARCHAR(255),
    name              VARCHAR(255),
    product_type_id   BIGINT REFERENCES product_type (product_type_id) ON DELETE SET NULL
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
    shop_order_id     BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
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
    order_id        BIGINT REFERENCES shop_order (shop_order_id) ON DELETE CASCADE,
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

CREATE TABLE IF NOT EXISTS ai_thread
(
    ai_thread_id      BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    created_timestamp TIMESTAMP,
    user_id           BIGINT REFERENCES user_account (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ai_message
(
    ai_message_id     BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    role              VARCHAR(255),
    message           TEXT,
    created_timestamp TIMESTAMP,
    ai_thread_id      BIGINT REFERENCES ai_thread (ai_thread_id) ON DELETE CASCADE
);

INSERT INTO product_type(name)
VALUES ('Телефон'),
       ('Умные часы'),
       ('Колонки'),
       ('Фитнес-браслет');

INSERT INTO product(price, quantity_in_stock, description, image_src, name, product_type_id)
VALUES (29999.99, 8, 'Технолоическое новшество современной России',
        'D:\Edu\Course3_2\Development-of-client-server-applications\productcrud\images\4e60a5f7-66f0-4767-8c3e-76f47f2cbd95.png',
        'Р-ФОН', 1),
       (9999.99, 8, 'Покажут время даже под водой',
        'D:\Edu\Course3_2\Development-of-client-server-applications\productcrud\images\5e60c5f7-66f0-4767-8c3e-76f47f2cbd95.jpg',
        'Smart Watch X8', 2),
       (5999.99, 3, 'Колонки громкие как песни КИШ',
        'D:\Edu\Course3_2\Development-of-client-server-applications\productcrud\images\6e60c5f7-66f0-4767-8c3e-76f47f2cbd95.jpg',
        'Яндекс.Станция Лайт', 3),
       (9999.99, 4, 'Храни вас Бог',
        'D:\Edu\Course3_2\Development-of-client-server-applications\productcrud\images\7e60c5f7-66f0-4767-8c3e-76f47f2cbd95.jpg',
        'Xiaomi Mi Band 8 RU', 4)

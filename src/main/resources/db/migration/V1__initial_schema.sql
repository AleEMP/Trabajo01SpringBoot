-- V1__Create_initial_tables.sql
-- Este script crea las tablas customers, products, orders y order_items
-- con la estructura y convenciones de nombres solicitadas.

-- Crear la tabla de clientes (customers)
CREATE TABLE customers
(
    id          SERIAL        NOT NULL,
    first_name  VARCHAR(50)   NOT NULL,
    last_name   VARCHAR(50)   NOT NULL,
    email       VARCHAR(100)  NOT NULL UNIQUE,
    is_active   BOOLEAN       NOT NULL DEFAULT TRUE,
    is_verified BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP,
    CONSTRAINT customers_pkey PRIMARY KEY (id)
);

-- Crear la tabla de productos (products)
CREATE TABLE products
(
    id              SERIAL         NOT NULL,
    name            VARCHAR(255)   NOT NULL,
    description     VARCHAR(255),
    price           DECIMAL(10, 2) NOT NULL,
    stock_quantity  INT            NOT NULL,
    is_active       BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

-- Crear la tabla de órdenes (orders)
CREATE TABLE orders
(
    id              SERIAL         NOT NULL,
    order_date      TIMESTAMP      NOT NULL,
    total_amount    DECIMAL(10, 2) NOT NULL,
    status          VARCHAR(50)    NOT NULL,
    created_at      TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP,
    customer_id     INT            NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT fk_orders_customer_id FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Crear la tabla de ítems de órdenes (order_items)
CREATE TABLE order_items
(
    id          SERIAL         NOT NULL,
    quantity    INT            NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    order_id    INT            NOT NULL,
    product_id  INT            NOT NULL,
    CONSTRAINT order_items_pkey PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_items_product_id FOREIGN KEY (product_id) REFERENCES products(id)
);
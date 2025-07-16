
create schema if not EXISTS ORDER_MGMT;

CREATE TABLE order_mgmt.orders (
    order_id VARCHAR(20) PRIMARY KEY,
    custometer_id VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    subtotal NUMERIC(10,2),
    tax NUMERIC(10,2),
    shipping_cost NUMERIC(10,2),
    total NUMERIC(10,2),
    shipping_address TEXT,
    billing_address TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE order_mgmt.order_item (
    order_item_id VARCHAR(20) PRIMARY KEY,
    order_id VARCHAR(20) REFERENCES order_mgmt.orders(order_id) ON DELETE CASCADE,
    product_id VARCHAR(20),
    product_name VARCHAR(100),
    quantity INTEGER,
    unit_price NUMERIC(10,2),
    total_price NUMERIC(10,2)
);



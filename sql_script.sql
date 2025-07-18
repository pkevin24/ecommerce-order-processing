
create schema if not EXISTS ORDER_MGMT;

CREATE TABLE order_mgmt.orders (
    order_id VARCHAR(255) PRIMARY KEY,
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
    order_id VARCHAR(255) REFERENCES order_mgmt.orders(order_id) ON DELETE CASCADE,
    product_id VARCHAR(20),
    product_name VARCHAR(100),
    quantity INTEGER,
    unit_price NUMERIC(10,2),
    total_price NUMERIC(10,2)
);

ALTER TABLE order_mgmt.orders 
ALTER COLUMN status SET DATA TYPE SMALLINT USING status::smallint;


--To be run in inventory db

-- Ensure the schema exists
CREATE SCHEMA IF NOT EXISTS inventory_mgmt;

-- Create the inventory table
CREATE TABLE inventory_mgmt.inventory (
    id BIGSERIAL PRIMARY KEY,
    product_id VARCHAR(50) NOT NULL UNIQUE,
    product_name VARCHAR(255) NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL,
    available_quantity INTEGER NOT NULL DEFAULT 0,
    reserved_quantity INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Insert sample inventory items (dummy data)
INSERT INTO inventory_mgmt.inventory 
    (product_id, product_name, unit_price, available_quantity, reserved_quantity, created_at, updated_at)
VALUES 
    ('P1001', 'Wireless Mouse', 499.00, 150, 0, NOW(), NOW()),
    ('P1002', 'Mechanical Keyboard', 2299.50, 100, 0, NOW(), NOW()),
    ('P1003', '27-inch Monitor', 12499.99, 75, 0, NOW(), NOW()),
    ('P1004', 'USB-C Docking Station', 3499.00, 60, 0, NOW(), NOW()),
    ('P1005', 'External SSD 1TB', 7499.00, 80, 0, NOW(), NOW()),
    ('P1006', 'Laptop Stand', 1199.99, 200, 0, NOW(), NOW()),
    ('P1007', 'Noise Cancelling Headphones', 8999.00, 50, 0, NOW(), NOW()),
    ('P1008', 'Webcam Full HD', 1499.00, 90, 0, NOW(), NOW());




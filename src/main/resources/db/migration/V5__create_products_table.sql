CREATE TABLE IF NOT EXISTS products (
    id SERIAL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(6, 2) NOT NULL,
    product_category_id INT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (product_category_id) REFERENCES product_categories (id)
);
CREATE TABLE IF NOT EXISTS order_products (
    product_id INT NOT NULL,
    order_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (order_id)  REFERENCES "orders" (id)
);


CREATE TABLE IF NOT EXISTS "orders" (
    id SERIAL,
    user_id INT NOT NULL,
    restaurant_table_id INT NOT NULL,
    create_date TIMESTAMP NOT NULL,
    order_status_id INT NOT NULL,
    FOREIGN KEY (order_status_id) REFERENCES order_statuses (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_tables (id),
    PRIMARY KEY (id)
);
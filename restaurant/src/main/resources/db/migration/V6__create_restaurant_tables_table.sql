CREATE TABLE IF NOT EXISTS restaurant_tables (
    id SERIAL,
    restaurant_table_number INT NOT NULL UNIQUE CHECK (restaurant_table_number > 0),
    PRIMARY KEY (id)
);

INSERT INTO restaurant_tables (restaurant_table_number)
    VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10);



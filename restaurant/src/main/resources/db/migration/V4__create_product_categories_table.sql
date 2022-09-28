CREATE TABLE IF NOT EXISTS product_categories (
    id SERIAL,
    category_name VARCHAR (100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
)
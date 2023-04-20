CREATE TYPE status_type AS ENUM ('ACTIVE', 'COMPLETE');

CREATE TABLE IF NOT EXISTS order_statuses (
    id SERIAL,
    status_type status_type,
    PRIMARY KEY (id)
);

INSERT INTO order_statuses (status_type)
     VALUES ('ACTIVE'), ('COMPLETE');
     

     

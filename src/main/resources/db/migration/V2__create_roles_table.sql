CREATE TYPE role_type AS enum ('ADMIN', 'USER');

CREATE TABLE IF NOT EXISTS "roles" (
    id SERIAL,
    role_type role_type,
    PRIMARY KEY (id)
);

INSERT INTO "roles" (role_type)
     VALUES ('ADMIN'), ('USER');
    

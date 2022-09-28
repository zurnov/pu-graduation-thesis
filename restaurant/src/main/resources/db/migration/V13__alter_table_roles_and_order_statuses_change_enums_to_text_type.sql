ALTER TABLE roles
    ALTER COLUMN role_type TYPE TEXT;

ALTER TABLE order_statuses
    ALTER COLUMN status_type TYPE TEXT;

DROP TYPE IF EXISTS role_type;

DROP TYPE IF EXISTS status_type;
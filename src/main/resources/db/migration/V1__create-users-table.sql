CREATE TABLE IF NOT EXISTS "users" (
    id BIGSERIAL PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_role TEXT NOT NULL CHECK (user_role IN ('ADMIN', 'USER'))
);

CREATE TYPE role as ENUM ('USER', 'ADMIN');

ALTER TABLE users
    ADD COLUMN role role;
ALTER TABLE users
    ALTER COLUMN role SET DEFAULT 'USER';
UPDATE users
    SET role = 'USER'
    WHERE role IS NULL;
ALTER TABLE users
    ALTER COLUMN role SET NOT NULL;

-- Make friendships unique (A cannot befriend B multiple times)
ALTER TABLE friends
    ADD CONSTRAINT unique_friendships UNIQUE (userId, friendId);
ALTER TABLE friends
    ADD CONSTRAINT no_self_friendships CHECK (userId != friendId);
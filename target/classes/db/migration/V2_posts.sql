CREATE TABLE posts (
    id BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    image VARCHAR(255),
    comment VARCHAR(255),
    title VARCHAR(255),
    createdAt TIMESTAMP WITH TIME ZONE NOT NULL,
    modifiedAt TIMESTAMP WITH TIME ZONE,
    deleted BOOLEAN NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE posts
    ADD CONSTRAINT user_id userId REFERENCES users (id);
ALTER TABLE posts
    ALTER COLUMN deleted SET DEFAULT FALSE;
ALTER TABLE posts
    ALTER COLUMN createdAt SET DEFAULT now;

--   Add creation and update times to user
ALTER TABLE users
    ADD COLUMN createdAt TIMESTAMP WITH TIME ZONE;
UPDATE users
    SET createdAt = now;
    WHERE createdAt = NULL;
ALTER TABLE users
    ALTER COLUMN createdAt SET NOT NULL;
ALTER TABLE users
    ALTER COLUMN createdAt SET DEFAULT now;
ALTER TABLE users
    ADD COLUMN updatedAt TIMESTAMP WITH TIME ZONE;
UPDATE users
    SET updatedAt = now;
    WHERE updatedAt = NULL;
ALTER TABLE users
    ALTER COLUMN updatedAt SET NOT NULL;
ALTER TABLE users
    ALTER COLUMN updatedAt SET DEFAULT now;

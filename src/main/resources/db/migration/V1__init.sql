-- Users
CREATE TABLE users
  (
     id         BIGSERIAL NOT NULL,
     name       VARCHAR(255),
     email      VARCHAR(255),
     nickname   VARCHAR(255),
     password   VARCHAR(255),
     createdAt TIMESTAMP WITH TIME ZONE NOT NULL,
     updatedAt TIMESTAMP WITH TIME ZONE NOT NULL,
     deleted    BOOLEAN NOT NULL,
     PRIMARY KEY (id)
  );

  ALTER TABLE users
    ADD CONSTRAINT make_unique UNIQUE (name, email, nickname, password);
  ALTER TABLE users
    ALTER COLUMN deleted SET DEFAULT FALSE;

-- Posts
CREATE TABLE posts (
    id BIGSERIAL NOT NULL,
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
    ADD CONSTRAINT user_id FOREIGN KEY(userId) REFERENCES users(id);
ALTER TABLE posts
    ALTER COLUMN deleted SET DEFAULT FALSE;

-- Friends
CREATE TABLE friends (
    id BIGSERIAL NOT NULL,
    userId BIGINT NOT NULL,
    friendId BIGINT NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE friends
    ADD CONSTRAINT user_ids FOREIGN KEY(userId) REFERENCES users(id);
ALTER TABLE friends
    ADD CONSTRAINT friend_ids FOREIGN KEY(friendId) REFERENCES users(id);
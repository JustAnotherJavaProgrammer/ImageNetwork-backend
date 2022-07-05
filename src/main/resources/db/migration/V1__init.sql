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
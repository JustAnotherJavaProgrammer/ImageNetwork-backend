CREATE TABLE friends (
    id BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    friendId BIGINT NOT NULL,
    PRIMARY KEY(id)
);

ALTER TABLE friends
    ADD CONSTRAINT user_ids FOREIGN KEY(userId) REFERENCES users(id);
ALTER TABLE friends
    ADD CONSTRAINT friend_ids FOREIGN KEY(friendId) REFERENCES users(id);
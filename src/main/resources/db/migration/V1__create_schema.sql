CREATE TABLE users (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(150) NOT NULL,
    password VARCHAR(250) NOT NULL,
    role     VARCHAR(20)  NOT NULL DEFAULT 'USER',
    PRIMARY KEY (id)
);

CREATE TABLE user_profiles (
    user_id         BIGINT       PRIMARY KEY NOT NULL,
    bio             VARCHAR(256),
    favorite_animal VARCHAR(256),
    magic_place     VARCHAR(256),
    age             INTEGER,
    CONSTRAINT fk_user_profiles_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

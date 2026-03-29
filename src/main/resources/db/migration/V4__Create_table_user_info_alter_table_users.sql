-- TODO: alterar a tabela de usuario removendo infomaçoes que nao sao exenciais para o sistema e as colocando em outra tabela
CREATE TABLE user_profiles (
    user_id     BIGINT  PRIMARY KEY NOT NULL,
    bio         VARCHAR(256),
    favorite_animal VARCHAR(256),
    magic_place VARCHAR(256),
    age         INTEGER,
    CONSTRAINT fk_user_profiles_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

ALTER TABLE users
    DROP COLUMN bio,
    DROP COLUMN favorite_animal,
    DROP COLUMN magic_place,
    DROP COLUMN age;
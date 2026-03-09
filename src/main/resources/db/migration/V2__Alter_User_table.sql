-- Alterando o tipo da coluna id de INT para BIGINT
-- Se o seu ID for AUTO_INCREMENT, é importante reafirmar isso no comando
ALTER TABLE users
    MODIFY COLUMN id BIGINT AUTO_INCREMENT;

-- Adicionando o novo atributo 'role' do tipo STRING (VARCHAR no MySQL)
-- Definimos um limite de caracteres (ex: 50) e podemos colocar um valor padrão
ALTER TABLE users
    ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'USER';
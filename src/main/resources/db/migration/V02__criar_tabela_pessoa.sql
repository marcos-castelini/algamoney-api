CREATE TABLE pessoa
(
    id          BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome        VARCHAR(50) NOT NULL,
    ativo       BIT(1)      NOT NULL,
    logradouro  VARCHAR(50),
    numero      VARCHAR(10),
    complemento VARCHAR(30),
    bairro      VARCHAR(40),
    cep         VARCHAR(10),
    cidade      VARCHAR(80),
    estado      VARCHAR(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (id, nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) VALUES (null, 'Marcos Paulo', 1, 'Rua Amador de Paula Bueno', '1809', 'Casa', 'Jardm Europa', '15.150-000', 'Monte Aprazível', 'SP');

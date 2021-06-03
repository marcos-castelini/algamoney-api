create table lancamento
(
    id              BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao       VARCHAR(50)    NOT NULL,
    data_vencimento date           not null,
    data_pagamento  date,
    valor           decimal(10, 2) not null,
    observacao      varchar(100),
    tipo            varchar(20)    not null,
    id_categoria    bigint(20) not null,
    id_pessoa       bigint(20) not null,
    foreign key (id_categoria) references categoria (id),
    foreign key (id_pessoa) references pessoa (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into lancamento (id, descricao, data_vencimento, data_pagamento, valor, observacao, tipo, id_categoria, id_pessoa) VALUES (null, 'Água', '2021-06-30', null, 30.50, '', 'DESPESA', 1, 1);

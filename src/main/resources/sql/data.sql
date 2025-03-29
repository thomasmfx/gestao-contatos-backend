-- Clientes

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('João da Silva', '12345678901', '1980-05-15', 'Rua das Flores, 123');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Ana Rodrigues', '11122233344', '1988-07-10', 'Alameda dos Anjos, 567');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Pedro Santos', '55566677788', '1995-12-25', 'Praça da Liberdade, 890');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Juliana Costa', '99900011122', '1982-09-30', 'Avenida Paulista, 1011');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Lucas Pereira', '33344455566', '2001-04-18', 'Rua Augusta, 1213');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Mariana Almeida', '44455566677', '1992-11-20', 'Rua da Paz, 1415');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Fernando Souza', '77788899900', '1978-03-08', 'Avenida Central, 1617');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Camila Oliveira', '22233344455', '2005-06-22', 'Travessa da Amizade, 1819');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Ricardo Pereira', '66677788899', '1985-01-12', 'Largo do Sossego, 2021');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Isabela Santos', '88899900011', '1999-08-05', 'Estrada da Esperança, 2223');

-- Contatos

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (1, 'telefone', '(11) 99999-9999', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (1, 'email', 'joao.silva@email.com', 'Email pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (2, 'telefone', '(21) 88888-8888', 'Celular trabalho');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (3, 'telefone', '(31) 77777-7777', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (3, 'email', 'carlos.oliveira@email.com', 'Email pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (4, 'telefone', '(41) 66666-6666', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (4, 'email', 'ana.rodrigues@email.com', 'Email trabalho');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (2, 'linkedin', 'linkedin.com/maria-souza', 'Perfil profissional');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Mariana Almeida', '44455566677', '1992-11-20', 'Rua da Paz, 1415');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Fernando Souza', '77788899900', '1978-03-08', 'Avenida Central, 1617');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Camila Oliveira', '22233344455', '2005-06-22', 'Travessa da Amizade, 1819');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Ricardo Pereira', '66677788899', '1985-01-12', 'Largo do Sossego, 2021');

INSERT INTO cliente (nome, cpf, data_nascimento, endereco)
    VALUES ('Isabela Santos', '88899900011', '1999-08-05', 'Estrada da Esperança, 2223');
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
    VALUES (1, 'Telefone', '(11) 99999-9999', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (1, 'E-mail', 'joao.silva@email.com', 'Email pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (2, 'Telefone', '(21) 88888-8888', 'Celular trabalho');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (3, 'Telefone', '(31) 77777-7777', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (3, 'E-mail', 'carlos.oliveira@email.com', 'Email pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (4, 'Telefone', '(41) 66666-6666', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (4, 'E-mail', 'ana.rodrigues@email.com', 'Email trabalho');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (2, 'LinkedIn', 'linkedin.com/maria-souza', 'Perfil profissional');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (5, 'Telefone', '(51) 55555-5555', 'Celular trabalho');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (5, 'E-mail', 'lucas.pereira@email.com', 'Email pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (6, 'Telefone', '(61) 44444-4444', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (6, 'E-mail', 'fernando.souza@email.com', 'Email trabalho');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (7, 'Telefone', '(71) 33333-3333', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (7, 'Instagram', '@camila_oliveira', 'Perfil pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (8, 'Telefone', '(81) 22222-2222', 'Celular trabalho');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (9, 'Telefone', '(91) 11111-1111', 'Celular pessoal');

INSERT INTO contato (cliente_id, tipo, valor, observacao)
    VALUES (9, 'E-mail', 'isabela.santos@email.com', 'Email pessoal');
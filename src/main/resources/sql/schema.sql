DROP TABLE IF EXISTS cliente CASCADE;

CREATE TABLE cliente (
   id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
   nome VARCHAR(100) NOT NULL,
   cpf VARCHAR(14) UNIQUE NOT NULL,
   data_nascimento DATE NOT NULL,
   endereco VARCHAR(255)
);

DROP TABLE IF EXISTS contato;

CREATE TABLE contato (
   id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
   cliente_id INTEGER NOT NULL,
   tipo VARCHAR(50) NOT NULL,
   valor VARCHAR(100) NOT NULL,
   observacao VARCHAR(255),
   CONSTRAINT fk_cliente
       FOREIGN KEY (cliente_id)
           REFERENCES cliente(id) ON DELETE CASCADE
);

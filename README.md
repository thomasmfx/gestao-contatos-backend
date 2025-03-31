# API Sistema de Gestão de Contatos - Backend

Este repositório contém a implementação backend do sistema de gestão de contatos desenvolvido para o desafio da Muralis. O backend foi construído como uma API RESTful utilizando Java e Spring Boot.

## Tecnologias Utilizadas

- Java 
- Spring Boot
- Maven
- Hibernate
- PostgreSQL

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── comerciosa/
│   │           └── gestao_contatos/
│   │               ├── config/
│   │               │   └── OpenApiConfig.java
│   │               ├── controller/
│   │               │   ├── ClienteController.java
│   │               │   └── ContatoController.java
│   │               ├── dto/
│   │               │   ├── request/
│   │               │   │   ├── ClienteRequestDTO.java
│   │               │   │   └── ContatoRequestDTO.java
│   │               │   └── response/
│   │               │       ├── ClienteResponseDTO.java
│   │               │       └── ContatoResponseDTO.java
│   │               ├── model/
│   │               │   ├── Cliente.java
│   │               │   └── Contato.java
│   │               ├── repository/
│   │               │   ├── ClienteRepository.java
│   │               │   └── ContatoRepository.java
│   │               ├── validation/
│   │               │   ├── Documento.java
│   │               │   └── DocumentoValidator.java
│   │               └── GestaoContatosApplication.java
│   └── resources/
│       ├── sql/
│           ├── data.sql
│           └── schema.sql
│       ├── static/
│       ├── templates/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── comerciosa/
                └── gestaocontatos/
                    └── GestaoContatosApplication.java
```

## Instalação

1. Caso ainda não tenha clonado o repositório, execute o seguinte comando:

```bash
git clone https://github.com/thomasmfx/gestao-contatos-backend.git
```

2. Navegue até o diretório do projeto:

```bash
cd gestao-contatos-backend
```

## Configuração do Banco de Dados

### Pré-requisitos

- PostgreSQL instalado
- Credenciais de acesso configuradas

<!-- Em `application.properties`, o projeto está configurado para criar o banco de dados e popular as tabelas automaticamente sempre que for iniciado: -->

### 1. Criação do Banco de Dados

Conecte-se ao PostgreSQL com um usuário que tenha permissão para criar bancos de dados e execute:

```sql
CREATE DATABASE agenda;
```

### 2. População do Banco

Dentro da pasta `src/main/resources/sql/`, os scripts para criação das tabelas e população de dados são, respectivamente: `schema.sql` e `data.sql`. Os scripts estão programados para serem executados toda vez que a aplicação é iniciada, porém, é necessário ajustar a configuração do banco em `application.properties` para funcionar localmente com seu usuário e senha.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agenda
# Insira seu usuario
spring.datasource.username=<seu_usuario>
# Insira sua senha
spring.datasource.password=<sua_senha>

spring.jpa.hibernate.ddl-auto=none 

spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:sql/schema.sql
spring.sql.init.data-locations=classpath:sql/data.sql
```

> [!NOTE]
> **Nota:** As instruções partem da premissa de que os servidores `localhost` estão usando as portas padrões ao serem iniciados. Caso exista alguma outra aplicação usando as portas referenciadas, finalize essas aplicações ou ajuste as URLs adequadamente a fim de garantir o funcionamento do projeto.

## Execução

### Pré-requisitos

- JDK (versão 17)
- Maven
- PostgreSQL com banco configurado conforme instruções deste README.md

### Passos para execução

1. Compile e execute o projeto:

```bash
mvn clean install
mvn spring-boot:run
```

Pronto! Caso tudo ocorra certo, a API estará disponível em: `http://localhost:8080`

## Documentação da API

A API utiliza Springdoc OpenAPI para documentação interativa. Após iniciar o servidor, você pode acessar a documentação completa em:

```
http://localhost:8080/swagger-ui/index.html#/
```

Esta interface permite:

- Visualizar todos os endpoints disponíveis
- Testar as requisições diretamente pelo navegador
- Verificar os modelos de dados e parâmetros esperados
- Explorar as respostas possíveis de cada endpoint

### Endpoints

#### Clientes

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | /clientes | Listar todos os clientes |
| GET | /clientes/{id} | Buscar cliente por ID |
| GET | /clientes?search={nome/cpf} | Buscar cliente por nome ou CPF |
| POST | /clientes | Cadastrar novo cliente |
| PUT | /clientes/{id} | Atualizar cliente |
| DELETE | /clientes/{id} | Excluir cliente |

#### Contatos

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | /contatos | Listar todos os contatos |
| GET | /contatos?clienteid={clienteId} | Listar contatos de um cliente |
| GET | /contatos/{id} | Buscar contato por ID |
| POST | /contatos | Cadastrar novo contato |
| PUT | /contatos/{id} | Atualizar contato |
| DELETE | /contatos/{id} | Excluir contato |

Para mais detalhes e exemplos de requisições, consulte a documentação interativa do Swagger UI.

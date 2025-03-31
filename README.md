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
│   │               ├── config/****
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

### 1. Criação do Banco de Dados

Conecte-se ao PostgreSQL com um usuário que tenha permissão para criar bancos de dados e execute:

```sql
CREATE DATABASE agenda;
```

### 2. População do Banco

Dentro da pasta `src/main/resources/sql/`, os scripts para criação das tabelas e população de dados são, respectivamente: `schema.sql` e `data.sql`. Os scripts estão programados para serem executados toda vez que a aplicação é iniciada. Além disso, as credenciais de acesso ao banco de dados estão configuradas em `application.properties`, usando variáveis de ambiente:

```properties
spring.datasource.username={$DB_USER$}
spring.datasource.password={$DB_PASSWORD$}
```

Antes de rodar o projeto garanta que as variáveis estão configuradas no seu ambiente local para armazenar as credenciais do banco de dados:

### Linux/macOS (Terminal ou ~/.zshrc/.bashrc)

```bash
export DB_USER=seu_usuario
export DB_PASSWORD=sua_senha
```

### Windows (CMD ou PowerShell)

#### CMD

```cmd
set DB_USER=seu_usuario
set DB_PASSWORD=sua_senha
```

#### PowerShell 

```powershell
$env:DB_USER="seu_usuario"
$env:DB_PASSWORD="sua_senha"
```

Algumas IDEs, como o IntelliJ, não carregam variáveis de ambiente automaticamente.
Se estiver usando IntelliJ e a aplicação não rodar, recomendo que siga [este tutorial](https://coffops.com/configurar-variaveis-ambiente-intellij/).

## Execução

> [!NOTE]
> As instruções partem da premissa de que os servidores `localhost` estão usando as portas padrões ao serem iniciados. Caso exista alguma outra aplicação usando as portas referenciadas, finalize essas aplicações ou ajuste as URLs adequadamente a fim de garantir o funcionamento do projeto.

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

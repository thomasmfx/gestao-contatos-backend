# API Sistema de Gestão de Contatos - Backend

Este repositório contém a implementação backend do sistema de gestão de contatos desenvolvido para o desafio da Muralis. O backend foi construído como uma API RESTful utilizando Java e Spring Boot, além da integração com a API externa da ViaCep vara busca de endereço.

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Maven
- Hibernate
- PostgreSQL
- Docker
- JUnit 5
- ViaCep API

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── comerciosa/
│   │           └── gestao_contatos/
│   │               ├── config/
│   │               │   └── AppConfig.java
│   │               ├── controller/
│   │               │   ├── ClienteController.java
│   │               │   ├── ContatoController.java
│   │               │   └── EnderecoViaCepController.java
│   │               ├── dto/
│   │               │   ├── error/
│   │               │   │   └── ErrorResponseDTO.java
│   │               │   ├── request/
│   │               │   │   ├── ClienteRequestDTO.java
│   │               │   │   ├── ContatoRequestDTO.java
│   │               │   │   └── EnderecoRequestDTO.java
│   │               │   └── response/
│   │               │       ├── ClienteDecadaResponseDTO.java
│   │               │       ├── ClienteResponseDTO.java
│   │               │       ├── ClienteResponseDTO.java
│   │               │       └── EnderecoResponseDTO.java
│   │               ├── exception/
│   │               │   ├── BadRequestException.java
│   │               │   ├── ControllerAdvisor.java
│   │               │   └── ResourceNotFoundException.java
│   │               ├── mapper/
│   │               │   ├── ClienteMapper.java
│   │               │   ├── ContatoMapper.java
│   │               │   ├── EnderecoMapper.java
│   │               │   └── EnderecoViaCepMapper.java
│   │               ├── model/
│   │               │   ├── Cliente.java
│   │               │   ├── Contato.java
│   │               │   ├── Endereco.java
│   │               │   └── EnderecoViaCep.java
│   │               ├── repository/
│   │               │   ├── ClienteRepository.java
│   │               │   ├── ContatoRepository.java
│   │               │   └── EnderecoRepository.java
│   │               ├── service/
│   │               │   ├── ClienteService.java
│   │               │   ├── ContatoService.java
│   │               │   └── EnderecoViaCepService.java
│   │               ├── validation/
│   │               │   ├── Documento.java
│   │               │   └── DocumentoValidator.java
│   │               └── GestaoContatosApplication.java
│   └── resources/
│       └── application.properties
└── test/
│   └── java/
│       └── com/
│           └── comerciosa/
│               └── gestaocontatos/
│                   │   ├── Service/
│                   │   │   └── EnderecoViaCepServiceTest.java
│                   │   └── Validation/
│                   │   │   └── DocumentoValidatorTest.java                    
│                   └── GestaoContatosApplication.java
├── Dockerfile
├── pom.xml
└── README.md
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

## Configuração das variáveis de ambiente

Para estabelecer a conexão com o banco de dados, crie um arquivo `.env` na raíz do projeto e configure as variáveis:

```js
DATABASE_USER={$DATABASE_USER}
DATABASE_PASSWORD={$DATABASE_PASSWORD}
DATABASE_HOST=db // Não alterar
DATABASE_PORT=5432 // Não alterar
DATABASE_NAME={$DATABASE_NAME}
```

Lembre-se de substituir os valores manualmente no `.env`, ou crie as variáveis na sua máquina e referencia elas. Exemplo:

```js
DATABASE_USER=postgres // manualmente
// ou
DATABASE_USER={$DATABASE_USER} // consumindo da máquina
```

Para criar varáveis de ambiente globais na sua máquina:

### Linux/macOS

```bash
export DATABASE_USER=<usuario>
export DATABASE_PASSWORD=<senha>
export DATABASE_HOST=<host>
export DATABASE_PORT=<porta>
export DATABASE_NAME=<database>
```

### Windows (CMD ou PowerShell)

#### CMD

```cmd
set DATABASE_USER=<usuario>
set DATABASE_PASSWORD=<senha>
set DATABASE_HOST=<host>
set DATABASE_PORT=<porta>
set DATABASE_NAME=<database>
```

#### PowerShell 

```powershell
$env:DATABASE_USER="usuario"
$env:DATABASE_PASSWORD="senha"
$env:DATABASE_HOST="host"
$env:DATABASE_PORT="porta"
$env:DATABASE_NAME="database"
```

## Documentação da API

A API utiliza Springdoc OpenAPI para documentação interativa. Após iniciar o servidor, você pode acessar a documentação completa em:

```
http://localhost:7772/swagger-ui/index.html#/
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

#### Endereço

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | /endereco/{cep} | Obtém um endereço específico |

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

## Referências

Materiais que serviram de apoio na construção do backend:

[https://www.youtube.com/watch?v=lUVureR5GqI](https://www.youtube.com/watch?v=lUVureR5GqI)
# Arquitetura do Sistema de Microsserviços - Catálogo de Livros

## Estrutura do Projeto

Este projeto é composto por 3 microsserviços independentes:

```
catalogo-livros/
├── livros/           (Microsserviço de Catálogo de Livros)
├── pedido/           (Microsserviço de Pedidos)
└── usuarios/         (Microsserviço de Usuários)
```

## Microsserviços

### 1. **Livros** (Catálogo de Livros)
- **Porta:** 8080
- **Banco de Dados:** `catalogo_livros`
- **Responsabilidades:**
  - Gerenciar livros disponíveis
  - Manter catálogo atualizado
  - Validar disponibilidade de livros
- **Endpoints:**
  - `GET /livros` - Listar todos os livros
  - `GET /livros/{id}` - Buscar livro por ID
  - `POST /livros` - Criar novo livro
  - `PUT /livros/{id}` - Atualizar livro
  - `DELETE /livros/{id}` - Deletar livro
- **Clients:**
  - `UsuarioClient` (comunica com Usuários)

### 2. **Pedidos** (Gerenciamento de Pedidos)
- **Porta:** 8082
- **Banco de Dados:** `pedidos`
- **Responsabilidades:**
  - Gerenciar pedidos de compra
  - Integrar com Livros e Usuários
  - Rastrear status dos pedidos
- **Endpoints:**
  - `GET /pedidos` - Listar todos os pedidos
  - `GET /pedidos/{id}` - Buscar pedido por ID
  - `POST /pedidos` - Criar novo pedido
  - `PUT /pedidos/{id}` - Atualizar pedido
  - `DELETE /pedidos/{id}` - Cancelar pedido
- **Clients:**
  - `LivroClient` (comunica com Livros)
  - `UsuarioClient` (comunica com Usuários)
- **DTOs:**
  - `LivroDTO` - Representação de livro no contexto de pedido
  - `UsuarioDTO` - Representação de usuário no contexto de pedido

### 3. **Usuários** (Gerenciamento de Usuários)
- **Porta:** 8081
- **Banco de Dados:** `usuarios`
- **Responsabilidades:**
  - Gerenciar dados de usuários
  - Autenticação e autorização
  - Validar informações de usuários
- **Endpoints:**
  - `GET /usuarios` - Listar todos os usuários
  - `GET /usuarios/{id}` - Buscar usuário por ID
  - `POST /usuarios` - Criar novo usuário
  - `PUT /usuarios/{id}` - Atualizar usuário
  - `DELETE /usuarios/{id}` - Deletar usuário
- **Clients:** Nenhum (serve para os outros microsserviços)

## Estrutura de Diretórios

```
src/main/java/com/catalogo/
├── livros/
│   ├── CatalogoLivrosApplication.java  (Classe principal)
│   ├── controller/
│   │   └── LivroController.java        (Endpoints)
│   ├── model/
│   │   └── Livro.java                  (Entidade JPA)
│   ├── repository/
│   │   └── LivroRepository.java        (Acesso a dados)
│   ├── service/
│   │   └── LivroService.java           (Lógica de negócio)
│   ├── client/
│   │   └── UsuarioClient.java          (Feign Client)
│   └── resources/
│       └── application.properties
│
├── pedido/
│   ├── PedidoApplication.java          (Classe principal)
│   ├── controller/
│   │   └── PedidoController.java       (Endpoints)
│   ├── model/
│   │   └── Pedido.java                 (Entidade JPA)
│   ├── repository/
│   │   └── PedidoRepository.java       (Acesso a dados)
│   ├── service/
│   │   └── PedidoService.java          (Lógica de negócio)
│   ├── client/
│   │   ├── LivroClient.java            (Feign Client)
│   │   └── UsuarioClient.java          (Feign Client)
│   ├── dto/
│   │   ├── LivroDTO.java               (Data Transfer Object)
│   │   └── UsuarioDTO.java             (Data Transfer Object)
│   └── resources/
│       └── application.properties
│
└── usuarios/
    ├── UsuarioApplication.java         (Classe principal)
    ├── controller/
    │   └── UsuarioController.java      (Endpoints)
    ├── model/
    │   └── Usuario.java                (Entidade JPA)
    ├── repository/
    │   └── UsuarioRepository.java      (Acesso a dados)
    ├── service/
    │   └── UsuarioService.java         (Lógica de negócio)
    └── resources/
        └── application.properties
```

## Configuração de Banco de Dados

Cada microsserviço possui seu próprio banco de dados:

```sql
CREATE DATABASE catalogo_livros;
CREATE DATABASE pedidos;
CREATE DATABASE usuarios;
```

## Comunicação entre Microsserviços

A comunicação é realizada via **OpenFeign** (HTTP/REST):

- **Livros ↔ Usuários** via `UsuarioClient`
- **Pedidos ↔ Livros** via `LivroClient`
- **Pedidos ↔ Usuários** via `UsuarioClient`

### URLs de Comunicação

- Livros: `http://localhost:8080`
- Usuários: `http://localhost:8081`
- Pedidos: `http://localhost:8082`

## Dependências Principais

- **Spring Boot 3.5.7**
- **Spring Data JPA** (Persistência)
- **Spring Cloud OpenFeign** (Comunicação entre serviços)
- **MySQL Connector** (Banco de dados)
- **Spring Validation** (Validação de dados)
- **Spring Devtools** (Desenvolvimento)

## Como Executar

### 1. Preparar Banco de Dados

Execute os scripts SQL para criar os bancos de dados e tabelas.

### 2. Configurar Aplicações

Cada microsserviço tem seu próprio `application.properties` com:
- Nome da aplicação
- Credenciais do banco de dados
- Porta do servidor

### 3. Iniciar os Microsserviços

#### Opção 1: Via Maven
```bash
# Terminal 1 - Livros
mvn clean install -DskipTests
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=livros"

# Terminal 2 - Usuários
mvn clean install -DskipTests
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=usuarios"

# Terminal 3 - Pedidos
mvn clean install -DskipTests
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=pedidos"
```

#### Opção 2: Executar direto do IDE
- Clique com botão direito em `CatalogoLivrosApplication.java` → Run
- Clique com botão direito em `UsuarioApplication.java` → Run
- Clique com botão direito em `PedidoApplication.java` → Run

## Padrões de Arquitetura

### Camadas

1. **Controller** - Recebe requisições HTTP
2. **Service** - Contém lógica de negócio
3. **Repository** - Acesso aos dados (JPA)
4. **Model** - Entidades do banco de dados
5. **Client** - Comunicação com outros microsserviços
6. **DTO** - Transferência de dados entre serviços

### Fluxo de Requisição

```
Cliente HTTP
    ↓
Controller (@RequestMapping)
    ↓
Service (regras de negócio)
    ↓
Repository (JPA)
    ↓
Banco de Dados
```

### Fluxo de Comunicação entre Serviços

```
Pedidos.Service
    ↓
LivroClient (Feign)
    ↓
HTTP GET /livros/{id}
    ↓
Livros.Controller
    ↓
Livros.Service
    ↓
Livros.Repository
    ↓
Banco de Dados (livros)
```

## Boas Práticas Implementadas

✅ Separação por microsserviços  
✅ Isolamento de bancos de dados  
✅ Comunicação assíncrona via REST  
✅ Uso de DTOs para transferência de dados  
✅ Camadas bem definidas (Controller → Service → Repository)  
✅ OpenFeign para comunicação tipada  

## Próximas Melhorias

- [ ] Implementar API Gateway (Kong, API Management)
- [ ] Adicionar autenticação JWT
- [ ] Implementar Circuit Breaker (Hystrix/Resilience4j)
- [ ] Adicionar logging centralizado (ELK Stack)
- [ ] Implementar testes integrados
- [ ] Containerizar com Docker
- [ ] Orquestração com Kubernetes


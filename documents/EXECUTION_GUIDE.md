# Guia de Execução - Microsserviços

## Requisitos

- Java 17+
- Maven 3.8+
- MySQL 8.0+

## Passo 1: Preparar Banco de Dados

Execute este script no MySQL:

```sql
-- Criar bancos de dados
CREATE DATABASE IF NOT EXISTS catalogo_livros CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS usuarios CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS pedidos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Criar usuário (se necessário)
-- CREATE USER 'root'@'localhost' IDENTIFIED BY '1234';
-- GRANT ALL PRIVILEGES ON catalogo_livros.* TO 'root'@'localhost';
-- GRANT ALL PRIVILEGES ON usuarios.* TO 'root'@'localhost';
-- GRANT ALL PRIVILEGES ON pedidos.* TO 'root'@'localhost';
-- FLUSH PRIVILEGES;
```

## Passo 2: Compilar o Projeto

```bash
mvn clean install -DskipTests
```

## Passo 3: Executar os Microsserviços

### Opção A: IDE (Recomendado)

1. **Livros (Porta 8080)**
   - Clique direito em `src/main/java/com/catalogo/livros/CatalogoLivrosApplication.java`
   - Selecione "Run CatalogoLivrosApplication"

2. **Usuários (Porta 8081)**
   - Clique direito em `src/main/java/com/catalogo/usuarios/UsuarioApplication.java`
   - Selecione "Run UsuarioApplication"

3. **Pedidos (Porta 8082)**
   - Clique direito em `src/main/java/com/catalogo/pedido/PedidoApplication.java`
   - Selecione "Run PedidoApplication"

### Opção B: Linha de Comando

Abra 3 terminais diferentes e execute:

```bash
# Terminal 1 - Serviço de Livros
cd c:\Users\maria\OneDrive\Área de Trabalho\Semestre 4\livros
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"

# Terminal 2 - Serviço de Usuários
cd c:\Users\maria\OneDrive\Área de Trabalho\Semestre 4\livros
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"

# Terminal 3 - Serviço de Pedidos
cd c:\Users\maria\OneDrive\Área de Trabalho\Semestre 4\livros
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"
```

## Verificar Status

### Health Check

```bash
# Verificar se os serviços estão rodando
curl http://localhost:8080
curl http://localhost:8081
curl http://localhost:8082
```

### Logs

Procure por mensagens como:
```
Started CatalogoLivrosApplication in X seconds
Started UsuarioApplication in X seconds
Started PedidoApplication in X seconds
```

## Testar a Comunicação entre Microsserviços

### 1. Criar um Usuário

```bash
curl -X POST http://localhost:8081/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","email":"joao@example.com"}'
```

### 2. Criar um Livro

```bash
curl -X POST http://localhost:8080/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Spring Boot na Prática","autor":"João Silva","preco":89.90}'
```

### 3. Criar um Pedido (vai comunicar com os outros serviços)

```bash
curl -X POST http://localhost:8082/pedidos \
  -H "Content-Type: application/json" \
  -d '{"usuarioId":1,"livroId":1,"quantidade":2}'
```

## Troubleshooting

### Erro: "Connection refused"
- Verifique se os 3 microsserviços estão rodando
- Confirme as portas: 8080, 8081, 8082

### Erro: "Database connection failed"
- Verifique credenciais no `application.properties`
- Confirme que MySQL está rodando
- Execute o script SQL de criação dos bancos

### Erro: "Feign Client error"
- Verifique os logs para detalhes
- Confirme que o serviço destino está rodando
- Verifique a URL no `@FeignClient` está correta

## Estrutura de Portas

| Serviço | Porta | DB |
|---------|-------|-----|
| Livros | 8080 | catalogo_livros |
| Usuários | 8081 | usuarios |
| Pedidos | 8082 | pedidos |

## Próximos Passos

1. Implementar validação de dados com `@Valid` e `@Validated`
2. Adicionar tratamento de exceções global
3. Implementar testes unitários e integrados
4. Adicionar documentação Swagger/OpenAPI
5. Implementar paginação nas listagens
6. Adicionar filtros e buscas avançadas


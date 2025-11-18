# Guia de Conexão de Microsserviços

## Arquitetura Implementada

Seus 3 microsserviços agora estão configurados para se comunicarem através de:

### 1. **Eureka Service Discovery** 🔍
- Permite que os microsserviços se registrem e se descubram automaticamente
- **Servidor**: `http://localhost:8761/eureka/`

### 2. **Spring Cloud OpenFeign** 📡
- Abstração HTTP para chamadas REST entre serviços
- Simplifica a comunicação sem usar `RestTemplate` manualmente

---

## Microsserviços e Portas

| Serviço | Porta | Nome Eureka |
|---------|-------|-------------|
| 📚 Catálogo de Livros | 8080 | `catalogo-livros-service` |
| 📦 Pedidos | 8082 | `pedidos-service` |
| 👤 Usuários | 8081 | `usuarios-service` |

---

## Como Usar - Exemplos

### ✅ No Serviço de Pedidos: Buscar Livro

```java
// Injetar o cliente Feign
@Autowired
private LivroClient livroClient;

// Usar em qualquer lugar
public void procesarPedido(Long livroId) {
    LivroDTO livro = livroClient.buscarLivroPorId(livroId);
    // Usar dados do livro
}
```

### ✅ No Serviço de Pedidos: Buscar Usuário

```java
@Autowired
private UsuarioClient usuarioClient;

public void criarPedido(Long usuarioId, Long livroId) {
    UsuarioDTO usuario = usuarioClient.buscarUsuarioPorId(usuarioId);
    LivroDTO livro = livroClient.buscarLivroPorId(livroId);
    // Processar pedido com dados do usuário e livro
}
```

### ✅ No Serviço de Livros: Buscar Usuário (autor/criador)

```java
@Autowired
private UsuarioClient usuarioClient;

public Livro salvarLivro(Livro livro, Long usuarioId) {
    // Validar se usuário existe
    usuarioClient.buscarUsuarioPorId(usuarioId);
    return livroRepository.save(livro);
}
```

---

## Passos para Executar Tudo

### 1️⃣ **Instalar Eureka Server**

Crie um novo projeto Spring Boot com dependência:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

**application.properties:**
```properties
spring.application.name=eureka-server
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

**Main Class:**
```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

### 2️⃣ **Compilar Projeto Principal**

```bash
mvn clean install
```

### 3️⃣ **Iniciar os Serviços**

**Terminal 1 - Eureka Server:**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=eureka"
# Acesse: http://localhost:8761
```

**Terminal 2 - Microsserviço de Usuários (8081):**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=usuarios"
```

**Terminal 3 - Microsserviço de Livros (8080):**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=livros"
```

**Terminal 4 - Microsserviço de Pedidos (8082):**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=pedidos"
```

---

## Verificar Status

- **Eureka Dashboard**: http://localhost:8761
- **Livros**: http://localhost:8080
- **Usuários**: http://localhost:8081
- **Pedidos**: http://localhost:8082

Todos os serviços devem aparecer com status **UP** no Eureka Dashboard.

---

## Fluxo de Comunicação

```
┌─────────────┐
│   Cliente   │
└──────┬──────┘
       │ HTTP Request
       ▼
┌─────────────────────────────────────────┐
│        API Gateway (Opcional)           │
└──────────┬──────────────────────────────┘
           │
       ┌───┴────┬──────────┬──────────┐
       │         │          │          │
       ▼         ▼          ▼          ▼
   ┌────────┐ ┌──────┐ ┌───────┐ ┌────────┐
   │Eureka  │ │Livros│ │Usuários│ │Pedidos│
   │Server  │ │(8080)│ │ (8081) │ │(8082) │
   └────────┘ └──┬───┘ └───┬───┘ └───┬───┘
            Feign │        │         │
            Calls └─────────┴─────────┘
```

---

## Dependências Adicionadas

✅ `spring-cloud-starter-netflix-eureka-client` - Registro em Eureka
✅ `spring-cloud-starter-openfeign` - Clientes HTTP declarativos

---

## Próximos Passos (Opcional)

1. **API Gateway** - Adicionar Spring Cloud Gateway para rotear requisições
2. **Load Balancer** - Usar Ribbon (já integrado com Eureka)
3. **Circuit Breaker** - Adicionar Resilience4j para resiliência
4. **Logging Centralizado** - ELK Stack ou Splunk

---

## Troubleshooting

### Serviço não registra no Eureka
- Verifique se Eureka Server está rodando em `localhost:8761`
- Cheque a configuração do `application.properties`

### Feign Client retorna erro
- Verifique se o serviço está **UP** no Eureka
- Cheque se a anotação `@FeignClient` tem o nome correto do serviço
- Valide se o endpoint existe no serviço chamado

### Erro de timeout
- Aumente o timeout nas properties:
```properties
feign.client.config.default.connectTimeout=5000
feign.client.config.default.readTimeout=5000
```


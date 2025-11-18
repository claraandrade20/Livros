# Catálogo de Livros

> Microserviço simples para gerenciar um catálogo de livros — CRUD básico usando Spring Boot + Spring Data JPA + MySQL.

## Visão geral

O projeto fornece uma API REST mínima para criar, listar, buscar e deletar livros. Cada livro possui título, autor, categoria e preço. A aplicação foi criada com Spring Boot 3.5.7 e está preparada para rodar com Java 17+ (recomendado).

## Tecnologias

- Java 17+
- Spring Boot 3.5.7
- Spring Data JPA
- MySQL (driver `mysql-connector-j`)
- Maven

## Estrutura principal

- `src/main/java/com/catalogo/livros` — código-fonte
  - `controller/LivroController.java` — expõe os endpoints REST
  - `service/LivroService.java` — lógica de aplicação
  - `repository/LivroRepository.java` — repositório JPA
  - `model/Livro.java` — entidade JPA (id, titulo, autor, categoria, preco)
- `src/main/resources/application.properties` — configurações (datasource, JPA, porta)

## Modelo `Livro`

Campos:

- `id` (Long) — chave autogerada
- `titulo` (String)
- `autor` (String)
- `categoria` (String)
- `preco` (Double)

## Endpoints

Base: `http://localhost:8080/livros`

- `POST /livros`  
  - Cria um novo livro. Enviar JSON no body. Exemplo:
  ```json
  {
    "titulo": "Exemplo",
    "autor": "Autor",
    "categoria": "Ficcao",
    "preco": 29.9
  }
  ```

- `GET /livros`  
  - Retorna lista com todos os livros.

- `GET /livros/{id}`  
  - Retorna um livro por `id`.

- `DELETE /livros/{id}`  
  - Deleta o livro com o `id` informado.

- `GET /livros/buscar?titulo=...`  
  - Busca livros cujo título contém o valor informado.

- `GET /livros/categoria/{categoria}`  
  - Retorna livros de uma categoria específica.

## Configuração do banco (padrão)

Arquivo: `src/main/resources/application.properties`

Configuração de exemplo já presente no projeto (ajuste usuário/senha/URL conforme seu ambiente):

```
spring.datasource.url=jdbc:mysql://localhost:3306/catalogo_livros?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

Observação: quando `spring.jpa.hibernate.ddl-auto=update`, o Hibernate cria/atualiza as tabelas automaticamente — use com cuidado em produção.

## Como compilar e executar

Pré-requisitos:

- Java 17 ou superior instalado (recomendado) — ver `java -version`
- Maven instalado (ou use `./mvnw` / `mvnw.cmd` no Windows)
- MySQL rodando com banco `catalogo_livros` (ou ajuste a URL no `application.properties`)

Build com Maven:

```powershell
cd "C:\caminho\para\projeto\livros"
mvn clean package -DskipTests
```

Executar o JAR (exemplo usando Java do sistema):

```powershell
java -jar target\livros-0.0.1-SNAPSHOT.jar
```

Se o seu sistema tiver múltiplas versões de Java e você quiser usar a JRE empacotada no VS Code, por exemplo, rode:

```powershell
& 'C:\Users\maria\.vscode\extensions\redhat.java-1.47.0-win32-x64\jre\21.0.8-win32-x86_64\bin\java.exe' -jar target\livros-0.0.1-SNAPSHOT.jar
```

## Executando em desenvolvimento (com reloading)

Você pode usar o `spring-boot-devtools` (já incluído) ao executar a aplicação a partir da IDE para reload automático.

## Observações e próximos passos

- A versão do Spring Boot (3.5.7) requer Java 17+. Recomenda-se manter `pom.xml` com `<java.version>17</java.version>`.
- Recomendo configurar credenciais do banco em variáveis de ambiente ou `application-{profile}.properties` para não deixar senhas em repositório.
- Poderíamos adicionar testes de integração, DTOs e tratamento de erros (ex.: retornar 404 quando não encontrado) como melhorias.

## Contato

Projeto mantido por quem o enviou ao repositório. Para ajustes no `application.properties` ou deploy, entre em contato com o autor.

---
Arquivo gerado automaticamente pelo assistente; ajuste conforme necessário.

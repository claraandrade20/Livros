# Projeto: Catálogo de Livros (Microsserviços)

Este repositório contém a implementação de um sistema de microsserviços para um Catálogo de Livros. O objetivo deste documento é explicitar o que já está implementado, o que falta para cumprir os requisitos de submissão e instruções práticas para executar e preparar a entrega.

## Visão geral
- Serviços implementados (no mesmo repositório):
  - `livros` (Catálogo de Livros) — porta `8080` — classe principal: `CatalogoLivrosApplication`
  - `usuarios` (Gerenciamento de Usuários) — porta `8081` — classe principal: `UsuarioApplication`
  - `pedidos` (Gerenciamento de Pedidos) — porta `8082` — classe principal: `PedidoApplication`

## Status de conformidade com a submissão
- Título do projeto: presente (`Catálogo de Livros`).
- Solução arquitetural: existe documentação textual em `document/ARCHITECTURE.md` e `document/MICROSSERVICES_GUIDE.md`. Diagramas visuais foram omitidos por solicitação (opcional).
- Funcionalidades: cada integrante pode implementar um microserviço (há 3 serviços implementados). Falta corrigir um bug na integração entre serviços (detalhes abaixo).
- Entrega (vídeos e repositórios públicos): NÃO há repositórios públicos separados nem links de deploy. Para cumprir EXATAMENTE o enunciado, revise a seção "Repositórios e vídeos" abaixo.

## Como executar localmente (rápido)
1. Prepare os bancos de dados MySQL: `catalogo_livros`, `usuarios`, `pedidos` (ver `document/EXECUTION_GUIDE.md`).
2. Na raiz do projeto execute:

```powershell
mvn clean install -DskipTests
# Em terminais separados, execute:
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=livros"
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=usuarios"
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=pedidos"
```

Endpoints principais:
- Livros: `http://localhost:8080/livros`
- Usuários: `http://localhost:8081/usuarios`
- Pedidos: `http://localhost:8082/pedidos`

## Bug conhecido e correção recomendada (essencial)
O serviço `pedidos` atualmente faz chamadas com `RestTemplate` usando URLs incorretas (portas invertidas). Para garantir comunicação correta entre serviços, escolha uma das opções abaixo:

- Opção A (recomendada): usar os `FeignClient` já existentes. Em `PedidoService` injete `LivroClient` e `UsuarioClient` e chame:

```java
UsuarioDTO usuario = usuarioClient.buscarUsuarioPorId(usuarioId);
LivroDTO livro = livroClient.buscarLivroPorId(livroId);
```

- Opção B (corrigir `RestTemplate`): usar as URLs corretas:

```java
restTemplate.getForObject("http://localhost:8081/usuarios/" + usuarioId, UsuarioDTO.class);
restTemplate.getForObject("http://localhost:8080/livros/" + livroId, LivroDTO.class);
```

Se quiser, posso aplicar a correção automaticamente (opção A recomendada).

## Repositórios e vídeos (como cumprir EXATAMENTE)
O enunciado pede que cada integrante mostre "cada repositório público no GitHub para cada microserviço" e um vídeo por integrante. Isso implica:

- Preferível: criar 3 repositórios públicos separados (um por microserviço). Vantagens:
  - Atende literalmente ao enunciado
  - Permite que cada integrante gerencie seu repositório
  - Facilita deploy independente de cada serviço

- Alternativa aceita somente se combinada com o professor: manter um monorepo e, no vídeo, navegar até cada pasta do microserviço e demonstrar execução e deploy. Porém, a frase do enunciado sugere repositórios separados; confirmar com o professor evita problemas.

- Se os repositórios forem privados, adicione `ronaldo@unifor.br` como colaborador conforme instrução.

## Deploy / links públicos
- Atualmente NÃO existem links públicos de deploy. Para entregar, escolha uma opção de cloud (ex.: Railway, Render, Heroku, Azure App Service) e crie um deploy para cada serviço. Adicione os links no `README.md` de cada repositório e neste documento.

## Checklist curto (o que falta fazer)
- [ ] Corrigir integração entre microsserviços (PedidoService).
- [ ] Decidir se vai criar 3 repositórios públicos (recomendado) ou manter monorepo com aprovação do professor.
- [ ] Criar/registrar os links públicos de deploy para cada serviço e adicioná-los aos READMEs.
- [ ] Gravar os vídeos (1–5 minutos por integrante) demonstrando repositório, execução local e deploy.
- [ ] Incluir evidência de aprovação do professor (se exigido).

## Quer que eu aplique a correção de integração agora?
Responda "Sim — aplicar Feign" para eu corrigir `PedidoService` usando Feign, ou "Sim — corrigir RestTemplate" para ajustar apenas as URLs, ou "Não agora" para que eu gere o checklist e templates de README separados para cada microserviço.

---
Documento gerado automaticamente para facilitar a submissão requerida pelo trabalho.

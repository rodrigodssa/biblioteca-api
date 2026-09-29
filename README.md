# Biblioteca API

API REST para gerenciamento de uma biblioteca — cadastro de livros, autores, usuários e controle de empréstimos, com regras de negócio, autenticação por papéis e documentação interativa.

## Tecnologias

- Java 21
- Spring Boot 4.1.1 / Spring Framework 7
- Spring Data JPA + PostgreSQL
- Spring Security (autenticação HTTP Basic, autorização por papéis)
- Bean Validation
- Lombok
- JUnit 5 + Mockito
- springdoc-openapi (Swagger UI)

## Arquitetura

Camadas separadas por responsabilidade:
Controller → Service → Repository → Banco de dados (PostgreSQL)

- **Controller**: recebe requisições HTTP e devolve respostas (`ResponseEntity`)
- **Service**: regras de negócio (ex: um livro só pode ser emprestado se não houver empréstimo em aberto)
- **Repository**: acesso a dados via Spring Data JPA

## Funcionalidades

- CRUD completo de Livros, Autores e Usuários
- Relacionamento entre entidades (Livro ↔ Autor, Empréstimo ↔ Livro/Usuário)
- Regra de disponibilidade: um livro não pode ser emprestado se já estiver emprestado
- Tratamento centralizado de erros (`@RestControllerAdvice`), com status HTTP apropriados (400, 404, 409)
- Validação de dados de entrada (`@NotBlank`, `@NotNull`)
- Autenticação HTTP Basic com dois papéis: `BIBLIOTECARIO` (leitura e escrita) e `LEITOR` (somente leitura)
- Testes unitários (Mockito) e de integração (`@SpringBootTest` + MockMvc)
- Documentação interativa via Swagger UI

## Pré-requisitos

- JDK 21
- Maven
- PostgreSQL rodando localmente, com um banco chamado `biblioteca_db`

## Configuração

Este projeto **não** contém nenhuma senha no código. Todas as credenciais são lidas de variáveis de ambiente:

| Variável | Obrigatória | Descrição |
|---|---|---|
| `DB_PASSWORD` | Sim | Senha do usuário do PostgreSQL |
| `BIBLIOTECARIO_PASSWORD` | Sim | Senha do usuário `bibliotecario` da API |
| `LEITOR_PASSWORD` | Sim | Senha do usuário `leitor` da API |
| `DB_URL` | Não (padrão: `jdbc:postgresql://localhost:5432/biblioteca_db`) | URL de conexão do banco |
| `DB_USERNAME` | Não (padrão: `postgres`) | Usuário do PostgreSQL |

**Na IDE (IntelliJ):** Run → Edit Configurations → Environment variables, e adicione os pares `NOME=valor` separados por `;`.

**Via terminal:**
```bash
export DB_PASSWORD=sua_senha
export BIBLIOTECARIO_PASSWORD=sua_senha
export LEITOR_PASSWORD=sua_senha
```

## Executando o projeto

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## Documentação da API

Com a aplicação rodando, acesse:
http://localhost:8080/swagger-ui.html

## Endpoints principais

| Método | Endpoint | Papel exigido |
|---|---|---|
| GET | `/livros` | Autenticado |
| POST | `/livros` | BIBLIOTECARIO |
| PUT | `/livros/{id}` | BIBLIOTECARIO |
| DELETE | `/livros/{id}` | BIBLIOTECARIO |
| GET | `/autores` | Autenticado |
| GET | `/usuarios` | Autenticado |
| POST | `/emprestimos` | BIBLIOTECARIO |
| PUT | `/emprestimos/{id}/devolver` | BIBLIOTECARIO |

## Testes

```bash
./mvnw test
```

# Product and User Manager

Um sistema backend robusto desenvolvido com Spring Boot e Java 21, utilizando autenticação e autorização com Spring Security. Inclui conteinerização com Docker, banco de dados PostgreSQL gerenciado por Flyway e uma abordagem de arquitetura modular seguindo os princípios da Clean Architecture.

## 📚 Tabela de Conteúdo

1. [Sobre](#sobre)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Funcionalidades](#funcionalidades)
4. [Clean Architecture](#clean-architecture)
5. [Como Executar](#como-executar)
6. [Endpoints Principais](#endpoints-principais)
7. [Testes Unitários](#testes-unitarios)
8. [Estrutura do Projeto](#estrutura-do-projeto)
9. [Autor](#autor)

## ⚖️ Sobre

Este projeto é um sistema de gerenciamento de usuários e produtos com autenticação e autorização baseadas em roles (ADMIN e USER), utilizando JWT. Foi desenvolvido com foco em escalabilidade, modularidade e segurança.

### Principais Características:

#### ADMIN:

- Criar, atualizar e excluir usuários.
- Alterar roles de outros usuários.
- Gerenciar produtos (criar, atualizar e excluir).

#### USER:

- Visualização de produtos.

### Segurança:

- **Autenticação**: Realizada via JWT, com tokens incluídos no cabeçalho das requisições.
- **Autorização**: Controle de acessos baseado em roles, gerenciado pelo Spring Security.

## 🚀 Tecnologias Utilizadas

- **Java 21**: Versão mais recente do Java, com recursos aprimorados de desempenho e segurança.
- **Spring Boot**: Framework para desenvolvimento ágil e eficiente.
- **Spring Security**: Gerenciamento completo de autenticação e autorização.
- **PostgreSQL**: Banco de dados relacional de alta performance.
- **Flyway**: Migração e versionamento do banco de dados.
- **Docker**: Ferramenta para conteinerização e padronização de ambientes.
- **JUnit 5**: Framework para testes unitários.
- **Swagger**: Documentação interativa para APIs REST.

## 🔧 Funcionalidades

- **Usuários:**
  - **ADMIN**: Gerencia todos os usuários e roles.
  - **USER**: Consulta apenas informações.
- **Produtos:**
  - Gerenciamento completo de produtos para ADMIN.
  - Consulta de produtos para USER.
- **JWT:**
  - Token seguro para autenticação.
- **Clean Architecture:**
  - Modularidade que facilita a manutenção e expansão do projeto.

## 🔄 Clean Architecture

Este projeto implementa os princípios da **Clean Architecture**, garantindo uma separação clara de responsabilidades e a possibilidade de futuras integrações com módulos independentes.

### Estrutura do Projeto:

1. **Application**: Contém os casos de uso e interfaces.
2. **Domain**: Contém as entidades e regras de negócio.
3. **Entity**: Entidades de domínio.
4. **Exception**: Exceções relacionadas ao sistema.
5. **Infrastructure**: Implementação de gateways, controladores e integrações externas.

## ***⛏️ Como Executar***

### ***1. Clonar o Repositório***

```bash
git clone git@github.com:WenderGustavo/product-user-manager.git
cd product-user-manager
```

### 2. Subir o Banco de Dados com Docker

```bash
docker-compose up -d
```

Isso inicializará o PostgreSQL na porta **5432**.

### 3. Executar a Aplicação

```bash
./mvnw spring-boot:run
```

### 4. Acessar a Documentação Swagger

Abra o navegador e acesse:

```
http://localhost:8080/swagger-ui/index.html
```

## 🔗 Endpoints Principais

### Usuários:

- **GET /users**: Lista todos os usuários.
- **POST /users**: Cria um novo usuário.
- **PUT /users/{id}**: Atualiza um usuário.
- **DELETE /users/{id}**: Remove um usuário.

### Produtos:

- **GET /products**: Lista todos os produtos.
- **POST /products**: Cria um novo produto.
- **PUT /products/{id}**: Atualiza um produto.
- **DELETE /products/{id}**: Remove um produto.

## 📊 Testes Unitários

Execute os testes unitários para verificar a integridade do sistema:

```bash
./mvnw test
```

## 📂 Estrutura do Projeto

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── application/     # Casos de uso e interfaces
│   │   ├── domain/          # Regras de negócio
│   │   ├── entity/          # Entidades de domínio
│   │   ├── exception/       # Exceções relacionadas ao sistema
│   │   ├── infrastructure/  # Controllers, segurança e gateways
│   ├── resources/
│       ├── application.yml
│       └── db/migration/    # Migrações do Flyway
├── test/                    # Testes unitários
```

## 👨‍💼 Autor

Desenvolvido por **Wender Gustavo**.\
Conecte-se comigo no [LinkedIn](https://www.linkedin.com/in/wendergustavo/).

---

Este projeto está licenciado sob a licença [MIT](LICENSE).


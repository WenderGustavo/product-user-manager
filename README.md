# Product and User Manager

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-lightblue)
![Docker](https://img.shields.io/badge/Container-Docker-2496ED)
![License](https://img.shields.io/badge/License-MIT-yellow)

Este é um projeto de backend utilizando **Spring Boot** e **Java 21**, implementando autenticação com **Spring Security**, **Docker** para conteinerização, banco de dados **PostgreSQL** com migrações gerenciadas pelo **Flyway** e testes unitários. A arquitetura segue o padrão **Clean Architecture**.

---

## 📑 Tabela de Conteúdo
1. [Sobre](#sobre)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Funcionalidades](#funcionalidades)
4. [Clean Architecture](#clean-architecture)
5. [Como Executar](#como-executar)
6. [Endpoints Principais](#endpoints-principais)
7. [Testes Unitários](#testes-unitários)
8. [Estrutura do Projeto](#estrutura-do-projeto)
9. [Autor](#autor)

---

## 🛠 Sobre

Este projeto implementa um sistema de gerenciamento de usuários e produtos com controle de acesso baseado em roles (ADMIN e USER), utilizando JWT para autenticação e autorização.

### Funcionalidades Principais:

#### Para ADMIN:
- Criar, atualizar e excluir usuários.
- Alterar roles de outros usuários.
- Gerenciar produtos (criar, atualizar e excluir).

#### Para USER:
- Apenas visualizar produtos.

### Segurança:
- Autenticação via JWT, com tokens enviados no cabeçalho das requisições.
- Controle de permissões baseado em roles, gerenciado pelo Spring Security.

---

## 🚀 Tecnologias Utilizadas

- **[Java 21](https://openjdk.org/projects/jdk/21/)**: Versão mais recente do Java.
- **[Spring Boot](https://spring.io/projects/spring-boot)**: Framework para desenvolvimento ágil.
- **[Spring Security](https://spring.io/projects/spring-security)**: Gerenciamento de autenticação e autorização.
- **[PostgreSQL](https://www.postgresql.org/)**: Banco de dados relacional.
- **[Flyway](https://flywaydb.org/)**: Migração e versionamento do banco.
- **[Docker](https://www.docker.com/)**: Conteinerização para ambientes consistentes.
- **[JUnit](https://junit.org/junit5/)**: Testes unitários para maior confiabilidade.
- **[Swagger](https://swagger.io/)**: Documentação interativa de APIs.

---

## 📂 Clean Architecture

O projeto segue os princípios da **Clean Architecture**, garantindo modularidade e manutenibilidade.

### Estrutura:

1. **Domain**: Entidades e regras de negócio.
2. **Application**: Lógica de aplicação (use cases e interfaces).
3. **Infrastructure**: Integrações externas (controllers, gateways, repos).
4. **Configurações e Exceções**: Configuração de segurança, OpenAPI e tratativas de erro.

---

## 🏗️ Como Executar

### 1. Clonar o Repositório

```bash
git clone git@github.com:WenderGustavo/product-user-manager.git
cd product-user-manager
2. Subir Banco de Dados com Docker
bash
Copiar código
docker-compose up -d
Isso inicializará o PostgreSQL na porta 5432.

3. Executar a Aplicação
bash
Copiar código
./mvnw spring-boot:run
4. Acessar a Documentação Swagger
Abra no navegador:

bash
Copiar código
http://localhost:8080/swagger-ui/index.html
🔗 Endpoints Principais
Usuários
GET /users: Lista todos os usuários.
POST /users: Cria um novo usuário.
PUT /users/{id}: Atualiza um usuário.
DELETE /users/{id}: Remove um usuário.
Produtos
GET /products: Lista todos os produtos.
POST /products: Cria um novo produto.
PUT /products/{id}: Atualiza um produto.
DELETE /products/{id}: Remove um produto.
🧪 Testes Unitários
Execute os testes unitários com:

bash
Copiar código
./mvnw test
📂 Estrutura do Projeto
bash
Copiar código
src/
├── main/
│   ├── java/
│   │   ├── domain/          # Entidades e regras de negócio
│   │   ├── application/     # Casos de uso e interfaces
│   │   ├── infrastructure/  # Controllers, segurança e gateways
│   │   ├── config/          # Configurações gerais
│   └── resources/
│       ├── application.yml
│       └── db/migration/    # Migrações do Flyway
├── test/                    # Testes unitários
👤 Autor
Desenvolvido por Wender Gustavo.
📎 LinkedIn

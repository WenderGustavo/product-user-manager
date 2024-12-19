Projeto Backend com Spring Boot

Descrição

Este é um projeto de backend desenvolvido utilizando Spring Boot e Java 21. O sistema implementa recursos modernos como autenticação com Spring Security, Docker para conteinerização, banco de dados PostgreSQL com migrações gerenciadas pelo Flyway, além de testes unitários e um padrão de arquitetura bem estruturado seguindo a Clean Architecture.

Sobre

Este projeto implementa um sistema de gerenciamento de usuários e produtos com controle de acesso baseado em roles (ADMIN e USER), utilizando JWT para autenticação e autorização.

Funcionalidades Principais:
Registro de Usuários (/auth/register):

Permite cadastrar usuários com a role ADMIN ou USER.
Usuários ADMIN têm acesso completo às funcionalidades do sistema.
Usuários USER possuem acesso restrito.
Permissões:

ADMIN:
Criar, atualizar e excluir usuários.
Alterar a role de outros usuários (ex.: transformar um USER em ADMIN).
Gerenciar produtos (criar, atualizar e excluir).
USER:
Apenas listar produtos.
Autenticação:

Realizada via JWT.
O token deve ser enviado no cabeçalho das requisições para acessar as rotas protegidas.
Autorização:

Controlada por Spring Security.
As permissões são baseadas no role do usuário.
Exemplos de Cenários:
Um ADMIN pode criar outros usuários, gerenciar produtos e atualizar as permissões de um usuário.
Um USER pode visualizar os produtos disponíveis, mas não pode gerenciar usuários ou produtos.

Funcionalidades Principais:
Registro de Usuários (/auth/register):

Permite cadastrar usuários com a role ADMIN ou USER.
Usuários ADMIN têm acesso completo às funcionalidades do sistema.
Usuários USER possuem acesso restrito.
Permissões:

ADMIN:
Criar, atualizar e excluir usuários.
Alterar a role de outros usuários (ex.: transformar um USER em ADMIN).
Gerenciar produtos (criar, atualizar e excluir).
USER:
Apenas listar produtos.
Autenticação:

Realizada via JWT.
O token deve ser enviado no cabeçalho das requisições para acessar as rotas protegidas.
Autorização:

Controlada por Spring Security.
As permissões são baseadas no role do usuário.
Exemplos de Cenários:
Um ADMIN pode criar outros usuários, gerenciar produtos e atualizar as permissões de um usuário.
Um USER pode visualizar os produtos disponíveis, mas não pode gerenciar usuários ou produtos.

Tecnologias Utilizadas

Java 21: Versão mais recente do Java.

Spring Boot: Framework para desenvolvimento rápido de aplicações Java.

Spring Security: Implementação de segurança para controle de acesso e JWT.

Docker: Facilita a conteinerização da aplicação.

PostgreSQL: Banco de dados relacional.

Flyway: Gerenciamento de migrações do banco de dados.

JUnit: Testes unitários.

Logger (SLF4J): Geração de logs para monitoração e depuração.

Swagger: Documentação interativa dos endpoints REST.

Funcionalidades Implementadas

Autenticação: JWT implementado com Spring Security.

Migrações de Banco: Flyway para versões controladas.

Gestão de Exceções: Tratamento customizado de erros.

Testes Unitários: Cobertura de testes com JUnit e Mockito.

Logs: Geração de logs estruturados usando SLF4J e Logback.

Documentação com Swagger: Interface interativa para testar e visualizar endpoints.

Documentação da API

A documentação dos endpoints REST está disponível em:

JSON OpenAPI Specification: http://localhost:8080/v3/api-docs

Interface Swagger: http://localhost:8080/swagger-ui/index.html

Como acessar:

Inicie o projeto conforme descrito em Como Executar o Projeto.

Abra o navegador e acesse o endereço da interface Swagger para visualizar e testar os endpoints.

Exemplos de Endpoints disponíveis:

/auth/register: Cadastro de novos usuários.

/auth/login: Login e geração de tokens JWT.

/users: Gerenciamento de usuários.

/products: Gerenciamento de produtos.

Como Executar o Projeto

1. Clonar o Repositório

git clone git@github.com:WenderGustavo/product-user-manager.git
cd product-user-manager

2. Subir Banco de Dados com Docker

Certifique-se de ter Docker e Docker Compose instalados.

docker-compose up -d

Isso subirá o PostgreSQL na porta 5432.

3. Configurar o Banco de Dados

Flyway realiza a configuração automaticamente ao rodar a aplicação pela primeira vez.

4. Executar a Aplicação

./mvnw spring-boot:run

5. Acessar a Documentação com Swagger

Acesse o Swagger UI em http://localhost:8080/swagger-ui/index.html para visualizar e testar os endpoints REST.

Autor

Wender GustavoLinkedIn: www.linkedin.com/in/wender-gustavo-110343230


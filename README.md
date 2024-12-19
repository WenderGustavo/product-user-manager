## Descrição
Este é um projeto de backend desenvolvido utilizando **Spring Boot** e **Java 21**. O sistema implementa recursos modernos como autenticação com **Spring Security**, **Docker** para conteinerização, banco de dados **PostgreSQL** com migrações gerenciadas pelo **Flyway**, além de testes unitários e um padrão de arquitetura bem estruturado seguindo a **Clean Architecture**.

## Sobre
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


## Tecnologias Utilizadas
- [**Java 21**](https://openjdk.org/projects/jdk/21/): Versão mais recente do Java.
- [**Spring Boot**](https://spring.io/projects/spring-boot): Framework para desenvolvimento rápido de aplicações Java.
- [**Spring Security**](https://spring.io/projects/spring-security): Implementação de segurança para controle de acesso e JWT.
- [**Docker**](https://www.docker.com/): Facilita a conteinerização da aplicação.
- [**PostgreSQL**](https://www.postgresql.org/): Banco de dados relacional.
- [**Flyway**](https://flywaydb.org/): Gerenciamento de migrações do banco de dados.
- [**JUnit**](https://junit.org/junit5/): Testes unitários.
- [**Logger (SLF4J)**](http://www.slf4j.org/): Geração de logs para monitoração e depuração.
- [**Swagger**](https://swagger.io/): Documentação interativa dos endpoints REST.

---

## Funcionalidades Implementadas
- **Autenticação**: JWT implementado com Spring Security.
- **Migrações de Banco**: Flyway para versões controladas.
- **Gestão de Exceções**: Tratamento customizado de erros.
- **Testes Unitários**: Cobertura de testes com JUnit e Mockito.
- **Logs**: Geração de logs estruturados usando SLF4J e Logback.
- **Documentação com Swagger**: Interface interativa para testar e visualizar endpoints.

---

## Clean Architecture
O projeto segue os princípios da **Clean Architecture**, garantindo que a estrutura do código seja modular, testável e de fácil manutenção. A arquitetura está organizada da seguinte forma:

1. **Domain**: Entidades e regras de negócio:
   - domain/user: Domínio relacionado ao usuário.
   - domain/product: Domínio relacionado ao produto.

2. **Application**: Contém a lógica de aplicação e interfaces:
   - **Interfaces**:
     - PasswordEncoderGateway
     - ProductGateway
     - UserGateway
   - **Use Cases**:
     - UserInteractor
     - UserRegistrationService
     - ProductInteractor

3. **Infrastructure**: Implementação das ferramentas externas:
   - **Controllers**:
     - controllers/product: Contém os controllers e DTOs de produto.
     - controllers/user: Contém os controllers e DTOs de usuário.
   - **Gateways**:
     - gateways/impl: Implementações das interfaces.
     - gateways/mapper: Mapeadores de entidades.
   - **Repositories**:
     - repositories/product: Contém a entidade e o repository de produto.
     - repositories/user: Contém a entidade e o repository de usuário.
   - **Security**:
     - SecurityConfigurations
     - TokenService
     - security/filter: Contém os filtros de segurança.
   - **Services**:
     - AuthorizationService

4. **Configurações**:
   - OpenApiConfig: Configuração do Swagger.
   - ProductConfig
   - UserConfig

5. **Exceções**:
   - exception: Tratamento e personalização de exceções.

---

## Como Executar o Projeto

### 1. Clonar o Repositório
bash
git clone git@github.com:WenderGustavo/product-user-manager.git
cd product-user-manager


### 2. Subir Banco de Dados com Docker
Certifique-se de ter **Docker** e **Docker Compose** instalados.

bash
docker-compose up -d


Isso subirá o PostgreSQL na porta **5432**.

### 3. Configurar o Banco de Dados
- **Flyway** realiza a configuração automaticamente ao rodar a aplicação pela primeira vez.

### 4. Executar a Aplicação
bash
./mvnw spring-boot:run


### 5. Acessar a Documentação com Swagger
A documentação estará disponível em:
bash
http://localhost:8080/swagger-ui/index.html


### 6. Passo a Passo para Testar pelo Swagger
1. **Registrar um Usuário**:
   - No Swagger, vá até o endpoint POST /auth/register.
   - Clique em **Try it out**.
   - Preencha os campos do corpo da requisição no seguinte formato JSON:
   
json
   {
     "username": "admin_user",
     "password": "password123",
     "email": "admin@example.com",
     "role": "ADMIN"
   }

   - Clique em **Execute** para registrar o usuário.

2. **Fazer Login**:
   - No Swagger, vá até o endpoint POST /auth/login.
   - Clique em **Try it out**.
   - Preencha os campos username e password no seguinte formato JSON:
   
json
   {
     "username": "admin_user",
     "password": "password123"
   }

   - Clique em **Execute** e copie o valor do campo accessToken retornado na resposta.

3. **Autenticar no Swagger**:
   - No canto superior direito da interface do Swagger, clique em **Authorize**.
   - Insira o token JWT no formato Bearer <seu_token_jwt>.
   - Clique em **Authorize** para autenticar.

4. **Testar os Endpoints**:
   - Após autenticar, vá até o endpoint desejado, clique em **Try it out** e execute as requisições. Por exemplo:
     - GET /users para listar usuários.
     - POST /products para criar um produto.



---

## Endpoints Principais
Os exemplos abaixo são diretamente testáveis pelo Swagger após seguir o passo a passo acima.

#### **GET /users** - Lista todos os usuários
- No Swagger, clique no endpoint GET /users, clique em **Try it out** e depois em **Execute**.

#### **POST /users** - Cria um novo usuário
- No Swagger, vá até POST /users, clique em **Try it out**, preencha os campos no seguinte formato JSON:
  
json
  {
    "username": "new_user",
    "password": "password123",
    "email": "new_user@example.com",
    "role": "USER"
  }

  - Clique em **Execute**.

#### **PUT /users/{id}** - Atualiza um usuário existente
- No Swagger, selecione o endpoint PUT /users/{id}.
- Clique em **Try it out**, insira o id do usuário na URL e preencha os campos no seguinte formato JSON:
  
json
  {
    "username": "updated_user",
    "email": "updated_user@example.com",
    "role": "ADMIN"
  }

  - Clique em **Execute**.

#### **DELETE /users/{id}** - Remove um usuário
- Vá até o endpoint DELETE /users/{id}, clique em **Try it out**, insira o id do usuário e clique em **Execute**.

#### **GET /products** - Lista todos os produtos
- Clique em GET /products, clique em **Try it out** e depois em **Execute**.

#### **POST /products** - Cria um novo produto
- Vá até POST /products, clique em **Try it out**, preencha os campos no seguinte formato JSON:
  
json
  {
    "name": "Produto A",
    "price": 99.99
  }

  - Clique em **Execute**.

#### **PUT /products/{id}** - Atualiza um produto existente
- Selecione o endpoint PUT /products/{id}, clique em **Try it out**, insira o id do produto e preencha os campos no seguinte formato JSON:
  
json
  {
    "name": "Produto B",
    "price": 89.99
  }

  - Clique em **Execute**.

#### **DELETE /products/{id}** - Remove um produto
- Vá até o endpoint DELETE /products/{id}, clique em **Try it out**, insira o id do produto e clique em **Execute**.

---

### 7. Testes Unitários
Para executar os testes unitários:
bash
./mvnw test


## Logs
Os logs são gerados na pasta /logs, separados por nível de log (INFO, DEBUG, ERROR).

---

## Estrutura do Projeto
bash
src/
|-- main/
|   |-- java/
|   |   |-- domain/
|   |   |   |-- user/           # Domínio do usuário
|   |   |   |-- product/        # Domínio do produto
|   |   |-- application/
|   |   |   |-- interfaces/     # Interfaces (Gateways)
|   |   |   |-- usecases/       # Casos de uso
|   |   |-- infrastructure/
|   |   |   |-- controllers/
|   |   |   |   |-- product/    # Controllers e DTOs de produto
|   |   |   |   |-- user/       # Controllers e DTOs de usuário
|   |   |   |-- gateways/
|   |   |   |   |-- impl/       # Implementações dos Gateways
|   |   |   |   |-- mapper/     # Mapeadores
|   |   |   |-- repositories/
|   |   |   |   |-- product/    # Entidade e Repository de produto
|   |   |   |   |-- user/       # Entidade e Repository de usuário
|   |   |   |-- security/
|   |   |   |   |-- filter/     # Filtros de segurança
|   |   |   |   |-- SecurityConfigurations
|   |   |   |   |-- TokenService
|   |   |   |-- services/       # Serviços
|   |   |-- config/
|   |   |   |-- OpenApiConfig   # Configuração do Swagger
|   |   |   |-- ProductConfig
|   |   |   |-- UserConfig
|   |-- resources/
|       |-- application.yml
|       |-- db/
|           |-- migration/
|               |-- V1__init.sql
|-- test/
    |-- java/
        |-- <tests>


---

## Autor
- **Wender Gustavo**  
  LinkedIn: [www.linkedin.com/in/wender-gustavo-110343230](https://www.linkedin.com/in/wender-gustavo-110343230/)


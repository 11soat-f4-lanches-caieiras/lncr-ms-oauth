# lncr-ms-oauth

## Descrição

Microserviço responsável pelo gerenciamento de **Autenticação e Autorização (OAuth)** no sistema Lanches Caieiras. Este serviço implementa as funcionalidades relacionadas à geração e validação de tokens de acesso, controle de escopos de permissões, e gerenciamento de credenciais de clientes e aplicações.

## Funcionalidades

### Endpoints Disponíveis (`/oauth`)

| Método | Path | Descrição |
|--------|------|-----------|
| `POST` | `/oauth/token` | Gerar token de acesso |

**Headers obrigatórios:**
- `Authorization`: Credenciais codificadas em Base64 (Basic Auth)

**Body (JSON):**
- `scope`: Escopo de acesso solicitado (ex: "customer", "admin")
- Credenciais adicionais conforme o escopo

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.5
- PostgreSQL
- Maven
- Cucumber (BDD)
- JUnit 5
- JWT (JSON Web Tokens)
- Auth0 Java JWT

## Sonar Quality Gate

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=11soat-f4-lanches-caieiras_lncr-ms-oauth&metric=alert_status&token=51814d63573f0038cd74b9e5aaf21e1f4f0e62ff)](https://sonarcloud.io/summary/new_code?id=11soat-f4-lanches-caieiras_lncr-ms-oauth)

Acesse o dashboard completo: [SonarCloud - lncr-ms-oauth](https://sonarcloud.io/project/overview?id=11soat-f4-lanches-caieiras_lncr-ms-oauth)

## Dependências

- **lncr-core** (versão 3.0) - Biblioteca com regras de negócio e entidades de domínio
- **lncr-commons** (versão 1.0) - Biblioteca comum compartilhada com configurações e utilitários

## Guia de Download e Execução

### Pré-requisitos

- **Java 21** instalado
- **Maven 3.8+** instalado
- **Git** instalado

### Variáveis de Ambiente

Crie um arquivo `.env` ou configure as seguintes variáveis de ambiente:

```bash
# Configuração do Servidor
SERVER_PORT=8080

# URLs da Aplicação
LNCR_INTERNAL_URL=http://localhost:8080

# OAuth Configurações
LNCR_OAUTH_SECRET_KEY=your_secret_key_min_256_bits

# OAuth Profile: Admin
LNCR_OAUTH_ADMIN_CLIENT_ID=admin_client_id
LNCR_OAUTH_ADMIN_CLIENT_SECRET=admin_client_secret

# OAuth Profile: Monitor
LNCR_OAUTH_MONITOR_CLIENT_ID=monitor_client_id
LNCR_OAUTH_MONITOR_CLIENT_SECRET=monitor_client_secret

# OAuth Profile: Totem (Self Service Terminal)
LNCR_OAUTH_TOTEM_CLIENT_ID=totem_client_id
LNCR_OAUTH_TOTEM_CLIENT_SECRET=totem_client_secret
```

### Download e Instalação

```bash
# Clone o repositório
git clone https://github.com/11soat-f4-lanches-caieiras/lncr-ms-oauth.git

# Entre no diretório do projeto
cd lncr-ms-oauth/oauth

# Configure o GitHub Packages (necessário para dependências lncr-core e lncr-commons)
# Crie o arquivo ~/.m2/settings.xml com suas credenciais do GitHub

# Compile o projeto
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

### Executando com Docker

```bash
# Build da imagem
docker build -t lncr-ms-oauth:latest .

# Execute o container
docker run -p 8080:8080 \
  -e LNCR_INTERNAL_URL=http://localhost:8080 \
  -e LNCR_OAUTH_SECRET_KEY=your_secret_key_min_256_bits \
  -e LNCR_OAUTH_ADMIN_CLIENT_ID=admin_client_id \
  -e LNCR_OAUTH_ADMIN_CLIENT_SECRET=admin_client_secret \
  lncr-ms-oauth:latest
```

### Executando os Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com cobertura
mvn test -Pcoverage

# Executar apenas testes BDD
mvn test -Dcucumber.filter.tags="@bdd"
```

### Verificando a Aplicação

Após iniciar a aplicação, acesse:

- **Health Check**: http://localhost:8080/actuator/health
- **API Base**: http://localhost:8080/oauth

### Gerando Token de Acesso

```bash
# Exemplo de requisição para gerar token (Admin)
curl -X POST http://localhost:8080/oauth/token \
  -H "Authorization: Basic $(echo -n 'admin_client_id:admin_client_secret' | base64)" \
  -H "Content-Type: application/json" \
  -d '{"scope": "admin"}'
```

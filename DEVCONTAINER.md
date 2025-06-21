# DevContainer - Template Java Monolítico

Este projeto está configurado para rodar com DevContainer, fornecendo um ambiente de desenvolvimento isolado e consistente.

## Pré-requisitos

- Docker Desktop instalado e rodando
- VS Code com a extensão "Dev Containers" instalada
- Git

## Como usar

### 1. Abrir o projeto no DevContainer

1. Clone o repositório
2. Abra o projeto no VS Code
3. Quando solicitado, clique em "Reopen in Container" ou use `Ctrl+Shift+P` e selecione "Dev Containers: Reopen in Container"

### 2. Configuração do ambiente

O DevContainer já está configurado com:
- Java 21
- Maven
- PostgreSQL 15
- Todas as extensões necessárias para desenvolvimento Java

### 3. Executar o projeto

Dentro do container, você pode executar:

```bash
# Compilar o projeto
mvn clean compile

# Executar os testes
mvn test

# Executar a aplicação
mvn spring-boot:run
```

### 4. Acessar a aplicação

- **Aplicação**: http://localhost:8080
- **Banco de dados**: localhost:5432
  - Database: template
  - Username: template
  - Password: template

### 5. Debug

A aplicação está configurada para debug na porta 5005. Você pode:
1. Configurar breakpoints no código
2. Executar `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"`
3. Iniciar o debug no VS Code

## Estrutura do DevContainer

- `.devcontainer/devcontainer.json`: Configuração principal do DevContainer
- `Dockerfile`: Imagem base com Java 21 e Maven
- `docker-compose.yml`: Orquestração dos serviços (app + PostgreSQL)

## Volumes

- `.:/workspaces/template:cached`: Código fonte
- `maven-cache:/root/.m2`: Cache do Maven
- `pgdata_template:/var/lib/postgresql/data`: Dados do PostgreSQL

## Troubleshooting

### Problemas comuns

1. **Container não inicia**: Verifique se o Docker está rodando
2. **Porta já em uso**: Pare outros containers que possam estar usando as portas 8080, 5432 ou 5005
3. **Problemas de permissão**: O container roda como usuário `vscode`

### Comandos úteis

```bash
# Reconstruir o container
Ctrl+Shift+P -> "Dev Containers: Rebuild Container"

# Ver logs do container
docker-compose logs app

# Acessar o container via terminal
docker exec -it template_app bash
```

## Configurações específicas

- **Perfil Spring**: `local`
- **Porta da aplicação**: 8080
- **Porta do banco**: 5432
- **Porta de debug**: 5005 
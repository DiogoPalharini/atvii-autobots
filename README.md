# ATVII AutoBots - Sistema de Gestão para Lojas Veiculares

Bem-vindo ao **ATVII AutoBots**, um sistema desenvolvido para gerenciar lojas especializadas em manutenção de veículos e vendas de autopeças. Este projeto segue a arquitetura de micro-serviços e está alinhado ao modelo de maturidade de Richardson (RMM).

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 2.6.4**
- **Hibernate/JPA** (Persistência de Dados)
- **Spring HATEOAS** (HATEOAS Suporte)
- **Maven** (Gerenciador de Dependências)
- **H2 Database** (Banco de Dados para Testes)
- **Lombok** (Simplificação de Código)

---

## 📋 Funcionalidades

### **Gerenciamento de Clientes**
- **Cadastro**: Adicionar novos clientes com documentos, endereço e telefones.
- **Consulta**: Obter dados de clientes por ID ou listar todos os clientes.
- **Atualização**: Modificar dados de clientes existentes.
- **Exclusão**: Remover clientes.

### **Níveis do RMM Implementados**
1. **Recursos**: Endpoints organizados por entidades.
2. **Verb HTTP**: Uso correto de `GET`, `POST`, `PUT`, e `DELETE`.
3. **HATEOAS**: Inclusão de links para facilitar a navegação na API.

---

## 📡 Endpoints da API

### **Cliente**
| Método  | Endpoint                  | Descrição                           |
|---------|---------------------------|-------------------------------------|
| `GET`   | `/clientes/{id}`          | Obter cliente por ID                |
| `GET`   | `/clientes`               | Listar todos os clientes            |
| `POST`  | `/clientes/cadastro`      | Cadastrar um novo cliente           |
| `PUT`   | `/clientes/atualizar`     | Atualizar dados de um cliente       |
| `DELETE`| `/clientes/excluir/{id}`  | Excluir cliente pelo ID             |

---

## ⚙️ Configuração do Ambiente

### **Pré-requisitos**
- **Java 17**
- **Maven**
- IDE (Eclipse, IntelliJ, etc.)

### **Como Executar**
1. Clone o repositório:
   ```bash
   git clone https://github.com/DiogoPalharini/atvii-autobots.git
   ```
Acesse a pasta do projeto:

```bash

cd atvii-autobots
```
Compile e rode o projeto:

```bash

mvn spring-boot:run
```
Acesse a aplicação em:

http://localhost:8080

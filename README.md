### Sistema de Conta Bancária

Este é um aplicativo Java que gerencia contas bancárias, permitindo a criação, edição, depósito e saque de fundos.

### Funcionalidades

- Criação de novas contas bancárias com informações do usuário.
- Edição das informações de uma conta existente.
- Realização de depósitos e saques nas contas.
- Listagem de todas as contas no banco de dados.

### Tecnologias Utilizadas

- Linguagem de Programação: Java
- Framework: Spring Boot
- Banco de Dados: Banco de Dados Relacional (ex: MySQL, PostgreSQL)
- Ferramentas: Postman (para testar as requisições)
- Gerenciamento de Dependências: Maven

### Estrutura do Projeto

- `src/main/java/com/factory/contabancaria`: Contém os principais pacotes do projeto.
    - `controller`: Contém as classes de controle para as requisições REST.
    - `model`: Contém as classes de modelo (entidades) do projeto.
    - `dto`: Contém as classes de DTO que contém os dados a serem transferidos, sem lógica de negócios ou comportamento complexo.
    - `model/factory`: Contém a classe `ContaFactory` para criar instâncias de `CalculoConta`.
    - `repository`: Contém a interface `ContasRepository` para interações com o banco de dados.
    - `service`: Contém a classe `ContasService` para a lógica de negócios.
- `src/main/resources`: Contém os arquivos de configuração.

### Endpoints

- `GET /api/contas`: Lista todas as contas no banco de dados.
- `GET /api/contas/{id}`: Retorna os detalhes de uma conta específica.
- `POST /api/contas`: Cria uma nova conta com informações do usuário.
- `PUT /api/contas/{id}`: Edita as informações de uma conta existente.
- `PUT /api/contas/{id}/deposito`: Realiza um depósito em uma conta.
- `PUT /api/contas/{id}/saque`: Realiza um saque de uma conta.

### Executando o Projeto

1. Clone o repositório.
2. Configure as informações do banco de dados no arquivo `application.properties`.
3. Acesse os endpoints usando o Postman ou outro cliente HTTP.

### Considerações Finais

Esta documentação fornece um resumo das funcionalidades e estrutura do Sistema Bancário básico.

É recomendado analisar o código-fonte para obter detalhes completos sobre as implementações ou até incremento de novas funcionalidades e melhorias.

Certifique-se de ajustar, expandir, alterar estes códigos do repositório de acordo com as necessidades específicas do seu projeto.


###### FIM
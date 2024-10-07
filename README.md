# Criação de Usuário (First Decision)

## Descrição Geral
Projeto (desafio First Decision) com frontend e backend que realiza o cadastro, remove e lista os usuários.

## Tecnologias
Angular (Frontend)
Java 17 com Spring Boot 3 (Backend)
PostgreSQL (Banco de Dados)

## Funcionalidades
* [Cadastro]Cadastra o usuário com validação dos campos no frontend e backend.
* [Salvar_banco]Persistência dos dados no banco de dados.
* [Seguranca_senha]Senha persistida criptografada no banco.
* [Table]Visualização dos registros já cadastrados no front (table)
* [Remover]Opção de remover um registro
* [Detalhar]Opção de detalhar um registro
* [Docker]Ambiente dockerizado
* [Testes]Testes unitários nas principais funcionalidades do projeto (Front/Back)

## Como rodar o projeto

### Faça o clone
Faça o clone do projeto. No terminal rode o comando abaixo:
>git clone https://github.com/jeffersonfreitas-dev/desafio.first-decision.git


### Entre na pasta do projeto
>cd desafio.first-decision

### Rode o Docker-compose
Lembre de adicionar o 'sudo' caso não tenha permissão de rodar o docker.
> docker-compose build && docker-compose

## Imagens

### Tela de cadastro e listagem.
![Home](./images/home.png)
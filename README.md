# Cadastro de Endereço

## Descrição da Solução
Esta aplicação é uma API desenvolvida em **Java com Spring Boot** responsável por receber requisições do cliente, processar as regras de negócio, consumir uma **API externa** para enriquecer os dados e cadastrar os dados do endereço no banco de dados.

## Desenho da Solução
A arquitetura e o fluxo de integração entre a nossa aplicação e o serviço externo funcionam da seguinte forma:

Esse código gera um **fluxograma** simples com caixas de decisão e setas de fluxo.

## Tecnologias Utilizadas
- **Java 17+**
- **Spring Boot**
- **Spring Web** (`RestClient` para consumo da API externa)
- **Maven**

## Como Executar
1. Clone o repositório:
   `git clone url-do-seu-repositorio`
2. Configure a URL da API externa no arquivo `application.properties` ou `application.yml`.
3. Execute o projeto via Maven:
   `mvn spring-boot:run`
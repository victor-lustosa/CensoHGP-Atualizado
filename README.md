# 🏥 Censo HGP - Sistema de Gestão de Pacientes

Atualização de um sistema Java 8 para java 17, desenvolvido com **Spring Boot** para gerenciar informações do censo hospitalar, incluindo pacientes, departamentos, procedimentos e controle de acesso de usuários.

## 📦 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.x
- Spring Security (JWT)
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Jakarta Validation
- API RESTful

## 📁 Estrutura do Projeto

```text

br.com.unitins.censohgp
│
├── configs # Configurações de segurança e filtros JWT
├── controllers # Endpoints REST
├── dtos # DTOs (entrada e saída de dados)
├── exceptions # Exceções personalizadas
├── models # Entidades JPA
├── repositories # Interfaces do Spring Data JPA
├── services # Regras de negócio
├── utils # Utilitários
└── CensohgpApplication # Classe principal da aplicação

```

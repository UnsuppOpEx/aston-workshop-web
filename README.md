# aston-workshop-web

## Task 1

### Описание

1. Необходимо написать собственную реализацию HashMap. Обязательные методы: get, put, remove.

## Task 2

### Описание

1. Разработать консольное приложение(user-service) на Java, использующее Hibernate для взаимодействия с PostgreSQL, без
   использования Spring.
2. Приложение должно поддерживать базовые операции CRUD (Create, Read, Update, Delete) над сущностью User.

#### Требования

- Использовать Hibernate в качестве ORM. База данных — PostgreSQL.
- Настроить Hibernate без Spring, используя hibernate.cfg.xml или properties-файл.
- Реализовать CRUD-операции для сущности User (создание, чтение, обновление, удаление), которая состоит из полей: id,
  name, email, age, created_at.
- Использовать консольный интерфейс для взаимодействия с пользователем.(по согласованию заменён на Testcontainers)
- Использовать Maven для управления зависимостями. Настроить логирование.
- Настроить транзакционность для операций с базой данных.
- Использовать DAO-паттерн для отделения логики работы с БД.
- Обработать возможные исключения, связанные с Hibernate и PostgreSQL

#### Запуск тестов

> mvn -Dtest=aston.task2.** test

## Task 3

### Описание

1. Использовать JUnit 5, Mockito и Testcontainers.
2. Для тестирования DAO-слоя написать интеграционные тесты с использованием Testcontainers.
3. Для тестирования Service-слоя написать юнит-тесты с использованием Mockito.
4. Тесты должны быть изолированы друг от друга.

#### Запуск тестов

> mvn -Dtest=aston.task2.** test

## Task 4

### Описание

1. Использовать необходимые модули spring(boot, web, data etc).
2. Реализовать api для получения, создания, обновления и удаления юзера. Важно, entity не должен возвращаться из
   контроллера, необходимо использовать dto.
3. Заменить Hibernate на Spring data JPA.
4. Написать тесты для API(можно делать это при помощи mockMvc или других средств)

#### Поднять DB в docker перед запуском приложения

> docker-compose up -d

#### Запуск тестов

> mvn -Dtest=aston.** test

## Технологии

- Java 17
- Maven
- Junit
- Lombok
- Hibernate
- Testcontainers
- Flyway
- Spring boot
- Docker-compose
- Postgresql
- SpringDoc OpenAPI


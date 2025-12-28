create table users
(
    id         uuid primary key,
    name       varchar(255)        not null,
    email      varchar(255) unique not null,
    age        int,
    created_at timestamp           not null
);

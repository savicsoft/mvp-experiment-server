drop table if exists carpool_user;
create table carpool_user
(
    id                  BIGSERIAL PRIMARY KEY,
    uuid                uuid      not null,
    email               text      not null,
    password            text      not null,
    first_name          text      not null,
    last_name           text      not null,
    birth_date          timestamp not null,
    country             text      not null,
    city                text      not null,
    is_driver           boolean   not null,
    car_id              bigint    not null,
    user_preferences_id bigint    not null
);

create table car
(
    id   BIGSERIAL PRIMARY KEY,
    uuid uuid not null
);

create table user_preferences
(
    id            BIGSERIAL PRIMARY KEY,
    language      text not null,
    music         text not null,
    smoking       text not null,
    communication text not null
);

alter table user_preferences
    alter column language drop not null,
    alter column music drop not null,
    alter column smoking drop not null,
    alter column communication drop not null;


alter table car
    alter column user_id type bigint,
    alter column user_id set not null,
    drop column picture_url;

alter table carpool_user
    add column picture_url text,
    alter column first_name drop not null,
    alter column last_name drop not null,
    alter column birth_date drop not null,
    alter column country drop not null,
    alter column city drop not null,
    alter column is_driver drop not null,
    alter column phone_number drop not null,
    alter column user_preferences_id drop not null,
    drop column role;

drop type role;

drop table car_picture_url;
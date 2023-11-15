drop table if exists car_picture_url;
drop table if exists review;
drop table if exists rating;
alter table car drop constraint car_pkey;
alter table car drop constraint car_user_id_fkey;
alter table carpool_user drop constraint  carpool_user_pkey;
alter table carpool_user drop constraint carpool_user_user_preferences_id_fkey;
alter table user_preferences drop constraint  user_preferences_pkey;
alter table car drop column id;
alter table carpool_user drop column id;
drop table user_preferences;


create table user_preferences
(
    id            uuid PRIMARY KEY,
    language      text not null,
    music         text not null,
    smoking       text not null,
    communication text not null
);

alter table car rename column uuid to id;
alter table carpool_user rename column uuid to id;
alter table carpool_user drop column  user_preferences_id;
alter table carpool_user add column user_preferences_id uuid not null ;
alter table car drop column user_id;
alter table car add column user_id uuid not null ;



alter table carpool_user add primary key(id);
alter table car add primary key (id);
alter table carpool_user add foreign key (user_preferences_id) references user_preferences(id);
alter table car add foreign key (user_id) references carpool_user(id);




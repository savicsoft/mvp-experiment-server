alter table carpool_user drop column picture_url;
alter table carpool_user add column has_picture boolean not null;
alter table carpool_user add column rating double precision not null;


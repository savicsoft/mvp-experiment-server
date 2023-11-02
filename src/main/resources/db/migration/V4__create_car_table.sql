DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS carpool_user;

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
    user_preferences_id bigint    not null,
    FOREIGN KEY (user_preferences_id) REFERENCES user_preferences(id)
);

CREATE TABLE car (
            id BIGSERIAL PRIMARY KEY,
            uuid UUID NOT NULL,
            user_id BIGINT,
            registration_number TEXT NOT NULL,
            color TEXT,
            year INT,
            fuel_type TEXT,
            picture_url TEXT,
            fuel_efficiency DOUBLE PRECISION,
            CONSTRAINT enum_fuel_type CHECK (fuel_type IN (
                        'GASOLINE', 'DIESEL', 'BIODIESEL', 'ELECTRICITY', 'ETHANOL',
                        'COMPRESSED_NATURAL_GAS', 'LIQUEFIED_NATURAL_GAS', 'PROPANE'
                 )),
            FOREIGN KEY (user_id) REFERENCES carpool_user(id)
);

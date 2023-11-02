DROP TABLE IF EXISTS car;

CREATE TABLE car (
     id BIGSERIAL PRIMARY KEY,
     uuid UUID NOT NULL,
     user_id BIGINT,
     registration_number TEXT NOT NULL,
     color TEXT,
     year INT,
     fuel_type TEXT,
     fuel_efficiency DOUBLE PRECISION,
     CONSTRAINT enum_fuel_type CHECK (fuel_type IN (
                'GASOLINE', 'DIESEL', 'BIODIESEL', 'ELECTRICITY', 'ETHANOL',
                'COMPRESSED_NATURAL_GAS', 'LIQUEFIED_NATURAL_GAS', 'PROPANE'
         )),
     FOREIGN KEY (user_id) REFERENCES carpool_user(id)
);

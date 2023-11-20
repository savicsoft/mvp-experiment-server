
create table driver_ride(
    id uuid not null,
    driver_id uuid not null ,
    car_id uuid not null ,
    starting_point text not null ,
    ending_point text not null ,
    passengers_limit smallint not null check (passengers_limit <= 8),
    starting_time timestamptz not null ,
    ending_time timestamptz null,
    PRIMARY KEY (id),
    FOREIGN KEY (driver_id) REFERENCES carpool_user(id),
    FOREIGN KEY (car_id) REFERENCES car(id)

);

CREATE TYPE ride_status_enum AS ENUM ('Requested', 'Rejected', 'Accepted',
    'InProgress', 'PendingPayment', 'PassengerNoShow', 'Completed');
create table passenger_ride(
    id uuid not null,
    passenger_id uuid not null ,
    driver_ride_id uuid not null ,
    starting_point text not null ,
    ending_point text not null ,
    pick_up_time timestamptz not null ,
    ride_price numeric(6,2) not null,
    ride_status ride_status_enum not null ,
    PRIMARY KEY (id),
    FOREIGN KEY (passenger_id) REFERENCES carpool_user(id),
    FOREIGN KEY (driver_ride_id) REFERENCES driver_ride(id)
)
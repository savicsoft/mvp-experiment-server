create table carpool_user(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    email VARCHAR(50) NOT NULL
)

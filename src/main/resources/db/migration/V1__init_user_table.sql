create table carpool_user(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL
)


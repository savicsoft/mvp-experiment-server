CREATE TABLE rating (
        id BIGINT PRIMARY KEY,
        uuid UUID NOT NULL,
        user_id BIGINT NOT NULL,
        rating DOUBLE PRECISION NOT NULL,
        comment TEXT,
        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        anonymous BOOLEAN,
        rating_user_id BIGINT NOT NULL,
        rating_type TEXT,
        FOREIGN KEY (user_id) REFERENCES carpool_user(id),
        FOREIGN KEY (rating_user_id) REFERENCES carpool_user(id)
);

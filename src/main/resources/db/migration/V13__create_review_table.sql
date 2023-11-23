CREATE TABLE review
(
    id            UUID PRIMARY KEY,
    rated_user_id UUID             NOT NULL,
    rating        DOUBLE PRECISION NOT NULL,
    comment       TEXT,
    date          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    anonymous     BOOLEAN,
    rater_user_id UUID             NOT NULL,
    review_type   TEXT,
    FOREIGN KEY (rated_user_id) REFERENCES carpool_user (id),
    FOREIGN KEY (rater_user_id) REFERENCES carpool_user (id)
);

CREATE TABLE review
(
    id            BIGSERIAL PRIMARY KEY,
    uuid          UUID             NOT NULL,
    rated_user_id BIGINT           NOT NULL,
    rating        DOUBLE PRECISION NOT NULL,
    comment       TEXT,
    date          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    anonymous     BOOLEAN,
    rater_user_id BIGINT           NOT NULL,
    review_type   TEXT,
    FOREIGN KEY (rated_user_id) REFERENCES carpool_user (id),
    FOREIGN KEY (rater_user_id) REFERENCES carpool_user (id)
);

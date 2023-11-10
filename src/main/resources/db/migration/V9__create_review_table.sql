create table review(
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    user_id BIGINT NOT NULL,
    description TEXT NOT NULL,
    rating TEXT NOT NULL,
    CONSTRAINT enum_rating CHECK (rating IN (
                                       'VERY_BAD', 'BAD', 'OKAY', 'GOOD', 'EXCELLENT'
        )),
    FOREIGN KEY (user_id) REFERENCES carpool_user(id)
);

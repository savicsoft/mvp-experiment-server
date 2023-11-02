-- Create the new table to store picture URLs
CREATE TABLE car_picture_url (
                 id BIGSERIAL PRIMARY KEY,
                 car_id BIGINT REFERENCES car(id),
                 picture_url TEXT
);

-- Add a new column in the car table to reference car_picture_url
ALTER TABLE car ADD COLUMN picture_url_updated BIGINT;
UPDATE car SET picture_url_updated = (SELECT id FROM car_picture_url WHERE car_id = car.id);
ALTER TABLE car DROP COLUMN picture_url;

-- Rename the newly added column to picture_url
ALTER TABLE car RENAME COLUMN picture_url_updated TO picture_url;

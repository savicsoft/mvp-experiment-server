ALTER TABLE carpool_user
ALTER COLUMN birth_date SET DATA TYPE DATE;
ALTER TABLE carpool_user
RENAME COLUMN tel TO phone_number;
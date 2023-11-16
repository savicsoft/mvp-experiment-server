ALTER TABLE carpool_user ALTER COLUMN birth_date TYPE date USING birth_date::date;
-- making non essential fields in carpool_user nullable
ALTER TABLE carpool_user ALTER COLUMN first_name DROP NOT NULL;
ALTER TABLE carpool_user ALTER COLUMN last_name DROP NOT NULL;
ALTER TABLE carpool_user ALTER COLUMN country DROP NOT NULL;
ALTER TABLE carpool_user ALTER COLUMN city DROP NOT NULL;
ALTER TABLE carpool_user ALTER COLUMN is_driver DROP NOT NULL;
-- making user_preferences fields nullable
ALTER TABLE user_preferences ALTER COLUMN language DROP NOT NULL;
ALTER TABLE user_preferences ALTER COLUMN music DROP NOT NULL;
ALTER TABLE user_preferences ALTER COLUMN smoking DROP NOT NULL;
ALTER TABLE user_preferences ALTER COLUMN communication DROP NOT NULL;
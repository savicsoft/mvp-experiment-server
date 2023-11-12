ALTER TABLE carpool_user ALTER COLUMN birth_date TYPE date USING birth_date::date;
ALTER TABLE user_preferences ALTER COLUMN language SET DEFAULT 'No language';
ALTER TABLE user_preferences ALTER COLUMN music SET DEFAULT 'No music';
ALTER TABLE user_preferences ALTER COLUMN smoking SET DEFAULT 'No smoking';
ALTER TABLE user_preferences ALTER COLUMN communication SET DEFAULT 'No communication';
-- Add the 'role' column as an ENUM of strings to the 'carpool_user' table
ALTER TABLE carpool_user ADD COLUMN role text;

-- Populate the 'role' column with a default value, for example 'USER'
UPDATE carpool_user SET role = 'USER';

-- Create an ENUM type 'role' with allowed values
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'role') THEN
    CREATE TYPE role AS ENUM ('USER', 'ADMIN');
  END IF;
END $$;
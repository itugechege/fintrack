ALTER TABLE users
ALTER COLUMN preferred_currency TYPE varchar(3)
USING preferred_currency::varchar(3);
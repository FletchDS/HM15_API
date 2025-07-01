ALTER TABLE student
ADD CONSTRAINT age_check CHECK (age>=16);

ALTER TABLE student
ADD CONSTRAINT unique_name UNIQUE (name)

ALTER TABLE student
ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty
ADD CONSTRAINT unique_name_and_color UNIQUE(name, color);
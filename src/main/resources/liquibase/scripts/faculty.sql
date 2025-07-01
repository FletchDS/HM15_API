-- liquibase formatted sql

-- changeset vitaliy:1
CREATE TABLE faculty(
    id SERIAL,
	color VARCHAR(255),
	name VARCHAR(255),
    PRIMARY KEY (id)
)

-- changeset vitaliy:2
CREATE INDEX student_nc_index ON faculty(name, color)
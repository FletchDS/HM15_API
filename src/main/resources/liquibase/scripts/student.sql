-- liquibase formatted sql

-- changeset vitaliy:3
CREATE TABLE student(
    id SERIAL,
	age INT,
	name VARCHAR(255),
    faculty_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(id)
)

-- changeset vitaliy:4
CREATE INDEX student_name_index ON student(name)
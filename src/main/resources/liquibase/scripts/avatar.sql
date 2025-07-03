-- liquibase formatted sql

-- changeset vitaliy:5
CREATE TABLE avatar(
    id SERIAL,
	file_path VARCHAR(255),
	file_size BIGINT,
    media_type VARCHAR(255),
    data BYTEA,
    student_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES student(id)
)
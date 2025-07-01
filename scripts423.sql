SELECT student."name", student.age, faculty."name" 
FROM student
INNER JOIN faculty ON faculty_id = faculty.id;

SELECT student."name", student.age, avatar."data", avatar.file_path, avatar.file_size, avatar.media_type
FROM  student 
INNER JOIN avatar ON student.id = avatar.student_id 
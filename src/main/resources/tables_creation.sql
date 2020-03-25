CREATE TABLE lesson_type(
lesson_type_name VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY
);
CREATE TABLE time_slot(
time_slot_name VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY
);
CREATE TABLE building( 
building_id SERIAL PRIMARY KEY,
building_name VARCHAR(50) NOT NULL UNIQUE,
building_address VARCHAR(200) NOT NULL UNIQUE
);
CREATE TABLE auditorium(
auditorium_id SERIAL PRIMARY KEY,
auditorium_number INTEGER NOT NULL,
capacity INTEGER NOT NULL,
building_id INTEGER REFERENCES building(building_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE faculty(
faculty_id SERIAL PRIMARY KEY,
faculty_name VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE subject(
subject_id SERIAL PRIMARY KEY,
subject_name VARCHAR(30) NOT NULL UNIQUE,
faculty_id INTEGER REFERENCES faculty(faculty_id)
);
CREATE TABLE person(
person_id SERIAL PRIMARY KEY,
person_first_name VARCHAR(20) NOT NULL,
person_last_name VARCHAR(30) NOT NULL
);
CREATE TABLE teacher(
teacher_id INTEGER REFERENCES person(person_id) ON UPDATE CASCADE ON DELETE CASCADE PRIMARY KEY,
faculty_id INTEGER REFERENCES faculty(faculty_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE groups(
group_id SERIAL PRIMARY KEY,
group_name VARCHAR(10) NOT NULL UNIQUE
);
CREATE TABLE student(
student_id INTEGER REFERENCES person(person_id) ON UPDATE CASCADE ON DELETE CASCADE PRIMARY KEY,
group_id INTEGER REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE TABLE lesson(
lesson_id SERIAL PRIMARY KEY,
subject_id INTEGER REFERENCES subject(subject_id) ON UPDATE CASCADE ON DELETE CASCADE,
lesson_type_name VARCHAR(20) REFERENCES lesson_type(lesson_type_name) ON UPDATE CASCADE ON DELETE CASCADE,
auditorium_id INTEGER REFERENCES auditorium(auditorium_id) ON UPDATE CASCADE ON DELETE CASCADE,
date DATE,
time_slot_name VARCHAR(20) REFERENCES time_slot(time_slot_name) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE teacher_subject(
teacher_id INTEGER REFERENCES teacher(teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
subject_id INTEGER REFERENCES subject(subject_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE lesson_teacher(
teacher_id INTEGER REFERENCES teacher(teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
lesson_id INTEGER REFERENCES lesson(lesson_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE lesson_group(
lesson_id INTEGER REFERENCES lesson(lesson_id) ON UPDATE CASCADE ON DELETE CASCADE,
group_id INTEGER REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE
);
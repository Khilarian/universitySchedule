CREATE TABLE lesson_type(
lesson_type_id SERIAL PRIMARY KEY,
lesson_type_name VARCHAR(20) NOT NULL UNIQUE
);
CREATE TABLE time_slot(
time_slot_id SERIAL PRIMARY KEY,
time_slot_number INTEGER NOT NULL UNIQUE,
time_slot_name VARCHAR(20) NOT NULL UNIQUE,
time_slot_start TIME NOT NULL,
time_slot_end TIME NOT NULL
);
CREATE TABLE building( 
building_id SERIAL PRIMARY KEY,
building_name VARCHAR(50) NOT NULL UNIQUE,
building_address VARCHAR(200) NOT NULL UNIQUE
);
CREATE TABLE auditorium(
auditorium_id SERIAL PRIMARY KEY,
auditorium_number INTEGER NOT NULL CHECK(auditorium_number>0 AND auditorium_number<1000),
auditorium_capacity INTEGER NOT NULL CHECK(auditorium_number>0 AND auditorium_number<100),
building_id INTEGER NOT NULL REFERENCES building(building_id) ON UPDATE CASCADE ON DELETE CASCADE,
UNIQUE (auditorium_number, building_id)
);
CREATE TABLE faculty(
faculty_id SERIAL PRIMARY KEY,
faculty_name VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE groups(
group_id SERIAL PRIMARY KEY,
group_name CHAR(6) NOT NULL UNIQUE,
faculty_id INTEGER REFERENCES faculty(faculty_id) ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE TABLE course(
course_id SERIAL PRIMARY KEY,
course_name VARCHAR(30) NOT NULL UNIQUE,
faculty_id INTEGER NOT NULL REFERENCES faculty(faculty_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE teacher(
person_id SERIAL PRIMARY KEY,
person_first_name VARCHAR(20) NOT NULL,
person_last_name VARCHAR(30) NOT NULL,
faculty_id INTEGER REFERENCES faculty(faculty_id) ON UPDATE CASCADE ON DELETE SET NULL,
UNIQUE (person_id, faculty_id)
);
CREATE TABLE student(
person_id SERIAL PRIMARY KEY,
person_first_name VARCHAR(20) NOT NULL,
person_last_name VARCHAR(30) NOT NULL,
group_id INTEGER REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE SET NULL,
UNIQUE (person_id, group_id)
);
CREATE TABLE lesson(
lesson_id SERIAL PRIMARY KEY,
course_id INTEGER REFERENCES course(course_id) ON UPDATE CASCADE ON DELETE SET NULL,
lesson_type_id INTEGER NOT NULL REFERENCES lesson_type(lesson_type_id) ON UPDATE CASCADE ON DELETE CASCADE,
auditorium_id INTEGER REFERENCES auditorium(auditorium_id) ON UPDATE CASCADE ON DELETE SET NULL,
date DATE NOT NULL,
time_slot_id INTEGER REFERENCES time_slot(time_slot_id) ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE TABLE teacher_course(
person_id INTEGER REFERENCES teacher(person_id) ON UPDATE CASCADE ON DELETE CASCADE,
course_id INTEGER REFERENCES course(course_id) ON UPDATE CASCADE ON DELETE CASCADE,
UNIQUE (person_id, course_id)
);
CREATE TABLE lesson_teacher(
person_id INTEGER REFERENCES teacher(person_id) ON UPDATE CASCADE ON DELETE SET NULL,
lesson_id INTEGER REFERENCES lesson(lesson_id) ON UPDATE CASCADE ON DELETE CASCADE,
UNIQUE (person_id, lesson_id)
);
CREATE TABLE lesson_group(
lesson_id INTEGER REFERENCES lesson(lesson_id) ON UPDATE CASCADE ON DELETE CASCADE,
group_id INTEGER REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE SET NULL,
UNIQUE (group_id, lesson_id)
);

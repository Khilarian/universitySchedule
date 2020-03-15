CREATE TABLE academic_degree(
degree_name VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY
);
CREATE TABLE academic_rank(
rank_name VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY
);
CREATE TABLE course_number(
course_number_name VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY
);
CREATE TABLE lesson_type(
lesson_type_name VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY
);
CREATE TABLE term(
term_name VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY
);
CREATE TABLE time_slot(
time_slot_name VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY
);
CREATE TABLE building( 
building_id SERIAL PRIMARY KEY,
building_name VARCHAR(50) NOT NULL UNIQUE,
building_address VARCHAR(200) NOT NULL UNIQUE
);
CREATE TABLE room(
room_id SERIAL PRIMARY KEY,
room_number INTEGER NOT NULL,
building_id INTEGER REFERENCES building(building_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE auditorium(
room_id INTEGER REFERENCES room(room_id)ON UPDATE CASCADE ON DELETE CASCADE PRIMARY KEY,
capacity INTEGER NOT NULL
);
CREATE TABLE office(
office_id INTEGER REFERENCES room(room_id) ON UPDATE CASCADE ON DELETE CASCADE PRIMARY KEY,
opened_from TIME NOT NULL,
opened_till TIME NOT NULL
);
CREATE TABLE faculty(
faculty_id SERIAL PRIMARY KEY,
faculty_name VARCHAR(100) NOT NULL UNIQUE,
faculty_office_id INTEGER REFERENCES office(office_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE department(
department_id SERIAL PRIMARY KEY,
department_name VARCHAR(100) NOT NULL UNIQUE,
faculty_id INTEGER REFERENCES faculty(faculty_id) ON UPDATE CASCADE ON DELETE CASCADE,
department_office_id INTEGER REFERENCES office(office_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE subject(
subject_id SERIAL PRIMARY KEY,
subject_name VARCHAR(30) NOT NULL UNIQUE,
department_id INTEGER REFERENCES department(department_id)
);
CREATE TABLE person(
person_id SERIAL PRIMARY KEY,
person_first_name VARCHAR(20) NOT NULL,
person_last_name VARCHAR(30) NOT NULL
);
CREATE TABLE teacher(
teacher_id INTEGER REFERENCES person(person_id) ON UPDATE CASCADE ON DELETE CASCADE PRIMARY KEY,
degree_name VARCHAR(100) REFERENCES academic_degree(degree_name) ON UPDATE CASCADE ON DELETE CASCADE,
rank_name VARCHAR(100) REFERENCES academic_rank(rank_name) ON UPDATE CASCADE ON DELETE CASCADE,
department_id INTEGER REFERENCES department(department_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE speciality(
speciality_id SERIAL PRIMARY KEY,
speciality_name VARCHAR(50),
degree_name VARCHAR(100) REFERENCES academic_degree(degree_name) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE groups(
group_id SERIAL PRIMARY KEY,
group_name VARCHAR(10) NOT NULL UNIQUE,
speciality_id INTEGER REFERENCES speciality(speciality_id) ON UPDATE CASCADE ON DELETE CASCADE,
course_number_name VARCHAR(20) REFERENCES course_number(course_number_name) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE student(
student_id INTEGER REFERENCES person(person_id) ON UPDATE CASCADE ON DELETE CASCADE PRIMARY KEY,
group_id INTEGER REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE TABLE curriculum(
curriculum_id SERIAL PRIMARY KEY,
curriculum_name VARCHAR(50),
speciality_id INTEGER REFERENCES speciality(speciality_id) ON UPDATE CASCADE ON DELETE CASCADE,
valid_from DATE NOT NULL,
valid_till DATE NOT NULL
);
CREATE TABLE term_plan(
term_plan_id SERIAL PRIMARY KEY,
term_name VARCHAR(20) REFERENCES term(term_name) ON UPDATE CASCADE ON DELETE CASCADE,
curriculum_id INTEGER REFERENCES curriculum(curriculum_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE subject_plan(
subject_plan_id SERIAL PRIMARY KEY,
term_plan_id INTEGER REFERENCES term_plan(term_plan_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE subject_lesson(
subject_lesson_id SERIAL PRIMARY KEY,
subject_plan_id INTEGER REFERENCES subject_plan(subject_plan_id) ON UPDATE CASCADE ON DELETE CASCADE,
subject_id INTEGER REFERENCES subject(subject_id) ON UPDATE CASCADE ON DELETE CASCADE,
lesson_type_name VARCHAR(20) REFERENCES lesson_type(lesson_type_name) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE lesson(
lesson_id SERIAL PRIMARY KEY,
subject_lesson_id INTEGER REFERENCES subject_lesson(subject_lesson_id) ON UPDATE CASCADE ON DELETE CASCADE,
auditorium_id INTEGER REFERENCES auditorium(room_id) ON UPDATE CASCADE ON DELETE CASCADE,
date DATE,
time_slot_name VARCHAR(20) REFERENCES time_slot(time_slot_name) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE teacher_subject(
teacher_id INTEGER REFERENCES teacher(teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
subject_id INTEGER REFERENCES subject(subject_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE teacher_lesson(
teacher_id INTEGER REFERENCES teacher(teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
lesson_id INTEGER REFERENCES lesson(lesson_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE lesson_group(
lesson_id INTEGER REFERENCES lesson(lesson_id) ON UPDATE CASCADE ON DELETE CASCADE,
group_id INTEGER REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE
);
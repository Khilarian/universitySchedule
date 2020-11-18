INSERT INTO building (building_name, building_address) VALUES ('Main','Khimki'), ('Secondary','Klin');
INSERT INTO auditorium (auditorium_number, auditorium_capacity, building_id) VALUES (1,25,1), (2,30,1), (1,15,2),(2,20,2);
INSERT INTO faculty (faculty_name) VALUES ('IT'),('Mining'),('Traiding');
INSERT INTO groups (group_name, faculty_id) VALUES ('CS-201',1),('TR-201',3),('MN-201',2);
INSERT INTO course (course_name, faculty_id) VALUES ('Computer science',1),('Miners',2),('Mining OS',2),('Brokers',3);
INSERT INTO student (person_first_name, person_last_name, group_id, person_email, person_phone) VALUES ('Kirill','Rumakin', 3, 'rumki@ya.ru', '+7(925)8641498'),('Katya','Jopushka', 1, 'jopka@jopka.com', '+7(925)1112233'),('Alexey', 'Nagosha', 2, 'naga@ya.ru', '+7(925)1234567');
INSERT INTO teacher (person_first_name, person_last_name, faculty_id, person_email, person_phone) VALUES ('Hulk','Hogan', 3, 'hogan@gmail.com', '+7(897)0985665'),('Lex','Luger', 1, 'luger@gmail.com', '+7(567)5435454'),('Bill', 'Goldberg', 2, 'goldberg@gmail.com', '+7(999)1234567');
INSERT INTO teacher_course (person_id, course_id) VALUES (1, 4), (2, 1), (3, 2), (3, 3);
INSERT INTO lesson_type (lesson_type_name) VALUES ('LECTURE'),('SEMINAR'), ('LABORATORY'), ('EXAM');
INSERT INTO time_slot (time_slot_name, time_slot_number, time_slot_start, time_slot_end) VALUES ('FIRST',1,'08:30','09:50'),('SECOND',2,'10:00','11:20');
INSERT INTO lesson (course_id, lesson_type_id, auditorium_id, date, time_slot_id) VALUES (1,1,1,'16-11-2020',1);
INSERT INTO lesson_teacher (lesson_id, person_id) VALUES (1,1);
INSERT INTO lesson_group (lesson_id, group_id) VALUES (1,1);
INSERT INTO lesson (course_id, lesson_type_id, auditorium_id, date, time_slot_id) VALUES (2,3,3,'17-11-2020',2);
INSERT INTO lesson_teacher (lesson_id, person_id) VALUES (2,3);
INSERT INTO lesson_teacher (lesson_id, person_id) VALUES (2,2);
INSERT INTO lesson_group (lesson_id, group_id) VALUES (2,3);

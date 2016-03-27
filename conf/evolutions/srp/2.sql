# --- !Ups

insert into school_registration_request (school_name, principal_name, school_address, contact,request_number) values('dps', 'vineet', 'abcd', '9003218471', '12345');
insert into school (name, school_user_name, school_board, school_registration_id) values ('dps', 'dps', 'CBSE', 1234);
insert into login (user_name, password, school_id) VALUES ('vineet', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 1);
insert into employee (name, user_name, school_id, phone_number1) values('vineet', 'vineet', 1, '9003218471');
insert into school_principle (school_id, principle_id) values(1, 1);
insert into class(class_name, school_id, class_start_time, class_end_time,no_of_period, parent_class, user_name) values('7A', 1, '09:30 AM', '04:40 PM', 7, '7', 'manish');
insert into employee_class (class_id, teacher_id) values(1, 1);
insert into guardian (name, user_name, phone_number1) values('parma', 'parma', '9003218471');
insert into student (name, user_name, school_id) values('vineet', 'vineet', 1);
insert into student_guardian (student_id, guardian_id) values(1, 1);
insert into student_class (student_id, class_id) values(1, 1);
# --- !Downs
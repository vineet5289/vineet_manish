# --- !Ups

CREATE TABLE IF NOT EXISTS message (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  school_id bigint(20) NOT NULL,
  message longtext COLLATE utf8_unicode_ci NOT NULL,
  receiver_ids text COLLATE utf8_unicode_ci, 
  class_ids text COLLATE utf8_unicode_ci,
  is_for_parents tinyint(1) DEFAULT 0,
  is_for_teachers tinyint(1) DEFAULT 0,
  is_for_students tinyint(1) DEFAULT 0,
  is_for_school tinyint(1) DEFAULT 0, 
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS user_super_user (
  super_user_name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  school_id bigint(20) NOT NULL,
  user_name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (super_user_name, user_name),
  KEY FK_user_super_user_school_id (school_id),
  CONSTRAINT FK_user_super_user_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  KEY FK_user_super_user_name (super_user_name),
  CONSTRAINT FK_user_super_user_name FOREIGN KEY (super_user_name) REFERENCES login (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into login (user_name, password, name, role, access_rights, school_id) VALUES ('vineet5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'vineet singh', 'SUPERADMIN', 'ALL=1', 1);
insert into login (user_name, password, name, role, access_rights, school_id) VALUES ('manish5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'manish kumar', 'ADMIN', 'MODI_ST=1,MODI_CL=1', 1);
insert into login (user_name, password, name, role, access_rights, school_id) VALUES ('saurabh5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'saurabh', 'TEACHER', 'MODI_CL=1', 1);
insert into login (user_name, password, name, role, access_rights, school_id) VALUES ('sumit5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'sumit kumar', 'GUARDIAN', '', 1);
insert into login (user_name, password, name, role, access_rights, school_id) VALUES ('vivek5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'vivek kush', 'GUARDIAN', 'MODI_ST=1', 1);
insert into login (user_name, password, name, role, school_id) VALUES ('vivekKush5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'vivek', 'ACCOUNTENT', 1);
insert into login (user_name, password, name, role) VALUES ('vineetsingh5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'vineet kumar', 'GUARDIAN');
insert into login (user_name, password, name, role, school_id) VALUES ('vivek52', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'student1', 'STUDENT', 1);
insert into login (user_name, password, name, role, school_id) VALUES ('vineet52', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'student2', 'STUDENT', 1);
insert into login (user_name, password, name, role, school_id) VALUES ('sumit52', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'student3', 'STUDENT', 1);
insert into login (user_name, password, name, role, school_id) VALUES ('manish52', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'student4', 'STUDENT', 1);
insert into login (user_name, password, name, role, school_id) VALUES ('saurabh52', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'student5', 'STUDENT', 1);
insert into user_school (login_id, user_name, school_id, name) VALUES ('vineet5289', 'vivek52', 1, 'student1');
insert into user_school (login_id, user_name, school_id, name) VALUES ('manish5289', 'vivek52', 1, 'student1');
insert into user_school (login_id, user_name, school_id, name) VALUES ('saurabh5289', 'vineet52', 1, 'student2');
insert into user_school (login_id, user_name, school_id, name) VALUES ('sumit5289', 'sumit52', 1, 'student3');
insert into user_school (login_id, user_name, school_id, name) VALUES ('vivek5289', 'manish52', 1, 'student4');
insert into user_school (login_id, user_name, school_id, name) VALUES ('vineet5289', 'manish52', 1, 'student4');

# --- !Downs

drop table message;
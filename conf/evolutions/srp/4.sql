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

CREATE TABLE IF NOT EXISTS user_school (
  school_id bigint(20) NOT NULL,
  user_name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  role varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (school_id, user_name),
  KEY FK_user_school_school_id (school_id),
  CONSTRAINT FK_user_school_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  KEY FK_user_school_user_name (user_name),
  CONSTRAINT FK_user_school_user_name FOREIGN KEY (user_name) REFERENCES login (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into login (user_name, password, name, role, access_rights) VALUES ('vineet5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'vineet singh', 'SUPERADMIN', 'ALL=1');
insert into user_school (user_name, school_id, name, role) VALUES ('vineet5289', 1, 'vineet singh', 'SUPERADMIN');
insert into login (user_name, password, name, role, access_rights) VALUES ('manish5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'manish kumar', 'ADMIN', 'MODI_ST=1,MODI_CL=1');
insert into user_school (user_name, school_id, name, role) VALUES ('manish5289', 1, 'manish kumar', 'ADMIN');
insert into login (user_name, password, name, role, access_rights) VALUES ('vivek5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'vivek kush', 'STUDENT', 'MODI_ST=1');
insert into user_school (user_name, school_id, name, role) VALUES ('vivek5289', 1, 'vivek kush', 'STUDENT');
insert into login (user_name, password, name, role, access_rights) VALUES ('saurabh5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'saurabh', 'TEACHER', 'MODI_CL=1');
insert into user_school (user_name, school_id, name, role) VALUES ('saurabh5289', 1, 'saurabh', 'TEACHER');
insert into login (user_name, password, name, role, access_rights) VALUES ('sumit5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'sumit kumar', 'GUARDIAN', '');
insert into user_school (user_name, school_id, name, role) VALUES ('sumit5289', 1, 'sumit kumar', 'GUARDIAN');
insert into login (user_name, password, name, role) VALUES ('vivekKush5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', 'vivek', 'ACCOUNTENT');
insert into user_school (user_name, school_id, name, role) VALUES ('vivekKush5289', 1, 'vivek', 'ACCOUNTENT');

# --- !Downs

drop table message;
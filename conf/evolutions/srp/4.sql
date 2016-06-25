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

insert into login (user_name, password, school_id, role, access_rights) VALUES ('vivek5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'SUPERADMIN', 'ALL=1');
insert into login (user_name, password, school_id, role, access_rights) VALUES ('manish5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1,2', 'ADMIN', 'MODI_ST=1,MODI_CL=1');
insert into login (user_name, password, school_id, role, access_rights) VALUES ('vineet5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'STUDENT', 'MODI_ST=1');
insert into login (user_name, password, school_id, role, access_rights) VALUES ('saurabh5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'TEACHER', 'MODI_CL=1');
insert into login (user_name, password, school_id, role, access_rights) VALUES ('sumit5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'GUARDIAN', '');
insert into login (user_name, password, school_id, role) VALUES ('vivekKush5289', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'ACCOUNTENT');

# --- !Downs

drop table message;
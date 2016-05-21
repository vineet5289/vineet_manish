# --- !Ups

CREATE TABLE IF NOT EXISTS message (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  school_id bigint(20) NOT NULL,
  message varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into login (user_name, password, school_id, role) VALUES ('vineet_s', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'STUDENT');
insert into login (user_name, password, school_id, role) VALUES ('vineet_a', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'ADMIN');
insert into login (user_name, password, school_id, role) VALUES ('vineet_sa', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'SUPERADMIN');
insert into login (user_name, password, school_id, role) VALUES ('vineet_t', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'TEACHER');
insert into login (user_name, password, school_id, role) VALUES ('vineet_g', '$2a$10$uOlVktWoYf06wmwED53WaO1wUPGV.qFyJ.WtefhEDyooXgOF4UByC', '1', 'GUARDIAN');

# --- !Downs

drop table message;
# --- !Ups

CREATE TABLE IF NOT EXISTS login (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  password varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  password_state enum('FIRST_TIME', 'RESET', 'BLOCKED', 'CORRECT_PASSWORD') NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  auth_token varchar(255) COLLATE utf8_unicode_ci,
  role enum('STUDENT', 'ADMIN', 'SUPERADMIN') NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS board (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    board_code varchar(50)  ,
    board_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY (board_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS state (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  code varchar(255) COLLATE utf8_unicode_ci,
  PRIMARY KEY (id),
  UNIQUE KEY(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS school (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  school_registration_id bigint(20) NOT NULL,
  school_user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  schoole_email varchar(225) COLLATE utf8_unicode_ci,
  school_principle_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  address_line1 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  address_line2 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  city varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  state varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  pin_code varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  office_number1 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  office_number2 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  country varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  no_of_shift int(3) NOT NULL DEFAULT 1,
  school_category_id bigint(20) NOT NULL,
  school_board_id bigint(20) NOT NULL,
  school_type enum('GOVERMENT', 'PRIVATE') NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (id),
  KEY FK_school_school_category_id (school_category_id),
  CONSTRAINT FK_school_school_category_id FOREIGN KEY (school_category_id) REFERENCES school_category (id),
  KEY FK_school_school_board_id (school_board_id),
  CONSTRAINT FK_school_school_board_id FOREIGN KEY (school_board_id) REFERENCES board (id),
  UNIQUE KEY (school_user_name),
  UNIQUE KEY (school_registration_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS employee (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  designation varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  school_id bigint(20) NOT NULL,
  emp_email varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  joining_date timestamp NULL,
  leaving_date timestamp NULL,
  gender enum('M', 'F') NOT NULL,
  emp_category enum('TEACHER', 'ACCOUNTENT'),
  department varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  dob date not null,
  address_line1 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  address_line2 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  city varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  state varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  pin_code varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  phone_number1 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  phone_number2 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  country varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (id),
  KEY FK_employee_school_id (school_id),
  CONSTRAINT FK_employee_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS guardian (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  guardian_email varchar(225) COLLATE utf8_unicode_ci NULL,
  school_id bigint(20) NOT NULL,
  gender enum('M', 'F') NOT NULL,
  dob date not null,
  address_line1 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  address_line2 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  city varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  state varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  pin_code varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  phone_number1 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  phone_number2 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  country varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  occupation varchar(50) COLLATE utf8_unicode_ci NULL,
  relation enum('FATHER', 'MOTHER', 'GAURDIAN', 'BROTHER', 'SISTER') NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (id),
  KEY FK_guardian_school_id (school_id),
  CONSTRAINT FK_guardian_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS class (
  id bigint(11) NOT NULL AUTO_INCREMENT,
  class_name varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  school_id bigint(11) NOT NULL,
  teacher_id bigint(11) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_class_school_id (school_id),
  CONSTRAINT FK_class_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  KEY FK_class_teacher_id (teacher_id),
  CONSTRAINT FK_class_teacher_id FOREIGN KEY (teacher_id) REFERENCES employee (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS student (
  id bigint(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  school_id bigint(11) NOT NULL,
  guardian_id bigint(11) NOT NULL,
  class_id bigint(11) NOT NULL,
  student_email varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  student_roll_number varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  registration_number int(11) NOT NULL,
  registration_date timestamp NULL,
  school_joining_date timestamp NULL,
  school_leaving_date timestamp NULL,
  gender enum('M', 'F') NOT NULL,
  dob date not null,
  current_year date not null,
  address_line1 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  address_line2 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  city varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  state varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  pin_code varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  phone_number1 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  phone_number2 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  country varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  is_deleted tinyint(1) DEFAULT 0,
  PRIMARY KEY (id),
  KEY FK_student_school_id (school_id),
  CONSTRAINT FK_student_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  KEY FK_student_guardian_id (guardian_id),
  CONSTRAINT FK_student_guardian_id FOREIGN KEY (guardian_id) REFERENCES guardian(id),
  KEY FK_student_class_id (class_id),
  CONSTRAINT FK_student_class_id FOREIGN KEY (class_id) REFERENCES class(id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE UNIQUE INDEX login_user_name_index ON login (user_name);

INSERT INTO board (board_code, board_name) VALUES ('CBSE', 'CBSE');
INSERT INTO board (board_code, board_name) VALUES ('ICSE', 'ICSE');
INSERT INTO board (board_code, board_name) VALUES ('UP BOARD', 'UP BOARD');
INSERT INTO board (board_code, board_name) VALUES ('MP BOARD', 'MP BOARD');

# --- !Downs

drop table student;
drop table class;
drop table guardian;
drop table employee;
drop table school;
drop table login;
drop table board;
drop table state;

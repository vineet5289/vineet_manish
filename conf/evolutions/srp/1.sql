# --- !Ups

CREATE TABLE IF NOT EXISTS board (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    board_code varchar(50) NOT NULL,
    board_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    board_display_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    affiliated_to varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY (board_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS school_registration_request (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  school_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  contact_person_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  school_email varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  mobile_number varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  alternative_number varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  school_registration_id varchar(20) COLLATE utf8_unicode_ci,
  query text COLLATE utf8_unicode_ci,
  school_address_line1 varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  school_address_line2 varchar(255) COLLATE utf8_unicode_ci,
  city varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  state varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  country varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  pin_code varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  status enum('REQUESTED', 'REJECTED', 'APPROVED', 'REGISTERED') DEFAULT 'REQUESTED' NOT NULL,
  auth_token varchar(255) COLLATE utf8_unicode_ci,
  auth_token_genereated_at timestamp NULL,
  request_number varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  alert_done tinyint(1) DEFAULT 0 NOT NULL,
  group_of_school enum('SINGLE', 'MULTI_BRANCH') DEFAULT 'SINGLE' NOT NULL,
  no_of_branch int NOT NULL DEFAULT 1,
  approval_user_name varchar(255) COLLATE utf8_unicode_ci,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS school (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  school_prefered_name varchar(255) COLLATE utf8_unicode_ci,
  school_registration_id varchar(225) COLLATE utf8_unicode_ci,
  school_user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  school_email varchar(225) COLLATE utf8_unicode_ci,
  school_alternative_email varchar(15) COLLATE utf8_unicode_ci,
  address_line1 varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  address_line2 varchar(255) COLLATE utf8_unicode_ci,
  city varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  state varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  country varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  pin_code varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  phone_number varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  office_number varchar(20) COLLATE utf8_unicode_ci,
  is_hostel_facilities_available tinyint(1) DEFAULT 0 NOT NULL,
  is_hostel_compulsory tinyint(1) DEFAULT 0 NOT NULL ,
  school_board_id bigint(20) COLLATE utf8_unicode_ci NOT NULL,
  school_type enum('GOVERMENT', 'PRIVATE', 'INTERNATIONALSCHOOL', 'OPENSCHOOL', 'SPECIALNEEDSCHOOL') DEFAULT 'GOVERMENT' NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  add_school_request_id bigint(20) NOT NULL,
  no_of_shift int(3) NOT NULL DEFAULT 1,
  school_class_from varchar(100),
  school_class_to varchar(100),
  school_office_start_time varchar(10),
  school_office_end_time varchar(10),
  school_financial_start_day int,
  school_financial_end_day int,
  school_financial_start_month int,
  school_financial_end_month int,
  school_financial_start_year int,
  school_financial_end_year int,
  school_date_format varchar(50) COLLATE utf8_unicode_ci DEFAULT 'dd/MM/yyyy' NOT NULL,
  school_current_financial_year varchar(50),
  school_office_week_start_day varchar(10),
  school_office_week_end_day varchar(10),
  school_website_url varchar(255) COLLATE utf8_unicode_ci,
  school_logo_url varchar(255) COLLATE utf8_unicode_ci,
  group_of_school enum('SINGLE', 'MULTI_BRANCH') DEFAULT 'SINGLE' NOT NULL,
  no_of_branch int NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY (school_user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS login (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  email_id varchar(255) COLLATE utf8_unicode_ci,
  password varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  password_state enum('firststate', 'blockedstate', 'redirectstate', 'finalstate') DEFAULT 'firststate' NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  role varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  access_rights longtext COLLATE utf8_unicode_ci,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  type enum('GENERAL', 'GUARDIAN', 'STUDENT', 'SCHOOL', 'EMP') DEFAULT 'GENERAL' NOT NULL,
  school_id bigint(20),
  PRIMARY KEY (id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS employee (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  emp_prefered_name varchar(255) COLLATE utf8_unicode_ci,
  user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  school_id bigint(20) NOT NULL,
  gender enum('M', 'F') NOT NULL,
  phone_number varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  emp_code varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  alternative_number varchar(20) COLLATE utf8_unicode_ci,
  job_titles varchar(225) COLLATE utf8_unicode_ci,
  emp_email varchar(225) COLLATE utf8_unicode_ci,
  emp_alternative_email varchar(15) COLLATE utf8_unicode_ci,
  joining_date date,
  leaving_date date,
  departments varchar(255) COLLATE utf8_unicode_ci,
  dob date null,
  address_line1 varchar(255) COLLATE utf8_unicode_ci,
  address_line2 varchar(255) COLLATE utf8_unicode_ci,
  city varchar(255) COLLATE utf8_unicode_ci,
  state varchar(255) COLLATE utf8_unicode_ci,
  pin_code varchar(20) COLLATE utf8_unicode_ci,
  country varchar(255) COLLATE utf8_unicode_ci,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  requested_user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id),
  KEY FK_employee_school_id (school_id),
  CONSTRAINT FK_employee_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS school_principle (
	school_id bigint(20) NOT NULL,
	principle_id bigint(20) NOT NULL,
	is_active tinyint(1) DEFAULT 1,
	PRIMARY KEY (school_id, principle_id),
	KEY FK_school_principle_principle_id (principle_id),
	CONSTRAINT FK_school_principle_principle_id FOREIGN KEY (principle_id) REFERENCES employee (id),
	KEY FK_school_principle_school_id (school_id),
    CONSTRAINT FK_school_principle_school_id FOREIGN KEY (school_id) REFERENCES school (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS class (
  id bigint(11) NOT NULL AUTO_INCREMENT,
  class_name varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  school_id bigint(11) NOT NULL,
  class_start_time varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  class_end_time varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  no_of_period int(11) NOT NULL,
  parent_class varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  user_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id),
  KEY FK_class_school_id (school_id),
  CONSTRAINT FK_class_school_id FOREIGN KEY (school_id) REFERENCES school (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS employee_class (
  class_id bigint(11) NOT NULL,
  teacher_id bigint(11) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (class_id, teacher_id),
  KEY FK_employee_class_class_id (class_id),
  CONSTRAINT FK_employee_class_class_id FOREIGN KEY (class_id) REFERENCES class (id),
  KEY FK_employee_class_teacher_id (teacher_id),
  CONSTRAINT FK_employee_class_teacher_id FOREIGN KEY (teacher_id) REFERENCES employee (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS guardian (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  guardian_email varchar(225) COLLATE utf8_unicode_ci NULL,
  gender enum('M', 'F') NULL,
  dob date null,
  address_line1 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  address_line2 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  city varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  state varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  pin_code varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  country varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  phone_number1 varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  occupation varchar(50) COLLATE utf8_unicode_ci NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS student (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  user_name varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  school_id bigint(20) NOT NULL,
  student_email varchar(225) COLLATE utf8_unicode_ci DEFAULT NULL,
  school_joining_date timestamp NULL,
  school_leaving_date timestamp NULL,
  gender enum('M', 'F') NOT NULL,
  dob date null,
  address_line1 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  address_line2 varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  city varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  state varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  country varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  pin_code varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  phone_number1 varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (id),
  KEY FK_student_school_id (school_id),
  CONSTRAINT FK_student_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS student_guardian (
  student_id bigint(20) NOT NULL,
  guardian_id bigint(20) NOT NULL,
  relation enum('FATHER', 'MOTHER', 'GAURDIAN') NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (student_id, guardian_id),
  KEY FK_student_guardian_student_id (student_id),
  CONSTRAINT FK_student_guardian_student_id FOREIGN KEY (student_id) REFERENCES student (id),
  KEY FK_student_guardian_guardian_id (guardian_id),
  CONSTRAINT FK_student_guardian_guardian_id FOREIGN KEY (guardian_id) REFERENCES guardian (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS student_class (
  student_id bigint(20) NOT NULL,
  class_id bigint(20) NOT NULL,
  PRIMARY KEY (student_id, class_id),
  KEY FK_student_class_student_id (student_id),
  CONSTRAINT FK_student_class_student_id FOREIGN KEY (student_id) REFERENCES student (id),
  KEY FK_student_class_class_id (class_id),
  CONSTRAINT FK_student_class_class_id FOREIGN KEY (class_id) REFERENCES class (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT INTO board (board_code, board_name, board_display_name, affiliated_to) VALUES ('GBSHSE', 'Goa Board of Secondary & Higher Secondary Education', 'Goa Board', 'GOA');
INSERT INTO board (board_code, board_name, board_display_name, affiliated_to) VALUES ('KSEEB', 'Karnataka Secondary Education Examination Board ', 'Karnataka Board', 'KARNATAKA');
INSERT INTO board (board_code, board_name, board_display_name, affiliated_to) VALUES ('ICSE', 'Indian Certificate of Secondary Education', 'ICSE', 'ICSE');
INSERT INTO board (board_code, board_name, board_display_name, affiliated_to) VALUES ('CBSE', 'Central Board of Secondary Education', 'CBSE', 'CBSE');
INSERT INTO board (board_code, board_name, board_display_name, affiliated_to) VALUES ('IB', 'International Baccalaureate', 'IB', 'IB');


# --- !Downs

drop table student;
drop table class;
drop table guardian;
drop table employee;
drop table school;
drop table login;
drop table board;

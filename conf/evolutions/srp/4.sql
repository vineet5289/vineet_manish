# --- !Ups

CREATE TABLE IF NOT EXISTS school_shift_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  shift_name varchar(50) NOT NULL,
  shift_class_start_time varchar(10) NOT NULL,
  shift_class_end_time varchar(10) NOT NULL,
  shift_week_start_time varchar(10) NOT NULL,
  shift_week_end_time varchar(10) NOT NULL,
  shift_start_class_from varchar(50) NOT NULL,
  shift_end_class_to varchar(50) NOT NULL,
  shift_attendence_type enum('once', 'twice', 'everyperiod') DEFAULT 'once' NOT NULL,
  school_id bigint(20) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (id),
  KEY FK_school_shift_info_school_id (school_id),
  CONSTRAINT FK_school_shift_info_school_id FOREIGN KEY (school_id) REFERENCES institute (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS permissions (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  permission_name varchar(50) NOT NULL,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS user_permissions (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_name varchar(225) COLLATE utf8_unicode_ci,
  permissions_list text,
  institute_id bigint(20) NOT NULL,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_institute_institute_id (institute_id),
  CONSTRAINT FK_institute_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO permissions (permission_name) VALUES ('addEmployee');
INSERT INTO permissions (permission_name) VALUES ('deleteEmployee');
INSERT INTO permissions (permission_name) VALUES ('editEmployeeInfo');
INSERT INTO permissions (permission_name) VALUES ('viewEmployeeFullDetails');
INSERT INTO permissions (permission_name) VALUES ('addStudent');
INSERT INTO permissions (permission_name) VALUES ('deleteStudent');
INSERT INTO permissions (permission_name) VALUES ('viewStudentFullDetails');
# --- !Downs

drop table school_shift_info;

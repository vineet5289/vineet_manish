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
  permission_name varchar(255) NOT NULL,
  permission_description varchar(255),
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS groups (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  group_name varchar(255) NOT NULL,
  group_description varchar(255),
  group_added_by varchar(255),
  institute_id bigint(20) NOT NULL,
  head_institute_id bigint(20) NOT NULL,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_group_institute_id (institute_id),
  CONSTRAINT FK_group_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id),
  KEY FK_group_head_institute_id (head_institute_id),
  CONSTRAINT FK_group_head_institute_id FOREIGN KEY (head_institute_id) REFERENCES head_institute (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS roles (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) NOT NULL,
  role_description varchar(255),
  role_added_by varchar(255),
  permission text COLLATE utf8_unicode_ci,
  institute_id bigint(20),
  head_institute_id bigint(20),
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  is_editable tinyint(1) DEFAULT 1 NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS employee_role_group (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  employee_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  group_id bigint(20) NOT NULL,
  institute_id bigint(20) NOT NULL,
  head_institute_id bigint(20) NOT NULL,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_employee_role_group_institute_id (institute_id),
  CONSTRAINT FK_employee_role_group_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id),
  KEY FK_employee_role_group_head_institute_id (head_institute_id),
  CONSTRAINT FK_employee_role_group_head_institute_id FOREIGN KEY (head_institute_id) REFERENCES head_institute (id),
  KEY FK_employee_role_group_employee_id (employee_id),
  CONSTRAINT FK_employee_role_group_employee_id FOREIGN KEY (employee_id) REFERENCES employee (id),
  KEY FK_employee_role_group_role_id (role_id),
  CONSTRAINT FK_employee_role_group_role_id FOREIGN KEY (role_id) REFERENCES roles (id),
  KEY FK_employee_role_group_group_id (group_id),
  CONSTRAINT FK_employee_role_group_group_id FOREIGN KEY (group_id) REFERENCES groups (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO roles (role_name, role_description, role_added_by, permission, institute_id, head_institute_id, is_active, is_editable) 
VALUES ('testing_role_active', 'this is just for testing', 'vineet5289', 'add_new_employee,delete_employee,view_full_employee_details', 1, 1, 1, 1);
INSERT INTO roles (role_name, role_description, role_added_by, permission, institute_id, head_institute_id, is_active, is_editable) 
VALUES ('testing_role_inactive', 'this is just for testing', 'vineet5289', 'add_new_employee,delete_employee,view_full_employee_details', 1, 1, 0, 1);
INSERT INTO roles (role_name, role_description, role_added_by, permission, institute_id, head_institute_id, is_active, is_editable) 
VALUES ('instituteadmin', 'this is just for testing', 'vineet5289', 'add_new_employee,delete_employee,view_full_employee_details', 1, 1, 1, 0);
INSERT INTO roles (role_name, role_description, role_added_by, permission, institute_id, head_institute_id, is_active, is_editable) 
VALUES ('institutegroupadmin', 'this is just for testing', 'vineet5289', 'add_new_employee,delete_employee,view_full_employee_details', 1, 1, 1, 0);
INSERT INTO roles (role_name, role_description, role_added_by, permission, institute_id, head_institute_id, is_active, is_editable) 
VALUES ('student', 'this is just for testing', 'vineet5289', 'can only perform students related activity', 1, 1, 1, 0);

INSERT INTO permissions (permission_name) VALUES ('addEmployee');
INSERT INTO permissions (permission_name) VALUES ('deleteEmployee');
INSERT INTO permissions (permission_name) VALUES ('editEmployeeInfo');
INSERT INTO permissions (permission_name) VALUES ('viewEmployeeFullDetails');
INSERT INTO permissions (permission_name) VALUES ('addStudent');
INSERT INTO permissions (permission_name) VALUES ('deleteStudent');
INSERT INTO permissions (permission_name) VALUES ('viewStudentFullDetails');
# --- !Downs

drop table school_shift_info;

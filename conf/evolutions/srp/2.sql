# --- !Ups
CREATE TABLE IF NOT EXISTS subject (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  subject_name varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  class_id bigint(20) NOT NULL,
  institute_id bigint(20) NOT NULL,
  section_id bigint(20),
  subject_code varchar(20) COLLATE utf8_unicode_ci,
  subject_id bigint(20),
  is_active tinyint(1) DEFAULT 1,
  created_by varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  description varchar(255) COLLATE utf8_unicode_ci,
  recommended_book varchar(255) COLLATE utf8_unicode_ci,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_subject_class_id (class_id),
  CONSTRAINT FK_subject_class_id FOREIGN KEY (class_id) REFERENCES class (id),
  KEY FK_subject_institute_id (institute_id),
  CONSTRAINT FK_subject_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS timetable (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  day enum('sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday') DEFAULT 'sunday' NOT NULL,
  day_seq INT NOT NULL,
  period_no INT NOT NULL,
  period_name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  start_time varchar(20) NOT NULL,
  end_time varchar(20) NOT NULL,
  duration INT NOT NULL,
  number_of_days INT,
  institute_id bigint(20) NOT NULL,
  class_id bigint(20) NOT NULL,
  section_id bigint(20),
  same_as_previous_period tinyint(1) DEFAULT 0,
  time_table_updated_by varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  is_active tinyint(1) DEFAULT 1,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_timetable_institute_id (institute_id),
  CONSTRAINT FK_timetable_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id),
  KEY FK_timetable_class_id (class_id),
  CONSTRAINT FK_timetable_class_id FOREIGN KEY (class_id) REFERENCES class (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS temp_timetable (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  timetable_id bigint(20) NOT NULL,
  time_table_updated_by varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  start_date varchar(20) NOT NULl,
  end_date varchar(20) NOT NULL,
  is_active tinyint(1) DEFAULT 1,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_temp_timetable_timetable_id (timetable_id),
  CONSTRAINT FK_temp_timetable_timetable_id FOREIGN KEY (timetable_id) REFERENCES timetable (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS class_professor (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  institute_id bigint(20) NOT NULL,
  class_id bigint(20) NOT NULL,
  section_id bigint(20),
  professor_id bigint(20) NOT NULL,
  professor_category enum('permanent', 'guest') DEFAULT 'permanent' NOT NULL,
  is_active tinyint(1) DEFAULT 1,
  added_by varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_class_professor_institute_id (institute_id),
  CONSTRAINT FK_class_professor_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id),
  KEY FK_class_professor_class_id (class_id),
  CONSTRAINT FK_class_professor_class_id FOREIGN KEY (class_id) REFERENCES class (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS class_curriculum (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  class_professor_id bigint(20) NOT NULL,
  subject_id bigint(20) NOT NULL,
  timetable_id bigint(20),
  professor_slot_category enum('permanent', 'guest') DEFAULT 'permanent' NOT NULL,
  slot_allocated tinyint(1) DEFAULT 0,
  day enum('sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday') DEFAULT 'sunday' NOT NULL,
  start_time varchar(20) NOT NULL,
  end_time varchar(20) NOT NULL,
  start_date varchar(20),
  end_date varchar(20),
  is_active tinyint(1) DEFAULT 1,
  updated_by varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_class_curriculum_class_professor_id (class_professor_id),
  CONSTRAINT FK_class_curriculum_class_professor_id FOREIGN KEY (class_professor_id) REFERENCES class_professor (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS attendance (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  attendance_date date NOT NULL,
  institute_id bigint(20) NOT NULL,
  class_id bigint(20) NOT NULL,
  section_id bigint(20),
  period_id bigint(20) NOT NULL,
  subject_id bigint(20) NOT NULL,
  students_attendance longtext DEFAULT NULL,
  attendance_taken_by varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  attendance_updated_by varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  no_of_present_students INT,
  no_of_absent_students INT,
  total_no_of_students INT,
  is_active tinyint(1) DEFAULT 1,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_attendance_institute_id (institute_id),
  CONSTRAINT FK_attendance_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id),
  KEY FK_attendance_class_id (class_id),
  CONSTRAINT FK_attendance_class_id FOREIGN KEY (class_id) REFERENCES class (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
# --- !Downs
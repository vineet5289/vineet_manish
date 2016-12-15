# --- !Ups
CREATE TABLE IF NOT EXISTS subject (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  subject_name varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  class_id bigint(20) NOT NULL,
  section_id bigint(20) NOT NULL,
  institute_id bigint(20) NOT NULL,
  subject_code varchar(20) COLLATE utf8_unicode_ci,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  created_by varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  description varchar(255) COLLATE utf8_unicode_ci,
  recommended_book varchar(255) COLLATE utf8_unicode_ci,
  PRIMARY KEY (id),
  KEY FK_subject_class_id (class_id),
  CONSTRAINT FK_subject_class_id FOREIGN KEY (class_id) REFERENCES class (id),
  KEY FK_subject_section_id (section_id),
  CONSTRAINT FK_subject_section_id FOREIGN KEY (section_id) REFERENCES class (id),
  KEY FK_subject_institute_id (institute_id),
  CONSTRAINT FK_subject_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS timetable (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  day enum('sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday') DEFAULT 'sunday' NOT NULL,
  period_no INT NOT NULL,
  start_time varchar(20) NOT NULL,
  end_time varchar(20) NOT NULL,
  institute_id bigint(20) NOT NULL,
  class_id bigint(20) NOT NULL,
  section_id bigint(20),
  professor_id bigint(20),
  subject_id  bigint(20),
  time_table_updated_by varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  is_active tinyint(1) DEFAULT 1,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_timetable_institute_id (institute_id),
  CONSTRAINT FK_timetable_institute_id FOREIGN KEY (institute_id) REFERENCES institute (id),
  KEY FK_timetable_class_id (class_id),
  CONSTRAINT FK_timetable_class_id FOREIGN KEY (class_id) REFERENCES class (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS attendance (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  attendance_date date NOT NULL,
  institute_id bigint(20) NOT NULL,
  class_id bigint(20) NOT NULL,
  section_id bigint(20),
  attendance_value longtext COLLATE utf8_unicode_ci,
  attendance_type enum('onceinaday', 'twiceaday', 'periodwise', 'notspecific') DEFAULT 'onceinaday' NOT NULL,
  period_id bigint(20),
  no_of_present_students INT,
  no_of_absent_students INT,
  total_no_of_students INT,
  attendance_taken_by varchar(150) COLLATE utf8_unicode_ci NOT NULL,
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
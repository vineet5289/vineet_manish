# --- !Ups

CREATE TABLE IF NOT EXISTS subject (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  subject_name varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  class_id bigint(20) NOT NULL,
  school_id bigint(20) NOT NULL,
  subject_code varchar(20) COLLATE utf8_unicode_ci,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1,
  user_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id),
  KEY FK_subject_class_id (class_id),
  CONSTRAINT FK_subject_class_id FOREIGN KEY (class_id) REFERENCES class (id),
  KEY FK_subject_school_id (school_id),
  CONSTRAINT FK_subject_school_id FOREIGN KEY (school_id) REFERENCES school (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS time_table (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  lecture_name varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  lecture_start_time varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  lecture_end_time varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  lecture_start_day varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  lecture_end_day varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  class_id bigint(20) NOT NULL,
  teacher_id bigint(20) NOT NULL,
  subject_id bigint(20) NOT NULL,
  is_active tinyint(1) DEFAULT 1,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY FK_time_table_class_id (class_id),
  CONSTRAINT FK_time_table_class_id FOREIGN KEY (class_id) REFERENCES class (id),
  KEY FK_time_table_teacher_id (teacher_id),
  CONSTRAINT FK_time_table_teacher_id FOREIGN KEY (teacher_id) REFERENCES employee (id),
  KEY FK_time_table_subject_id (subject_id),
  CONSTRAINT FK_time_table_subject_id FOREIGN KEY (subject_id) REFERENCES subject (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# --- !Downs
# --- !Ups

CREATE TABLE IF NOT EXISTS school_shift_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  shift_name varchar(50) NOT NULL,
  shift_class_start_time varchar(10) NOT NULL,
  shift_class_end_time varchar(10) NOT NULL,
  shift_week_start_time varchar(10) NOT NULL,
  shift_week_end_time varchar(10) NOT NULL,
  shift_start_class_name varchar(10) NOT NULL,
  shift_end_class_name varchar(10) NOT NULL,
  shift_attendence_type enum('ONCE_DAY', 'TWICE_DAY', 'EVERY_PERIOD') DEFAULT 'ONCE_DAY' NOT NULL,
  school_id bigint(20) NOT NULL,
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_active tinyint(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (id),
  KEY FK_school_shift_info_school_id (school_id),
  CONSTRAINT FK_school_shift_info_school_id FOREIGN KEY (school_id) REFERENCES school (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# --- !Downs

drop table school_shift_info;

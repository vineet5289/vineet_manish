# --- !Ups

CREATE TABLE IF NOT EXISTS school_shift_info (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  shift_name varchar(50) NOT NULL,
  shift_class_start_time varchar(10) NOT NULL,
  shift_class_end_time varchar(10) NOT NULL,
  shift_start_date date NOT NULL,
  shift_end_date date NOT NULL,
  shift_start_Month varchar(10) NOT NULL,
  shift_end_Month varchar(10) NOT NULL,
  shift_week_start_time varchar(10) NOT NULL,
  shift_week_end_time varchar(10) NOT NULL,
  shift_start_class_name varchar(10) NOT NULL,
  shift_end_class_name varchar(10) NOT NULL,
  shift_attendence_type enum('ONCE_DAY', 'TWICE_DAY', 'EVERY_PERIOD') DEFAULT 'REQUESTED' NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# --- !Downs

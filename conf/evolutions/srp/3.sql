# --- !Ups
CREATE TABLE IF NOT EXISTS message (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  school_id bigint(20) NOT NULL,
  message longtext COLLATE utf8_unicode_ci NOT NULL,
  receiver_ids text COLLATE utf8_unicode_ci, 
  class_ids text COLLATE utf8_unicode_ci,
  is_for_parents tinyint(1) DEFAULT 0,
  is_for_teachers tinyint(1) DEFAULT 0,
  is_for_students tinyint(1) DEFAULT 0,
  is_for_school tinyint(1) DEFAULT 0, 
  created_at timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS user_super_user (
  super_user_name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  school_id bigint(20) NOT NULL,
  user_name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  name varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  is_active tinyint(1) DEFAULT 1,
  PRIMARY KEY (super_user_name, user_name),
  KEY FK_user_super_user_school_id (school_id),
  CONSTRAINT FK_user_super_user_school_id FOREIGN KEY (school_id) REFERENCES school (id),
  KEY FK_user_super_user_name (super_user_name),
  CONSTRAINT FK_user_super_user_name FOREIGN KEY (super_user_name) REFERENCES login (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

# --- !Downs
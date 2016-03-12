# --- !Ups

CREATE TABLE IF NOT EXISTS school_registration_request (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  school_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  principal_name varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  principal_email varchar(255) COLLATE utf8_unicode_ci,
  school_address varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  contact varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  school_registration_id varchar(20) COLLATE utf8_unicode_ci,
  query varchar(20) COLLATE utf8_unicode_ci,
  requested_at timestamp DEFAULT CURRENT_TIMESTAMP,
  status enum('REQUESTED', 'REJECTED', 'APPROVED', 'REGISTERED'),
  status_updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  auth_token varchar(255) COLLATE utf8_unicode_ci,
  auth_token_genereated_at timestamp,
  request_number varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY(request_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


# --- !Downs

drop table class;
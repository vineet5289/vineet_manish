# --- !Ups

ALTER TABLE school_registration_request ADD alert_done tinyint(1) DEFAULT 0;

# --- !Downs

ALTER TABLE school_registration_request DROP COLUMN alert_done
ALTER TABLE response_for_session
  MODIFY COLUMN comment VARCHAR(768);
ALTER TABLE response_for_seminar
  MODIFY COLUMN comment VARCHAR(768);
ALTER TABLE response_for_seminar
  MODIFY COLUMN request VARCHAR(768);
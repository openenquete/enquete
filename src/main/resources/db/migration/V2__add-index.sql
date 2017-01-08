ALTER TABLE response_for_seminar
  ADD INDEX username(username);

ALTER TABLE response_for_session
  ADD INDEX username(username);

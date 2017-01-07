CREATE TABLE seminar (
  seminar_id   BINARY(16)   NOT NULL,
  seminar_date DATETIME     NOT NULL,
  seminar_name VARCHAR(255) NOT NULL,
  created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (seminar_id)
)
  ENGINE InnoDB;

CREATE TABLE session (
  session_id   BINARY(16)   NOT NULL,
  session_name VARCHAR(255) NOT NULL,
  seminar_id   BINARY(16),
  created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (session_id),
  FOREIGN KEY (seminar_id) REFERENCES seminar (seminar_id)
)
  ENGINE InnoDB;

CREATE TABLE seminar_closed (
  closed_id  BINARY(16) NOT NULL,
  seminar_id BINARY(16) NOT NULL,
  username   VARCHAR(255),
  created_at DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (closed_id),
  FOREIGN KEY (seminar_id) REFERENCES seminar (seminar_id)
)
  ENGINE InnoDB;

CREATE TABLE response_for_seminar (
  response_for_seminar_id BINARY(16)   NOT NULL,
  comment                 VARCHAR(255),
  ip_address              VARCHAR(255),
  request                 VARCHAR(255),
  satisfaction            VARCHAR(255) NOT NULL,
  username                VARCHAR(255),
  seminar_id              BINARY(16)   NOT NULL,
  created_at              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (response_for_seminar_id),
  FOREIGN KEY (seminar_id) REFERENCES seminar (seminar_id)
);

CREATE TABLE response_for_session (
  response_for_session_id BINARY(16)   NOT NULL,
  comment                 VARCHAR(255),
  difficulty              VARCHAR(255) NOT NULL,
  ip_address              VARCHAR(255),
  satisfaction            VARCHAR(255) NOT NULL,
  username                VARCHAR(255),
  session_id              BINARY(16)   NOT NULL,
  created_at              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at              DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (response_for_session_id),
  FOREIGN KEY (session_id) REFERENCES session (session_id)
);

CREATE TABLE session_speaker_display_names (
  session_id           BINARY(16) NOT NULL,
  speaker_display_name VARCHAR(255),
  FOREIGN KEY (session_id) REFERENCES session (session_id)
)
  ENGINE InnoDB;

CREATE TABLE session_speakers (
  session_id BINARY(16) NOT NULL,
  speaker    VARCHAR(255),
  FOREIGN KEY (session_id) REFERENCES session (session_id)
)
  ENGINE InnoDB;

ALTER TABLE seminar
  ADD INDEX seminar_date(seminar_date);

ALTER TABLE session_speakers
  ADD INDEX speaker(speaker);

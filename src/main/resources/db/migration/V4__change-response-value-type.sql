-- Create new columns

ALTER TABLE response_for_seminar
  ADD satisfaction_v TINYINT NOT NULL DEFAULT 0;
ALTER TABLE response_for_session
  ADD satisfaction_v TINYINT NOT NULL DEFAULT 0;
ALTER TABLE response_for_session
  ADD difficulty_v TINYINT NOT NULL DEFAULT 0;

-- Migrate values

UPDATE response_for_seminar
SET satisfaction_v =
(CASE WHEN satisfaction = 'EXCELLENT'
  THEN 5
 WHEN satisfaction = 'GOOD'
   THEN 4
 WHEN satisfaction = 'NOT_BAD'
   THEN 3
 WHEN satisfaction = 'BAD'
   THEN 2
 WHEN satisfaction = 'TERRIBLE'
   THEN 1
 ELSE 0
 END);

UPDATE response_for_session
SET satisfaction_v  =
(CASE WHEN satisfaction = 'EXCELLENT'
  THEN 5
 WHEN satisfaction = 'GOOD'
   THEN 4
 WHEN satisfaction = 'NOT_BAD'
   THEN 3
 WHEN satisfaction = 'BAD'
   THEN 2
 WHEN satisfaction = 'TERRIBLE'
   THEN 1
 ELSE 0
 END), difficulty_v =
(CASE WHEN difficulty = 'VERY_HARD'
  THEN 5
 WHEN difficulty = 'HARD'
   THEN 4
 WHEN difficulty = 'MODERATE'
   THEN 3
 WHEN difficulty = 'EASY'
   THEN 2
 WHEN difficulty = 'VERY_EASY'
   THEN 1
 ELSE 0
 END);

-- Drop old columns

ALTER TABLE response_for_seminar
  DROP satisfaction;
ALTER TABLE response_for_session
  DROP satisfaction;
ALTER TABLE response_for_session
  DROP difficulty;

-- Rename new columns

ALTER TABLE response_for_seminar
  CHANGE satisfaction_v satisfaction TINYINT NOT NULL DEFAULT 0;
ALTER TABLE response_for_session
  CHANGE satisfaction_v satisfaction TINYINT NOT NULL DEFAULT 0;
ALTER TABLE response_for_session
  CHANGE difficulty_v difficulty TINYINT NOT NULL DEFAULT 0;
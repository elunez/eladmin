-- Add tags column to event table
ALTER TABLE event
    ADD COLUMN poster_image VARCHAR(255),
    ADD COLUMN tags VARCHAR(255) NULL COMMENT 'Tags stored as comma-delimited string',
    ADD COLUMN current_participants INT DEFAULT 0 COMMENT 'Current number of participants',
    ADD COLUMN max_participants INT NULL COMMENT 'Maximum number of participants';

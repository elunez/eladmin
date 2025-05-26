-- Add tags column to event table
ALTER TABLE event
    add column poster_image varchar(255),
    ADD COLUMN tags         VARCHAR(255) NULL COMMENT 'Tags stored as comma-delimited string';

-- Add check_in_at and group_count columns to event table
ALTER TABLE event 
ADD COLUMN check_in_at TIMESTAMP NULL COMMENT 'Check-in time',
ADD COLUMN group_count INT NULL COMMENT 'Number of groups';

-- Add rating_history table
CREATE TABLE rating_history
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id   BIGINT       NOT NULL COMMENT 'Player ID',
    rate_score  DOUBLE       NULL COMMENT 'Rating score',
    changes     DOUBLE       NULL COMMENT 'Score changes',
    create_time TIMESTAMP    NULL COMMENT 'Creation time',
    match_id    BIGINT       NULL COMMENT 'Match ID',
    CONSTRAINT fk_rating_history_player FOREIGN KEY (player_id) REFERENCES player (id),
    CONSTRAINT fk_rating_history_match FOREIGN KEY (match_id) REFERENCES `match` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Rating History';

-- Create index for fast lookup by player and match
CREATE INDEX idx_rating_history_player ON rating_history (player_id);
CREATE INDEX idx_rating_history_match ON rating_history (match_id);

-- Update event_organizer table with additional fields
ALTER TABLE event_organizer
    ADD COLUMN description VARCHAR(255) NULL COMMENT 'Description',
ADD COLUMN create_time TIMESTAMP NULL COMMENT 'Creation time',
ADD COLUMN update_time TIMESTAMP NULL COMMENT 'Update time';

-- Create index for faster lookup by user_id
CREATE INDEX idx_event_organizer_user ON event_organizer (user_id);


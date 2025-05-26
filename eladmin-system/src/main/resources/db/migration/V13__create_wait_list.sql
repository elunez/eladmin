-- Create wait list table for tracking players waiting to join events
CREATE TABLE IF NOT EXISTS wait_list (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id      BIGINT       NOT NULL COMMENT 'Reference to event',
    player_id     BIGINT       NOT NULL COMMENT 'Reference to player',
    notes         VARCHAR(255) NULL COMMENT 'Notes',
    status        VARCHAR(20)  NOT NULL DEFAULT 'WAITING' COMMENT 'Status (WAITING, PROMOTED, CANCELLED, EXPIRED)',
    create_time   DATETIME     NULL COMMENT 'Creation time',
    update_time   DATETIME     NULL COMMENT 'Update time',
    CONSTRAINT fk_wait_list_event FOREIGN KEY (event_id) REFERENCES event (id),
    CONSTRAINT fk_wait_list_player FOREIGN KEY (player_id) REFERENCES player (id),
    UNIQUE KEY uk_wait_list_event_player (event_id, player_id)
) ENGINE = InnoDB COMMENT 'Event wait list';

-- Add index for faster queries
CREATE INDEX idx_wait_list_event ON wait_list (event_id);
CREATE INDEX idx_wait_list_player ON wait_list (player_id);
CREATE INDEX idx_wait_list_status ON wait_list (status);

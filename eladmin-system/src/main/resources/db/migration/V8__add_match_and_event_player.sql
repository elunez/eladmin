-- Create event_player join table for co-host players
CREATE TABLE event_co_host_player (
    event_id BIGINT,
    player_id BIGINT,
    PRIMARY KEY (event_id, player_id),
    CONSTRAINT fk_event_player_event FOREIGN KEY (event_id) REFERENCES event(id),
    CONSTRAINT fk_event_player_player FOREIGN KEY (player_id) REFERENCES player(id)
);

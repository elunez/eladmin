-- Create event_player join table for co-host players
CREATE TABLE event_co_host_player
(
    event_id  BIGINT,
    player_id BIGINT,
    PRIMARY KEY (event_id, player_id),
    CONSTRAINT fk_event_player_event FOREIGN KEY (event_id) REFERENCES event (id),
    CONSTRAINT fk_event_player_player FOREIGN KEY (player_id) REFERENCES player (id)
);

-- Create match_group table
CREATE TABLE match_group
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(255),
    event_id        BIGINT,
    group_team_size INT DEFAULT 2,
    CONSTRAINT fk_match_group_event FOREIGN KEY (event_id) REFERENCES event (id)
);

-- Create match table
CREATE TABLE ` match `
(
    id
    BIGINT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    match_group_id
    BIGINT,
    team_a_id
    BIGINT,
    team_b_id
    BIGINT,
    score_a
    INT
    DEFAULT
    0,
    score_b
    INT
    DEFAULT
    0,
    team_a_win
    BIT
    DEFAULT
    0,
    team_b_win
    BIT
    DEFAULT
    0,
    score_verified
    BIT
    DEFAULT
    0,
    CONSTRAINT
    fk_match_group
    FOREIGN
    KEY
(
    match_group_id
) REFERENCES match_group
(
    id
),
    CONSTRAINT fk_team_a FOREIGN KEY
(
    team_a_id
) REFERENCES team
(
    id
),
    CONSTRAINT fk_team_b FOREIGN KEY
(
    team_b_id
) REFERENCES team
(
    id
)
    );

-- Add match_group_id column to team table
ALTER TABLE team
    ADD COLUMN match_group_id BIGINT,
    ADD CONSTRAINT fk_team_match_group FOREIGN KEY (match_group_id) REFERENCES match_group (id);


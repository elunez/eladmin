alter table event
    add column status varchar(255),
    add column is_public bit default 0,
    add column allow_wait_list bit default 0;

CREATE TABLE team (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_id BIGINT references event(id),
    name VARCHAR(255),
    team_size INT default 2
);

CREATE TABLE team_player (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    team_id BIGINT,
    player_id BIGINT,
    score DOUBLE DEFAULT NULL,
    is_checked_in BIT DEFAULT 0,
    CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES team(id),
    CONSTRAINT fk_player FOREIGN KEY (player_id) REFERENCES player(id)
);

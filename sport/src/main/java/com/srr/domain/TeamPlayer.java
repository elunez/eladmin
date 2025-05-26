package com.srr.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "team_player")
public class TeamPlayer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "score")
    private Double score;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "is_checked_in")
    private boolean isCheckedIn;
}

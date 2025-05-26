package com.srr.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
public class Match implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "match_group_id")
    private MatchGroup matchGroup;

    @ManyToOne
    @JoinColumn(name = "team_a_id")
    private TeamPlayer teamA;

    @Column(name = "`score_a`")
    @ApiModelProperty(value = "Score A")
    private int scoreA;

    @Column(name = "`team_a_win`")
    private boolean teamAWin;

    @Column(name = "`score_b`")
    @ApiModelProperty(value = "Score B")
    private int scoreB;

    @Column(name = "`team_b_win`")
    private boolean teamBWin;

    @ManyToOne
    @JoinColumn(name = "team_b_id")
    private Team teamB;

    @Column(name = "`score_verified`")
    private boolean scoreVerified;
}

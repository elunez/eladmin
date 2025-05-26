package com.srr.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "`team`")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @Column(name = "`name`")
    @ApiModelProperty(value = "Name")
    private String name;

    @Column(name = "`team_size`")
    @ApiModelProperty(value = "Team size")
    private int teamSize;

    @OneToMany(mappedBy = "team")
    @JoinTable(name = "team_players",
            joinColumns = {@JoinColumn(name = "team_id",referencedColumnName = "id")})
    @ApiModelProperty(value = "Players")
    private List<Player> players; // <Player>
}

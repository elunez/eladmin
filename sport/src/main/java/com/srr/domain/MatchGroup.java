package com.srr.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class MatchGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(value = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // how many teams in one group
    @Column(name = "group_team_size")
    private int groupTeamSize;

    @OneToMany(mappedBy = "matchGroup")
    private List<Team> teams = new ArrayList<>();
}

package com.srr.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    
    @PrePersist
    @PreUpdate
    public void updateTeamScore() {
        if (team != null) {
            calculateAndUpdateTeamScore(team);
        }
    }
    
    /**
     * Calculates and updates the average score for the team
     * @param team The team to update the score for
     */
    private void calculateAndUpdateTeamScore(Team team) {
        List<TeamPlayer> players = team.getTeamPlayers();
        if (players == null || players.isEmpty()) {
            team.setAverageScore(0.0);
            return;
        }
        
        double totalScore = 0;
        int playerCount = 0;
        
        for (TeamPlayer player : players) {
            if (player.getScore() != null) {
                totalScore += player.getScore();
                playerCount++;
            }
        }
        
        double averageScore = playerCount > 0 ? totalScore / playerCount : 0;
        team.setAverageScore(averageScore);
    }
}

package com.srr.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JoinEventDto {
    private Long playerId;
    @NotNull
    private Long eventId;
    private Long teamId;
}

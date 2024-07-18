package org.depromeet.spot.application.team.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateHomeTeamReq(@NotEmpty @NotNull Set<@Positive @NotNull Long> teamIds) {}

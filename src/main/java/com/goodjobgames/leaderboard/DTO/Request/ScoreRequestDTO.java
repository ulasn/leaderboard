package com.goodjobgames.leaderboard.DTO.Request;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScoreRequestDTO {

    private Float score_worth;

    private String display_name;
}

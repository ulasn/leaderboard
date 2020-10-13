package com.goodjobgames.leaderboard.DTO.Response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScoreResponseDTO {

    private Float score_worth;

    private String user_id;

    private Long timestamp;

    public ScoreResponseDTO() {
    }

    public ScoreResponseDTO(Float score_worth, String user_id, Long timestamp) {
        this.score_worth = score_worth;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }
}

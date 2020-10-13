package com.goodjobgames.leaderboard.DTO.Response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDTO {

    private String user_id;

    private String display_name;

    private Float points;

    private Integer rank;

    public UserResponseDTO() {
    }

    public UserResponseDTO(String user_id, String display_name, Float points, Integer rank) {
        this.user_id = user_id;
        this.display_name = display_name;
        this.points = points;
        this.rank = rank;
    }
}

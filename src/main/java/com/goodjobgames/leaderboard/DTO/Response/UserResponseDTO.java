package com.goodjobgames.leaderboard.DTO.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {

    private String user_id;

    private String display_name;

    private Integer points;

    private Integer rank;

    private String country;

    public UserResponseDTO() {
    }

    public UserResponseDTO(String user_id, String display_name, Integer points, Integer rank) {
        this.user_id = user_id;
        this.display_name = display_name;
        this.points = points;
        this.rank = rank;
    }
}

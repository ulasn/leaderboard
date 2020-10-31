package com.goodjobgames.leaderboard.DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewUserRequestDTO {

    private String display_name;

    private String country_code;

    private Integer points;
}

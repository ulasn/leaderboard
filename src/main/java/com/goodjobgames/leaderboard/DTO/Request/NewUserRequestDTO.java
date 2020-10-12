package com.goodjobgames.leaderboard.DTO.Request;

import com.goodjobgames.leaderboard.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewUserRequestDTO {

    private String user_id;

    private String display_name;

    private Float points;

    private Integer rank;

    private String country_code;
}

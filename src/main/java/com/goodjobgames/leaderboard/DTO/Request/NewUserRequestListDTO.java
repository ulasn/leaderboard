package com.goodjobgames.leaderboard.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class NewUserRequestListDTO {

    private Integer number_of_users;

    private List<NewUserRequestDTO> newUserList;
}

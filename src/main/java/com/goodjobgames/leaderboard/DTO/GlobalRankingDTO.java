package com.goodjobgames.leaderboard.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalRankingDTO {

    private List<UserDTO> userList = new ArrayList<>();

    public void addUser (UserDTO userDTO){
        this.userList.add(userDTO);
    }
}

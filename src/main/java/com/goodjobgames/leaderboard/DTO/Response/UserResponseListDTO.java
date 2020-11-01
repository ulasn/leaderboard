package com.goodjobgames.leaderboard.DTO.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserResponseListDTO {

    List<UserResponseDTO> userList = new ArrayList<>();

    public void addUser (UserResponseDTO userDTO){
        this.userList.add(userDTO);
    }

}

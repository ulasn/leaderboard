package com.goodjobgames.leaderboard.DTO.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goodjobgames.leaderboard.DTO.UserDTO;
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

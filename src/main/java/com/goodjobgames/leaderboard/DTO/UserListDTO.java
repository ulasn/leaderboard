package com.goodjobgames.leaderboard.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserListDTO {

    private List<UserDTO> userDTOList = new ArrayList<>();


    public void addUser (UserDTO userDTO){
        this.userDTOList.add(userDTO);
    }
}

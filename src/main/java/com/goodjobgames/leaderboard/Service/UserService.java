package com.goodjobgames.leaderboard.Service;

import com.goodjobgames.leaderboard.DTO.Request.NewUserRequestDTO;
import com.goodjobgames.leaderboard.DTO.UserDTO;
import com.goodjobgames.leaderboard.DTO.UserListDTO;
import com.goodjobgames.leaderboard.Entity.User;
import com.goodjobgames.leaderboard.Exception.ServerErrorMessages;
import com.goodjobgames.leaderboard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public void createNewUser(NewUserRequestDTO newUserRequestDTO) {

        if(newUserRequestDTO.getDisplay_name() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.USERNAME_MISSING.getErrorMessage());
        }

        try{
            User user = new User(newUserRequestDTO.getDisplay_name());
            if(newUserRequestDTO.getUser_id() != null){
                user.setId(UUID.fromString(newUserRequestDTO.getUser_id()));
            }
            else{
                user.setId(UUID.randomUUID());
            }

            if(newUserRequestDTO.getPoints() != null){
                user.setPoints(newUserRequestDTO.getPoints());
            }

            if(newUserRequestDTO.getRank() != null){
                user.setRank(newUserRequestDTO.getRank());
            }

            if(newUserRequestDTO.getCountry_code() != null){
                user.setCountry(newUserRequestDTO.getCountry_code());
            }

            userRepository.save(user);
        }

        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ServerErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
    }


    public void getProfileInfo(Long guid) {
    }


    public UserListDTO getAllUsers() {
        Iterable<User> userList = userRepository.findAll();
        UserListDTO userListDTO = new UserListDTO();
        for(User user : userList){
            userListDTO.addUser(new UserDTO(
                    user.getId().toString(),
                    user.getName(),
                    user.getPoints(),
                    user.getRank()
            ));
        }
        return userListDTO;
    }
}

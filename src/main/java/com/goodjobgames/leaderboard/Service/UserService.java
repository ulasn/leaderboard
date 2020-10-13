package com.goodjobgames.leaderboard.Service;

import com.goodjobgames.leaderboard.DTO.Request.NewUserRequestDTO;
import com.goodjobgames.leaderboard.DTO.Response.UserResponseDTO;
import com.goodjobgames.leaderboard.DTO.UserDTO;
import com.goodjobgames.leaderboard.DTO.UserListDTO;
import com.goodjobgames.leaderboard.Entity.User;
import com.goodjobgames.leaderboard.Exception.ServerErrorMessages;
import com.goodjobgames.leaderboard.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public UserResponseDTO createNewUser(NewUserRequestDTO newUserRequestDTO) {

        if(newUserRequestDTO.getDisplay_name() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.USERNAME_MISSING.getErrorMessage());
        }

        Optional<User> checkUser = userRepository.findByName(newUserRequestDTO.getDisplay_name());
        if(checkUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    ServerErrorMessages.USER_EXISTS.getErrorMessage());
        }

        try{
            User user = new User(newUserRequestDTO.getDisplay_name());
            if(newUserRequestDTO.getCountry_code() != null){
                user.setCountry(newUserRequestDTO.getCountry_code());
            }

            userRepository.save(user);
            return new UserResponseDTO(user.getId().toString(),
                    user.getName(),
                    user.getPoints(),
                    user.getRank());
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ServerErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
    }


    public UserResponseDTO getProfileInfo(String guid) {
        Optional<User> user = userRepository.findById(UUID.fromString(guid));
        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    ServerErrorMessages.WRONG_GUID.getErrorMessage());
        }
        return new UserResponseDTO(user.get().getId().toString(),
                user.get().getName(),
                user.get().getPoints(),
                user.get().getRank());
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

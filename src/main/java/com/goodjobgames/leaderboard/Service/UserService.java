package com.goodjobgames.leaderboard.Service;

import com.goodjobgames.leaderboard.DTO.Request.NewUserRequestDTO;
import com.goodjobgames.leaderboard.DTO.Request.NewUserRequestListDTO;
import com.goodjobgames.leaderboard.DTO.Response.UserResponseDTO;
import com.goodjobgames.leaderboard.DTO.Response.UserResponseListDTO;
import com.goodjobgames.leaderboard.DTO.UserDTO;
import com.goodjobgames.leaderboard.DTO.UserListDTO;
import com.goodjobgames.leaderboard.Entity.User;
import com.goodjobgames.leaderboard.Exception.ServerErrorMessages;
import com.goodjobgames.leaderboard.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.collections.DefaultRedisZSet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DefaultRedisZSet redisZSet;


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

            if(newUserRequestDTO.getPoints() != null){
                user.setPoints(newUserRequestDTO.getPoints().floatValue());
            }

            redisZSet.add(user.getId(), user.getPoints());
            userRepository.save(user);
            Integer rank = redisZSet.reverseRank(user.getId()).intValue() + 1;


            return new UserResponseDTO(user.getId(),
                    user.getName(),
                    user.getPoints().intValue(),
                    rank);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ServerErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
    }


    public UserResponseDTO getProfileInfo(String guid) {
        Optional<User> user = userRepository.findById(guid);
        Integer rank = redisZSet.reverseRank(guid).intValue() + 1;
        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    ServerErrorMessages.WRONG_GUID.getErrorMessage());
        }
        return new UserResponseDTO(user.get().getId(),
                user.get().getName(),
                user.get().getPoints().intValue(),
                rank);
    }


    public UserListDTO getAllUsers() {
        UserListDTO userListDTO = new UserListDTO();
        Iterator<String> ranks = redisZSet.reverseRange(0, -1).iterator();
        Integer rank = 0;
        while(ranks.hasNext()){
            rank++;
            Optional <User> user= userRepository.findById(ranks.next());
            userListDTO.addUser(new UserDTO(
                    rank,
                    user.get().getPoints().intValue(),
                    user.get().getName(),
                    user.get().getCountry()
                    )
            );
            ranks.remove();
        }
        return userListDTO;
    }


    public UserResponseListDTO createMultipleUsers(NewUserRequestListDTO newUserRequestListDTO) {
        UserResponseListDTO userListDTO = new UserResponseListDTO();
        if(newUserRequestListDTO.getNumber_of_users() != null){
            if(!newUserRequestListDTO.getNumber_of_users().equals(newUserRequestListDTO.getNewUserList().size())){
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        ServerErrorMessages.NO_OF_USERS_WRONG.getErrorMessage());
            }
        }

        try{
         for(NewUserRequestDTO userRequestDTO : newUserRequestListDTO.getNewUserList()){
            User user = new User(userRequestDTO.getDisplay_name());
            user.setCountry(userRequestDTO.getCountry_code());
            userRepository.save(user);
            redisZSet.add(user.getId(), user.getPoints());

             Integer rank = redisZSet.reverseRank(user.getId()).intValue() + 1;
             userListDTO.addUser(new UserResponseDTO(
                     user.getId(),
                     user.getName(),
                     user.getPoints().intValue(),
                     rank
                     )
             );
         }
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        return userListDTO;
    }

    public UserResponseListDTO createMultipleUsersWithPoints(NewUserRequestListDTO newUserRequestListDTO) {
        UserResponseListDTO userListDTO = new UserResponseListDTO();
        if(newUserRequestListDTO.getNumber_of_users() != null){
            if(!newUserRequestListDTO.getNumber_of_users().equals(newUserRequestListDTO.getNewUserList().size())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        ServerErrorMessages.NO_OF_USERS_WRONG.getErrorMessage());
            }
        }

        try{
            for(NewUserRequestDTO userRequestDTO : newUserRequestListDTO.getNewUserList()){
                User user = new User(userRequestDTO.getDisplay_name());
                user.setCountry(userRequestDTO.getCountry_code());
                user.setPoints(userRequestDTO.getPoints().floatValue());
                userRepository.save(user);
                redisZSet.add(user.getId(), user.getPoints());

                Integer rank = redisZSet.reverseRank(user.getId()).intValue() + 1;
                userListDTO.addUser(new UserResponseDTO(
                                user.getId(),
                                user.getName(),
                                user.getPoints().intValue(),
                                rank
                        )
                );
            }
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        return userListDTO;
    }
}

package com.goodjobgames.leaderboard.Service;

import com.goodjobgames.leaderboard.DTO.Request.ScoreRequestDTO;
import com.goodjobgames.leaderboard.Entity.User;
import com.goodjobgames.leaderboard.Exception.ServerErrorMessages;
import com.goodjobgames.leaderboard.Repository.UserRepository;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {

    @Autowired
    private UserRepository userRepository;

    public void saveNewScore(ScoreRequestDTO scoreRequestDTO) {

        if(scoreRequestDTO.getUser_id() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.USER_ID_MISSING.getErrorMessage());
        }

        Optional<User> user = userRepository.findById(UUID.fromString(scoreRequestDTO.getUser_id()));
        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.WRONG_USER_ID.getErrorMessage());
        }

        if(scoreRequestDTO.getScore_worth() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.SCORE_MISSING.getErrorMessage());
        }

        if(scoreRequestDTO.getTimestamp() != null){
            user.get().setTimestamp(scoreRequestDTO.getTimestamp());
        }

        try{
            user.get().setPoints(user.get().getPoints() + scoreRequestDTO.getScore_worth());
            userRepository.save(user.get());
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    ServerErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }


    }
}

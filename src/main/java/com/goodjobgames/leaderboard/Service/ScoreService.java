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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {

    @Autowired
    private UserRepository userRepository;

    public void saveNewScore(ScoreRequestDTO scoreRequestDTO) {

        if(scoreRequestDTO.getDisplay_name() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.USERNAME_MISSING.getErrorMessage());
        }

        Optional<User> user = userRepository.findByName(scoreRequestDTO.getDisplay_name());
        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.WRONG_USERNAME.getErrorMessage());
        }

        if(scoreRequestDTO.getScore_worth() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ServerErrorMessages.SCORE_MISSING.getErrorMessage());
        }

        user.get().setTimestamp(System.currentTimeMillis());

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

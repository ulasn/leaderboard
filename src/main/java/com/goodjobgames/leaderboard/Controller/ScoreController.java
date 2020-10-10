package com.goodjobgames.leaderboard.Controller;

import com.goodjobgames.leaderboard.DTO.Request.ScoreRequestDTO;
import com.goodjobgames.leaderboard.Service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @RequestMapping(path="/submit", method = RequestMethod.POST)
    public ResponseEntity<?> scoreSubmission(ScoreRequestDTO scoreRequestDTO){
        scoreService.saveNewScore(scoreRequestDTO);
    }
}

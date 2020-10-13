package com.goodjobgames.leaderboard.Controller;

import com.goodjobgames.leaderboard.DTO.Request.ScoreRequestDTO;
import com.goodjobgames.leaderboard.DTO.Response.ScoreResponseDTO;
import com.goodjobgames.leaderboard.Service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @RequestMapping(path="/submit", method = RequestMethod.POST)
    @ResponseBody
    public ScoreResponseDTO scoreSubmission(@RequestBody ScoreRequestDTO scoreRequestDTO){
        return scoreService.saveNewScore(scoreRequestDTO);
    }
}

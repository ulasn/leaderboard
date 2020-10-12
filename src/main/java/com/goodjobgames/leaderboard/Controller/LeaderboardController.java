package com.goodjobgames.leaderboard.Controller;

import com.goodjobgames.leaderboard.DTO.CountryRankingDTO;
import com.goodjobgames.leaderboard.DTO.GlobalRankingDTO;
import com.goodjobgames.leaderboard.Service.LeaderboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @RequestMapping(path="", method = RequestMethod.GET)
    public GlobalRankingDTO getGlobalRankings(){
        leaderboardService.getGlobalRankings();
        return null;
    }


    @RequestMapping(path = "/{country_iso_code}", method = RequestMethod.GET)
    public CountryRankingDTO getCountryRankings(@PathVariable String isoCode){
        leaderboardService.getCountryRankings(isoCode);
        return null;
    }


}

package com.goodjobgames.leaderboard.Controller;

import com.goodjobgames.leaderboard.DTO.CountryRankingDTO;
import com.goodjobgames.leaderboard.DTO.GlobalRankingDTO;
import com.goodjobgames.leaderboard.Service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @RequestMapping(path="", method = RequestMethod.GET)
    public GlobalRankingDTO getGlobalRankings(){
        leaderboardService.getGlobalRankings();
    }


    @RequestMapping(path = "/{country_iso_code}", method = RequestMethod.GET)
    public CountryRankingDTO getCountryRankings(@PathVariable String isoCode){
        leaderboardService.getCountryRankings(isoCode);
    }


}

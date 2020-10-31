package com.goodjobgames.leaderboard.Controller;

import com.goodjobgames.leaderboard.DTO.CountryRankingDTO;
import com.goodjobgames.leaderboard.DTO.GlobalRankingDTO;
import com.goodjobgames.leaderboard.DTO.Request.RankingPageRequestDTO;
import com.goodjobgames.leaderboard.Service.LeaderboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @RequestMapping(path="", method = RequestMethod.GET)
    @ResponseBody
    public GlobalRankingDTO getGlobalRankings(){
        return leaderboardService.getGlobalRankings();
    }

    @RequestMapping(path="/range", method = RequestMethod.GET)
    @ResponseBody
    public GlobalRankingDTO getGlobalRankingsByRange(@RequestBody RankingPageRequestDTO rankingPageRequestDTO){
        return leaderboardService.getGlobalRankingsByRange(rankingPageRequestDTO);
    }

    @RequestMapping(path="/page/{page_number}", method = RequestMethod.GET)
    @ResponseBody
    public GlobalRankingDTO getGlobalRankingsByPage(@PathVariable Integer page_number){
        return leaderboardService.getGlobalRankingsByPage(page_number);
    }


    @RequestMapping(path = "/{country_iso_code}", method = RequestMethod.GET)
    @ResponseBody
    public CountryRankingDTO getCountryRankings(@PathVariable String country_iso_code){
        return leaderboardService.getCountryRankings(country_iso_code);
    }

    @RequestMapping(path = "/{country_iso_code}/{page_number}", method = RequestMethod.GET)
    @ResponseBody
    public CountryRankingDTO getCountryRankings(@PathVariable String country_iso_code, @PathVariable Integer page_number){
        return leaderboardService.getCountryRankingsByPage(country_iso_code, page_number);
    }


}

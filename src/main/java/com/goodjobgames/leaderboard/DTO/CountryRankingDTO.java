package com.goodjobgames.leaderboard.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CountryRankingDTO {

    List<UserDTO> countryRanking;

    public CountryRankingDTO() {
    }

    public CountryRankingDTO(List<UserDTO> countryRanking) {
        this.countryRanking = countryRanking;
    }
}

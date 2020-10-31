package com.goodjobgames.leaderboard.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Comparable<UserDTO>{

    private Integer rank;

    private Integer points;

    private String display_name;

    private String user_id;

    private String country;

    public UserDTO() {
    }

    public UserDTO(Integer rank, Integer points, String display_name, String country) {
        this.rank = rank;
        this.points = points;
        this.display_name = display_name;
        this.country = country;
    }

    public UserDTO(String user_id, String display_name, Integer points, Integer rank) {
        this.user_id = user_id;
        this.display_name = display_name;
        this.points = points;
        this.rank = rank;
    }

    @Override
    public int compareTo(UserDTO o) {
        return this.getRank().compareTo(o.getRank());
    }
}

package com.goodjobgames.leaderboard.Entity;

import com.goodjobgames.leaderboard.DTO.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("User")
@Getter @Setter
public class User implements Serializable, Comparable<User> {


    private String id = UUID.randomUUID().toString();

    @Indexed
    private String name;

    private Float points;

    @Indexed
    private String country;

    private long timestamp;

    public User(String name) {
        this.name = name;
        this.points = 0F;
    }

    @Override
    public int compareTo(User o) {
        return this.getPoints().compareTo(o.getPoints());
    }

}

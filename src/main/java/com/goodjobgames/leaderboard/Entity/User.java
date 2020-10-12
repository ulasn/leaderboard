package com.goodjobgames.leaderboard.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("User")
@Getter @Setter
public class User implements Serializable {

    private UUID id;

    private String name;

    private float points;

    private int rank;

    private String country;

    private long timestamp;

    public User(String name) {
        this.name = name;
        this.points = 0;
    }
}

package com.goodjobgames.leaderboard.Exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class RestExceptionResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();

    private int status;

    private String error;

    private String message;

    private String path;

    public static RestExceptionResponseBuilder builder() {
        return new RestExceptionResponseBuilder();
    }
}

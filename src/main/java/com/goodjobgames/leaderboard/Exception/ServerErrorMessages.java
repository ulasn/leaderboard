package com.goodjobgames.leaderboard.Exception;

public enum ServerErrorMessages {

    USERNAME_MISSING("No username was given"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    WRONG_USER_ID("No user was found with given id"),
    SCORE_MISSING("Score field is missing"),
    USER_ID_MISSING("User_id field is missing");

    private final String errorMessage;

    ServerErrorMessages(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}

package com.goodjobgames.leaderboard.Exception;

public enum ServerErrorMessages {

    USERNAME_MISSING("No username was given"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    WRONG_USERNAME("No user was found with given username"),
    SCORE_MISSING("Score field is missing"),
    USER_EXISTS("User exists with given username"),
    WRONG_GUID("No user exists with given guid");

    private final String errorMessage;

    ServerErrorMessages(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}

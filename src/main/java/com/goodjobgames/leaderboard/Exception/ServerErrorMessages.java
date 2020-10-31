package com.goodjobgames.leaderboard.Exception;

public enum ServerErrorMessages {

    USERNAME_MISSING("No username was given"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    WRONG_USERNAME("No user was found with given username"),
    SCORE_MISSING("Score field is missing"),
    USER_EXISTS("User exists with given username"),
    WRONG_GUID("No user exists with given guid"),
    NO_OF_USERS_WRONG("Number of records in json body does not match with given number_of_records field"),
    BAD_PAGE_REQUEST("Both fields must be present"),
    BAD_PAGE_REQUEST_CONSTRAINTS("Start field must be bigger than 0 and End field must be in range of leaderboard size which is: "),
    NO_OF_PAGES_WRONG("Page number overflow, page number cannot be bigger than(according to leaderboard size): "),
    PAGE_NUMBER_NEGATIVE("Page number must be bigger than 0");

    private final String errorMessage;

    ServerErrorMessages(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}

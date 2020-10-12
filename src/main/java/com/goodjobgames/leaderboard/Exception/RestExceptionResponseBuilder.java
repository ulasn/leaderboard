package com.goodjobgames.leaderboard.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class RestExceptionResponseBuilder {

    private int status;

    private String error;

    private String message;

    private String path;

    public RestExceptionResponseBuilder status(int status){
        this.status = status;
        return this;
    }

    public RestExceptionResponseBuilder status(HttpStatus httpStatus){
        this.status = httpStatus.value();

        if(httpStatus.isError()){
            this.error = httpStatus.getReasonPhrase();
        }
        return this;
    }

    public RestExceptionResponseBuilder exception(ResponseStatusException ex){
        HttpStatus status = ex.getStatus();
        this.status = status.value();

        if (!Objects.requireNonNull(ex.getReason()).isBlank()) {
            this.message = ex.getReason();
        }

        if(status.isError()){
            this.error = status.getReasonPhrase();
        }

        return this;
    }

    public RestExceptionResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public RestExceptionResponseBuilder path(String path) {
        this.path = path;
        return this;
    }

    public RestExceptionResponse build() {
        RestExceptionResponse response = new RestExceptionResponse();
        response.setStatus(status);
        response.setError(error);
        response.setMessage(message);
        response.setPath(path);
        return response;
    }

    public ResponseEntity<RestExceptionResponse> entity() {
        return ResponseEntity.status(status).headers(HttpHeaders.EMPTY).body(build());
    }

}

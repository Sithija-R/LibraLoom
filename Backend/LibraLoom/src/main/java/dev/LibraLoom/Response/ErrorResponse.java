package dev.LibraLoom.Response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private boolean status;

    public ErrorResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }


}

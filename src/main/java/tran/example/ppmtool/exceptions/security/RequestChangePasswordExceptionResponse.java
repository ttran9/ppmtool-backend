package tran.example.ppmtool.exceptions.security;

public class RequestChangePasswordExceptionResponse {
    private String username;

    public RequestChangePasswordExceptionResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


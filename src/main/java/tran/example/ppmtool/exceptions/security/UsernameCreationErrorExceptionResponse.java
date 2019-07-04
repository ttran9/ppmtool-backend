package tran.example.ppmtool.exceptions.security;

/**
 * A custom JSON object that indicates the attempted registered username cannot be created, mostly due to server error.
 */
public class UsernameCreationErrorExceptionResponse {
    private String username;

    public UsernameCreationErrorExceptionResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

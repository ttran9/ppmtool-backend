package tran.example.ppmtool.exceptions.security;

/**
 * A custom JSON object that will be returned when we are trying to activate a user but the token is not valid.
 */
public class EmailVerificationTokenExceptionResponse {

    private String token;

    public EmailVerificationTokenExceptionResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

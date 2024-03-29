package tran.example.ppmtool.exceptions.security;

/**
 * A custom JSON object that will be returned when we are trying to access a resource that requires authentication.
 */
public class InvalidLoginExceptionResponse {
    private String username;
    private String password;

    public InvalidLoginExceptionResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

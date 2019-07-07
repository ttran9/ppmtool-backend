package tran.example.ppmtool.exceptions.security;

/**
 * A custom JSON object that will be returned such as when we are trying to change the user's password but the
 * passwords don't match.
 */
public class ChangePasswordTokenExceptionResponse {
    private String password;
    private String confirmPassword;

    public ChangePasswordTokenExceptionResponse() {
        this.password = "passwords dont match";
        this.confirmPassword = "passwords dont match";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}


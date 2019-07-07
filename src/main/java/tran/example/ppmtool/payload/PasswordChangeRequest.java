package tran.example.ppmtool.payload;

import javax.validation.constraints.NotBlank;

/**
 * This request comes from the user when they enter in a new password and a confirm new password.
 */
public class PasswordChangeRequest {
    @NotBlank(message = "password cannot be blank")
    private String password;
    @NotBlank(message = "confirm password cannot be blank")
    private String confirmPassword;

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

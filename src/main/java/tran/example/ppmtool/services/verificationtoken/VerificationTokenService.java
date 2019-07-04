package tran.example.ppmtool.services.verificationtoken;

import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.VerificationToken;

public interface VerificationTokenService {
    /**
     * Creates a token for the passed in user and returns that token if no errors.
     * @param user The user the token will be associated with.
     * @return The generated registration token used to activate the user's account.
     */
    String createVerificationToken(ApplicationUser user);

    /**
     * Returns a verification token (if valid) from the database.
     * @param verificationToken The token specified from the user attempting to have his/her account activated.
     * @return Grab the specified verification token from the database.
     */
    VerificationToken getVerificationToken(String verificationToken);

    /**
     * verifies the token (ensures the token is present and is not yet expired). if valid the associated user account will
     * be enabled (active).
     * @param token The token to be verified.
     * @return If the validation is successful the associated user is returned.
     */
    ApplicationUser validateVerificationToken(String token);
}

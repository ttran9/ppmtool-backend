package tran.example.ppmtool.services.applicationusers;

import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

import javax.servlet.http.HttpServletRequest;

public interface ApplicationUserService {

    /**
     * creates the user attempting to be registered.
     * @param newApplicationUser The user to be registered.
     * @param request Object containing information about from the HTTP request (such as URL information).
     * @return Returns the new user with fields entered.
     */
    ApplicationUser saveUser(ApplicationUser newApplicationUser, HttpServletRequest request);

    /**
     * Save (an existing registered user) with an updated "enabled" field set to true.
     * @param user The registered user to be updated.
     */
    void enableRegisteredUser(ApplicationUser user);

    /**
     * Delete the user if there is some kind of error sending the email with the token required for user registration.
     * @param newApplicationUser The user to be deleted.
     */
    void removeUser(ApplicationUser newApplicationUser);

    /**
     * Sends an email with the token required to activate the registered user's account.
     * @param registeredUser The object containing the registered user name (email).
     * @param request The request containing information about the app's URL.
     */
    void sendSuccessRegistrationEmail(ApplicationUser registeredUser, HttpServletRequest request);

    /**
     * Takes in a new password and a user to change the password for.
     * @param newPassword The new password the user is changing to.
     * @param user The user to change the password for.
     */
    void changeUserPassword(String newPassword, ApplicationUser user);

    /**
     * Sends a URL to change the user's account along with a token which is necessary to change the password.
     * @param userName The email account that the change password link will be emailed to
     * @param request The request containing information about the app's URL.
     */
    void sendPasswordChangeEmail(String userName, HttpServletRequest request);

}

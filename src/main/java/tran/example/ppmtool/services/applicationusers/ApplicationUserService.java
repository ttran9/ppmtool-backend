package tran.example.ppmtool.services.applicationusers;

import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

public interface ApplicationUserService {

    /**
     * creates the user attempting to be registered.
     * @param newApplicationUser The user to be registered.
     * @return Returns the new user with fields entered.
     */
    ApplicationUser saveUser(ApplicationUser newApplicationUser);
}

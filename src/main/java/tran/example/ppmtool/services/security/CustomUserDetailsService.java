package tran.example.ppmtool.services.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

public interface CustomUserDetailsService extends UserDetailsService {

    /**
     * Retrieves the user by the id in the database.
     * @param id The database id of the user to be retrieved.
     * @return Returns a user if there is no issue with retrieval and throws an exception if there is.
     */
    ApplicationUser loadApplicationUserById(Long id);
}

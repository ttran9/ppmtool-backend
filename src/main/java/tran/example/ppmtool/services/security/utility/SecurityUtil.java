package tran.example.ppmtool.services.security.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

public class SecurityUtil {

    public static String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal != null) {
                if(principal instanceof ApplicationUser) {
                    ApplicationUser user = (ApplicationUser) principal;
                    return user.getUsername();
                }
            }
        }
        return ""; // TODO: I may have to throw an exception if this empty string causes issues and handle it..
    }


}

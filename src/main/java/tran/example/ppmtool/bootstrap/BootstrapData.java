package tran.example.ppmtool.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.security.JwtTokenProvider;
import tran.example.ppmtool.services.applicationusers.ApplicationUserService;
import tran.example.ppmtool.services.security.ApplicationUserAuthenticationService;

import static tran.example.ppmtool.constants.users.UserConstants.*;

@Component
@Profile({"test"})
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {
    private ApplicationUserService applicationUserService;

    @Autowired
    public BootstrapData(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createUsers();
    }

    private void createUsers() {
        // create a user that is enabled
        ApplicationUser user = createUser(USER_NAME_ONE, USER_PASSWORD, FULL_NAME_ONE);

        // create a user with an account that is not enabled.
        ApplicationUser secondUser = createUser(USER_NAME_TWO, USER_PASSWORD, FULL_NAME_TWO);

        // save/create users
        applicationUserService.saveUser(user, null);
        applicationUserService.enableRegisteredUser(user);

        applicationUserService.saveUser(secondUser, null);

    }

    private ApplicationUser createUser(String userName, String password, String fullName) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setFullName(fullName);
        return user;
    }
}

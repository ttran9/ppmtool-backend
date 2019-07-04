package tran.example.ppmtool.services.applicationusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.VerificationToken;
import tran.example.ppmtool.event.OnRegistrationSuccessEvent;
import tran.example.ppmtool.exceptions.security.UsernameCreationErrorException;
import tran.example.ppmtool.exceptions.security.UsernameDuplicateException;
import tran.example.ppmtool.exceptions.security.VerificationTokenException;
import tran.example.ppmtool.repositories.applicationusers.ApplicationUserRepository;
import tran.example.ppmtool.repositories.security.VerificationTokenRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                                      VerificationTokenRepository verificationTokenRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public ApplicationUser saveUser(ApplicationUser newApplicationUser, HttpServletRequest request) {
        try {
            newApplicationUser.setPassword(bCryptPasswordEncoder.encode(newApplicationUser.getPassword()));
            // username has to be unique (custom exception if this is violated)
            newApplicationUser.setUsername(newApplicationUser.getUsername());

            // make sure that password and confirm password match.

            // we don't persist or show the confirm password.
            newApplicationUser.setConfirmPassword("");

            newApplicationUser.setEnabled(false); // by default a user account is not yet activated.

            ApplicationUser createdUser = applicationUserRepository.save(newApplicationUser);

            // send an email
            sendSuccessRegistrationEmail(newApplicationUser, request);

            return createdUser;
        } catch(UsernameDuplicateException | DataIntegrityViolationException ex) {
            throw new UsernameDuplicateException("Username '" + newApplicationUser.getUsername() + "' already exists");
        }
    }

    @Transactional
    @Override
    public void enableRegisteredUser(ApplicationUser user) {
        user.setEnabled(true);
        applicationUserRepository.save(user);
        // user is enabled so now delete the registration token required to register the user.
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
        if(verificationToken == null) {
            throw new VerificationTokenException("token is not present.");
        }
        verificationTokenRepository.deleteByToken(verificationToken.getToken());
    }

    @Override
    public void removeUser(ApplicationUser newApplicationUser) {
        VerificationToken token = verificationTokenRepository.findByUser(newApplicationUser);
        newApplicationUser.setToken(token);
        applicationUserRepository.delete(newApplicationUser);
    }

    @Override
    public void sendSuccessRegistrationEmail(ApplicationUser registeredUser, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() +  ":" + request.getServerPort();
        try {
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(registeredUser, request.getLocale(), appUrl));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            removeUser(registeredUser);
            throw new UsernameCreationErrorException("Username '" + registeredUser.getUsername() + "' cannot be created at this time");
        }
    }
}

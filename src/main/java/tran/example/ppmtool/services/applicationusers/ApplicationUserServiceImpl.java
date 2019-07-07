package tran.example.ppmtool.services.applicationusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Request;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.ChangePasswordToken;
import tran.example.ppmtool.domain.security.EmailVerificationToken;
import tran.example.ppmtool.event.OnPasswordResetRequestEvent;
import tran.example.ppmtool.event.OnRegistrationSuccessEvent;
import tran.example.ppmtool.exceptions.security.*;
import tran.example.ppmtool.repositories.applicationusers.ApplicationUserRepository;
import tran.example.ppmtool.repositories.security.ChangePasswordTokenRepository;
import tran.example.ppmtool.repositories.security.EmailVerificationTokenRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    private ChangePasswordTokenRepository changePasswordTokenRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public ApplicationUserServiceImpl(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                                      EmailVerificationTokenRepository emailVerificationTokenRepository,
                                      ChangePasswordTokenRepository changePasswordTokenRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.changePasswordTokenRepository = changePasswordTokenRepository;
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
        EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByUser(user);
        if(emailVerificationToken == null) {
            throw new EmailVerificationTokenException("token is not present.");
        }
        emailVerificationTokenRepository.deleteByToken(emailVerificationToken.getToken());
    }

    @Override
    public void removeUser(ApplicationUser newApplicationUser) {
        EmailVerificationToken token = emailVerificationTokenRepository.findByUser(newApplicationUser);
        newApplicationUser.setEmailVerificationToken(token);
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

    @Transactional
    @Override
    public void changeUserPassword(String newPassword, ApplicationUser user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            applicationUserRepository.save(user);
            ChangePasswordToken token = changePasswordTokenRepository.findByUser(user);
            changePasswordTokenRepository.deleteByToken(token.getToken());
        } catch(Exception ex) {
            throw new ChangePasswordTokenException("unable to change password.");
        }


    }

    @Override
    public void sendPasswordChangeEmail(String userName, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" + request.getServerName() +  ":" + request.getServerPort();
        ApplicationUser user = applicationUserRepository.findByUsername(userName);
        if(user == null) {
            throw new RequestChangePasswordException("The password cannot be updated at this time.");
        }
        try {
            eventPublisher.publishEvent(new OnPasswordResetRequestEvent(user, request.getLocale(), appUrl));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RequestChangePasswordException("Password for '" + user.getUsername() + "' cannot be changed at this time");
        }
    }
}

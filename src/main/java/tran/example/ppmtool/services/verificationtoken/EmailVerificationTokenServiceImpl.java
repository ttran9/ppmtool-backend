package tran.example.ppmtool.services.verificationtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.EmailVerificationToken;
import tran.example.ppmtool.exceptions.security.EmailVerificationTokenException;
import tran.example.ppmtool.repositories.security.EmailVerificationTokenRepository;

import java.util.Calendar;
import java.util.UUID;

@Service
public class EmailVerificationTokenServiceImpl implements VerificationTokenService {

    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    public EmailVerificationTokenServiceImpl(EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    @Override
    public String createVerificationToken(ApplicationUser user) {
        String token = UUID.randomUUID().toString();
        EmailVerificationToken newUserToken = new EmailVerificationToken(token, user);
        try {
            emailVerificationTokenRepository.save(newUserToken);
        } catch(Exception ex) {
            return null;
        }
        return token;
    }

    @Override
    public EmailVerificationToken getVerificationToken(String verificationToken) {
        return emailVerificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public ApplicationUser validateVerificationToken(String token) {
        EmailVerificationToken emailVerificationToken = getVerificationToken(token);
        if(emailVerificationToken == null) {
            throw new EmailVerificationTokenException("token is not present.");
        }
        // verify if the token is expired.
        Calendar calendar = Calendar.getInstance();
        if((emailVerificationToken.getExpirationDate().getTime()-calendar.getTime().getTime())<=0) {
            throw new EmailVerificationTokenException("token has expired, please request another token.");
        }
        return emailVerificationToken.getUser();
    }
}

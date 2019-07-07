package tran.example.ppmtool.services.verificationtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.ChangePasswordToken;
import tran.example.ppmtool.exceptions.security.ChangePasswordTokenException;
import tran.example.ppmtool.repositories.security.ChangePasswordTokenRepository;

import java.util.Calendar;
import java.util.UUID;

@Service
public class ChangePasswordTokenServiceImpl implements VerificationTokenService {

    private ChangePasswordTokenRepository changePasswordTokenRepository;

    @Autowired
    public ChangePasswordTokenServiceImpl(ChangePasswordTokenRepository changePasswordTokenRepository) {
        this.changePasswordTokenRepository = changePasswordTokenRepository;
    }

    @Override
    public String createVerificationToken(ApplicationUser user) {
        String token = UUID.randomUUID().toString();
        ChangePasswordToken changePasswordToken = new ChangePasswordToken(token, user);
        try {
            changePasswordTokenRepository.save(changePasswordToken);
        } catch(Exception ex) {
            return null;
        }
        return token;
    }

    @Override
    public ChangePasswordToken getVerificationToken(String verificationToken) {
        return changePasswordTokenRepository.findByToken(verificationToken);
    }

    @Override
    public ApplicationUser validateVerificationToken(String token) {
        ChangePasswordToken emailVerificationToken = getVerificationToken(token);
        if(emailVerificationToken == null) {
            throw new ChangePasswordTokenException("token is not present.");
        }
        // verify if the token is expired.
        Calendar calendar = Calendar.getInstance();
        if((emailVerificationToken.getExpirationDate().getTime()-calendar.getTime().getTime())<=0) {
            throw new ChangePasswordTokenException("token has expired, please request another token.");
        }
        return emailVerificationToken.getUser();
    }
}

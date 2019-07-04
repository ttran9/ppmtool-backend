package tran.example.ppmtool.services.verificationtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.VerificationToken;
import tran.example.ppmtool.exceptions.security.VerificationTokenException;
import tran.example.ppmtool.repositories.security.VerificationTokenRepository;

import java.util.Calendar;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public String createVerificationToken(ApplicationUser user) {
        String token = UUID.randomUUID().toString();
        VerificationToken newUserToken = new VerificationToken(token, user);
        try {
            verificationTokenRepository.save(newUserToken);
        } catch(Exception ex) {
            return null;
        }
        return token;
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public ApplicationUser validateVerificationToken(String token) {
        VerificationToken verificationToken = getVerificationToken(token);
        if(verificationToken == null) {
            throw new VerificationTokenException("token is not present.");
        }
        // verify if the token is expired.
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpirationDate().getTime()-calendar.getTime().getTime())<=0) {
            throw new VerificationTokenException("token has expired, please request another token.");
        }
        return verificationToken.getUser();
    }
}

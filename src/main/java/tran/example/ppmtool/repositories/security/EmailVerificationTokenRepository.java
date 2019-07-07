package tran.example.ppmtool.repositories.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.EmailVerificationToken;

@Repository
public interface EmailVerificationTokenRepository extends CrudRepository<EmailVerificationToken, Long> {

    EmailVerificationToken findByToken(String token);
    EmailVerificationToken findByUser(ApplicationUser user);
    void deleteByToken(String token);
}

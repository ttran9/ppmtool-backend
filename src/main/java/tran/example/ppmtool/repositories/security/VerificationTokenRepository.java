package tran.example.ppmtool.repositories.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.VerificationToken;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);
    VerificationToken findByUser(ApplicationUser user);
    void deleteByToken(String token);
}

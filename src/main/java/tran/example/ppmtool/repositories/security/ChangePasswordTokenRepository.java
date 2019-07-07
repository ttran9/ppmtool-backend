package tran.example.ppmtool.repositories.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.domain.security.ChangePasswordToken;

@Repository
public interface ChangePasswordTokenRepository extends CrudRepository<ChangePasswordToken, Long> {

    ChangePasswordToken findByToken(String token);
    ChangePasswordToken findByUser(ApplicationUser user);
    void deleteByToken(String token);
}

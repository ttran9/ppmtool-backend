package tran.example.ppmtool.domain.security;

import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "change_password_token")
public class ChangePasswordToken extends VerificationToken {

    public ChangePasswordToken() { super(); }

    public ChangePasswordToken(String token, ApplicationUser user) {
        super(token, user);
        this.expirationDate = super.calculateExpirationDate(this.EXPIRATION);
    }


}

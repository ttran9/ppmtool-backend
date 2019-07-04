package tran.example.ppmtool.domain.security;

import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="token")
    @NotBlank
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private ApplicationUser user;

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="expiration_date")
    private Date expirationDate;

    public VerificationToken() { }

    public VerificationToken(String token, ApplicationUser user) {
        Calendar calendar = Calendar.getInstance();
        this.token = token;
        this.user = user;
        this.createdDate = new Date(calendar.getTime().getTime());
        this.expirationDate = calculateExpiryDate(EXPIRATION);
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        // set an expiration date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes); // add 60 * 24 minutes or 1440 minutes (1 day)
        return new Date(calendar.getTime().getTime());
    }
}

package tran.example.ppmtool.domain.security;

import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@MappedSuperclass
public abstract class VerificationToken {
    protected int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name="token")
    @NotBlank
    protected String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    protected ApplicationUser user;

    @Column(name="created_date")
    protected Date createdDate;

    @Column(name="expiration_date")
    protected Date expirationDate;

    VerificationToken() { }

    VerificationToken(String token, ApplicationUser user) {
        Calendar calendar = Calendar.getInstance();
        this.token = token;
        this.user = user;
        this.createdDate = new Date(calendar.getTime().getTime());
    }

    public int getEXPIRATION() {
        return EXPIRATION;
    }

    public void setEXPIRATION(int EXPIRATION) {
        this.EXPIRATION = EXPIRATION;
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

    public Date calculateExpirationDate(int expirationTimeInMinutes) {
        // set an expiration date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expirationTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}

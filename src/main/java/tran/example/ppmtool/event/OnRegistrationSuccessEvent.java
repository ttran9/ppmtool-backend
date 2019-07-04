package tran.example.ppmtool.event;

import org.springframework.context.ApplicationEvent;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

import java.util.Locale;

public class OnRegistrationSuccessEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String appUrl;
    private Locale locale;
    private ApplicationUser user;

    public OnRegistrationSuccessEvent(ApplicationUser user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}

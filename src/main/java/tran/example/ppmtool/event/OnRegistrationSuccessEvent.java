package tran.example.ppmtool.event;

import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

import java.util.Locale;

public class OnRegistrationSuccessEvent extends AbstractApplicationEvent {

    public OnRegistrationSuccessEvent(ApplicationUser user, Locale locale, String appUrl) {
        super(user, locale, appUrl);
    }
}

package tran.example.ppmtool.event;

import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

import java.util.Locale;

public class OnPasswordResetRequestEvent extends AbstractApplicationEvent {
    public OnPasswordResetRequestEvent(ApplicationUser user, Locale locale, String appUrl) {
        super(user, locale, appUrl);
    }
}

package tran.example.ppmtool.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tran.example.ppmtool.event.utility.EventUtil;
import tran.example.ppmtool.services.verificationtoken.EmailVerificationTokenServiceImpl;

import static tran.example.ppmtool.constants.mapping.MappingConstants.CHANGE_PASSWORD_URL;
import static tran.example.ppmtool.constants.mapping.MappingConstants.CONFIRM_REGISTRATION_URL;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
    @Autowired
    private EmailVerificationTokenServiceImpl emailVerificationTokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    @Value("${message.registrationSuccessConfirmationLink}")
    private String registrationSuccessConfirmationLink;

    @Override
    public void onApplicationEvent(OnRegistrationSuccessEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationSuccessEvent event) {
        try {
            String subject = "Confirm Registration";
            EventUtil.sendEmail(event, emailVerificationTokenService, messageSource, registrationSuccessConfirmationLink,
                    mailSender, subject, CONFIRM_REGISTRATION_URL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UsernameNotFoundException("account cannot be created at this time");
        }
    }

}

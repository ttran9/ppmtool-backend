package tran.example.ppmtool.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.services.verificationtoken.VerificationTokenService;

@Component
public class RegistrationEmailListener implements ApplicationListener<OnRegistrationSuccessEvent> {
    @Autowired
    private VerificationTokenService verificationTokenService;

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
        ApplicationUser user = event.getUser();
        String token = verificationTokenService.createVerificationToken(user);

        String recipient = user.getUsername();
        String subject = "Registration Confirmation";
        String url = event.getAppUrl() + "/confirmRegistration/" + token;
        String emailContent = messageSource.getMessage(registrationSuccessConfirmationLink, null, event.getLocale());
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipient);
            email.setSubject(subject);
            email.setText(emailContent + "\n" + url);
            mailSender.send(email);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UsernameNotFoundException("account cannot be created at this time");
        }
    }
}

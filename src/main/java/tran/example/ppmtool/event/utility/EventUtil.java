package tran.example.ppmtool.event.utility;

import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.event.AbstractApplicationEvent;
import tran.example.ppmtool.services.verificationtoken.VerificationTokenService;

public class EventUtil {

    public static void sendEmail(AbstractApplicationEvent event, VerificationTokenService tokenService,
                                 MessageSource messageSource, String messsageContent,
                                 JavaMailSender mailSender, String subject, String urlPath) {
        ApplicationUser user = event.getUser();
        String token = tokenService.createVerificationToken(user);

        String recipient = user.getUsername();

        String url = event.getAppUrl() + urlPath + token;
        String emailContent = messageSource.getMessage(messsageContent, null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(emailContent + "\n" + url);
        mailSender.send(email);

    }
}

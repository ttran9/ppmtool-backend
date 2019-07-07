package tran.example.ppmtool.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import tran.example.ppmtool.event.utility.EventUtil;
import tran.example.ppmtool.exceptions.security.ChangePasswordTokenException;
import tran.example.ppmtool.services.verificationtoken.ChangePasswordTokenServiceImpl;

import static tran.example.ppmtool.constants.mapping.MappingConstants.CHANGE_PASSWORD_URL;

@Component
public class ChangePasswordListener implements ApplicationListener<OnPasswordResetRequestEvent> {
    @Autowired
    private ChangePasswordTokenServiceImpl changePasswordTokenService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    @Value("${message.passwordChangeRequest}")
    private String changePasswordConfirmation;

    @Override
    public void onApplicationEvent(OnPasswordResetRequestEvent event) {
        this.changePasswordRequest(event);
    }

    private void changePasswordRequest(OnPasswordResetRequestEvent event) {
        try {
            String subject = "Change Password";
            EventUtil.sendEmail(event, changePasswordTokenService, messageSource, changePasswordConfirmation, mailSender,
                    subject, CHANGE_PASSWORD_URL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ChangePasswordTokenException("Password cannot be changed at this time!");
        }
    }
}

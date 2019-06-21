package tran.example.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;

@Component
public class ApplicationUserValidatorImpl implements ApplicationUserValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        /*
         * we are supporting our ApplicationUser class.
         * we are further validating that we have the correct object.
         */
        return ApplicationUser.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ApplicationUser applicationUser = (ApplicationUser) object;

        if(applicationUser.getPassword().length() < 6) {
            errors.rejectValue("password", "Length", "Password must be at least 6 characters.");
        }

        if(!applicationUser.getPassword().equals(applicationUser.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Passwords must match");
        }

    }
}

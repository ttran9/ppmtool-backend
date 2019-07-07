package tran.example.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import tran.example.ppmtool.payload.PasswordChangeRequest;

@Component
public class PasswordChangeRequestValidatorImpl implements PasswordChangeRequestValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        /*
         * we are supporting our ApplicationUser class.
         * we are further validating that we have the correct object.
         */
        return PasswordChangeRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        PasswordChangeRequest passwordChangeRequest = (PasswordChangeRequest) object;

        if(passwordChangeRequest.getPassword().length() < 6) {
            errors.rejectValue("password", "Length", "Password must be at least 6 characters.");
        }

        if(!passwordChangeRequest.getPassword().equals(passwordChangeRequest.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Passwords must match");
        }
    }
}

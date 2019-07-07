package tran.example.ppmtool.web.applicationusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tran.example.ppmtool.constants.mapping.MappingConstants;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.payload.InitialPasswordChangeRequest;
import tran.example.ppmtool.payload.LoginRequest;
import tran.example.ppmtool.payload.PasswordChangeRequest;
import tran.example.ppmtool.payload.RequestSuccessfulResponse;
import tran.example.ppmtool.security.JwtTokenProvider;
import tran.example.ppmtool.services.applicationusers.ApplicationUserService;
import tran.example.ppmtool.services.security.ApplicationUserAuthenticationService;
import tran.example.ppmtool.services.validations.MapValidationErrorService;
import tran.example.ppmtool.services.verificationtoken.ChangePasswordTokenServiceImpl;
import tran.example.ppmtool.services.verificationtoken.EmailVerificationTokenServiceImpl;
import tran.example.ppmtool.validator.ApplicationUserValidator;
import tran.example.ppmtool.validator.PasswordChangeRequestValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static tran.example.ppmtool.constants.messages.ResponseMessage.*;

@RestController
@RequestMapping("/api/users")
public class ApplicationUserController {

    private ApplicationUserService applicationUserService;

    private MapValidationErrorService mapValidationErrorService;

    private ApplicationUserValidator applicationUserValidator;

    private PasswordChangeRequestValidator passwordChangeRequestValidator;

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    private ApplicationUserAuthenticationService authenticationService;

    private EmailVerificationTokenServiceImpl emailVerificationService;

    private ChangePasswordTokenServiceImpl changePasswordTokenService;

    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService, MapValidationErrorService mapValidationErrorService,
                                     ApplicationUserValidator applicationUserValidator, JwtTokenProvider jwtTokenProvider,
                                     AuthenticationManager authenticationManager, ApplicationUserAuthenticationService authenticationService,
                                     EmailVerificationTokenServiceImpl emailVerificationService, ChangePasswordTokenServiceImpl changePasswordTokenService,
                                     PasswordChangeRequestValidator passwordChangeRequestValidator) {
        this.applicationUserService = applicationUserService;
        this.mapValidationErrorService = mapValidationErrorService;
        this.applicationUserValidator = applicationUserValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.emailVerificationService = emailVerificationService;
        this.changePasswordTokenService = changePasswordTokenService;
        this.passwordChangeRequestValidator = passwordChangeRequestValidator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
        if(errorMap != null) return errorMap;

        return authenticationService.applicationUserAuthentication(loginRequest, authenticationManager, jwtTokenProvider);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ApplicationUser applicationUser, BindingResult bindingResult,
                                          HttpServletRequest request) {
        // validate that passwords match.
        applicationUserValidator.validate(applicationUser, bindingResult);

        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
        if(errorMap != null) return errorMap;

        ApplicationUser newApplicationUser = applicationUserService.saveUser(applicationUser, request);

//        return new ResponseEntity<>(newApplicationUser, HttpStatus.CREATED);
        return new ResponseEntity<>(new RequestSuccessfulResponse(VERIFY_EMAIL_MESSAGE), HttpStatus.CREATED);
    }

    @PostMapping(MappingConstants.CONFIRM_REGISTRATION_URL + "{token}")
    public ResponseEntity<?> confirmRegistration(@PathVariable String token) {
        ApplicationUser user = emailVerificationService.validateVerificationToken(token);
        applicationUserService.enableRegisteredUser(user);

        return new ResponseEntity<>(new RequestSuccessfulResponse(SUCCESSFUL_ACCOUNT_ACTIVATION), HttpStatus.OK);
    }

    @PostMapping(MappingConstants.CHANGE_PASSWORD_URL + "{token}")
    public ResponseEntity<?> processChangeUserPassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest, BindingResult bindingResult,
                                                @PathVariable String token) {
        // process the password change request.
        // validate that passwords match.
        passwordChangeRequestValidator.validate(passwordChangeRequest, bindingResult);

        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
        if(errorMap != null) return errorMap;

        ApplicationUser user = changePasswordTokenService.validateVerificationToken(token);

        applicationUserService.changeUserPassword(passwordChangeRequest.getPassword(), user);

        return new ResponseEntity<>(new RequestSuccessfulResponse(PASSWORD_CHANGED), HttpStatus.OK);
    }

    @PostMapping(MappingConstants.CHANGE_PASSWORD_URL)
    public ResponseEntity<?> requestChangeUserPassword(@Valid @RequestBody InitialPasswordChangeRequest passwordChangeRequest,
                                                BindingResult bindingResult, HttpServletRequest request) {

        // requesting to change user password.
        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
        if(errorMap != null) return errorMap;

        applicationUserService.sendPasswordChangeEmail(passwordChangeRequest.getUsername(), request);

        return new ResponseEntity<>(new RequestSuccessfulResponse(CHANGE_PASSWORD), HttpStatus.OK);
    }
}

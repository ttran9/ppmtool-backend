package tran.example.ppmtool.web.applicationusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tran.example.ppmtool.domain.applicationuser.ApplicationUser;
import tran.example.ppmtool.payload.LoginRequest;
import tran.example.ppmtool.security.JwtTokenProvider;
import tran.example.ppmtool.services.applicationusers.ApplicationUserService;
import tran.example.ppmtool.services.security.ApplicationUserAuthenticationService;
import tran.example.ppmtool.services.validations.MapValidationErrorService;
import tran.example.ppmtool.validator.ApplicationUserValidator;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class ApplicationUserController {

    private ApplicationUserService applicationUserService;

    private MapValidationErrorService mapValidationErrorService;

    private ApplicationUserValidator applicationUserValidator;

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    private ApplicationUserAuthenticationService authenticationService;


    @Autowired
    public ApplicationUserController(ApplicationUserService applicationUserService, MapValidationErrorService mapValidationErrorService,
                                     ApplicationUserValidator applicationUserValidator, JwtTokenProvider jwtTokenProvider,
                                     AuthenticationManager authenticationManager,
                                     ApplicationUserAuthenticationService authenticationService) {
        this.applicationUserService = applicationUserService;
        this.mapValidationErrorService = mapValidationErrorService;
        this.applicationUserValidator = applicationUserValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
        if(errorMap != null) return errorMap;

        return authenticationService.applicationUserAuthentication(loginRequest, authenticationManager, jwtTokenProvider);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ApplicationUser applicationUser, BindingResult bindingResult) {
        // validate that passwords match.
        applicationUserValidator.validate(applicationUser, bindingResult);


        ResponseEntity<?> errorMap = mapValidationErrorService.outputCustomError(bindingResult);
        if(errorMap != null) return errorMap;

        ApplicationUser newApplicationUser = applicationUserService.saveUser(applicationUser);

        return new ResponseEntity<>(newApplicationUser, HttpStatus.CREATED);
    }
}

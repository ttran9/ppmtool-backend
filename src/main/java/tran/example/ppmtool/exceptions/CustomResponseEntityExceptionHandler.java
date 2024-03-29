package tran.example.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tran.example.ppmtool.exceptions.projects.ProjectNotFoundException;
import tran.example.ppmtool.exceptions.projects.ProjectIdExceptionResponse;
import tran.example.ppmtool.exceptions.projects.ProjectIdException;
import tran.example.ppmtool.exceptions.projects.ProjectNotFoundExceptionResponse;
import tran.example.ppmtool.exceptions.security.*;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex) {
        ProjectIdExceptionResponse projectIdExceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(projectIdExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex) {
        ProjectNotFoundExceptionResponse projectNotFoundExceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleDuplicateUsername(UsernameDuplicateException ex) {
        UsernameDuplicateExceptionResponse duplicateUserNameException = new UsernameDuplicateExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(duplicateUserNameException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidLogin(BadCredentialsException ex) {
        InvalidLoginExceptionResponse invalidLoginExceptionResponse = new InvalidLoginExceptionResponse();
        return new ResponseEntity<>(invalidLoginExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleDisabledAccountLogin(DisabledException ex) {
        InvalidLoginExceptionResponse invalidLoginExceptionResponse = new InvalidLoginExceptionResponse();
        return new ResponseEntity<>(invalidLoginExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameCreationException(UsernameCreationErrorException ex) {
        UsernameCreationErrorExceptionResponse usernameCreationErrorExceptionResponse = new UsernameCreationErrorExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(usernameCreationErrorExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleEmailVerificationTokenException(EmailVerificationTokenException ex) {
        EmailVerificationTokenExceptionResponse response = new EmailVerificationTokenExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRequestPasswordChangeException(RequestChangePasswordException ex) {
        RequestChangePasswordExceptionResponse response = new RequestChangePasswordExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleChangePasswordTokenException(ChangePasswordTokenException ex) {
        ChangePasswordTokenExceptionResponse response = new ChangePasswordTokenExceptionResponse();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

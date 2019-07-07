package tran.example.ppmtool.exceptions.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailVerificationTokenException extends RuntimeException {
    public EmailVerificationTokenException(String message) {
        super(message);
    }
}

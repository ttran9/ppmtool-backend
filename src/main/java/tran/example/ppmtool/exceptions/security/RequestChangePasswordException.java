package tran.example.ppmtool.exceptions.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestChangePasswordException extends RuntimeException {
    public RequestChangePasswordException(String message) {
        super(message);
    }
}


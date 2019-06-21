package tran.example.ppmtool.services.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorServiceImpl implements MapValidationErrorService {

    @Override
    public ResponseEntity<?> outputCustomError(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {

            Map<String, String> errorMap = new HashMap<>();

            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }

            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null; // no errors so return nothing.
    }
}

package noob.restmvc.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomException {
    @ExceptionHandler
    ResponseEntity<?> handleJPAViolation(TransactionSystemException e){
        ResponseEntity.BodyBuilder reb = ResponseEntity.badRequest();
        if(e.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException cve = (ConstraintViolationException) e.getCause().getCause();
            List<?> errorList = cve.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        Map<String , String> errorMap = new HashMap<>();
                        errorMap.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
                        return errorMap;
                    }).toList();
            return reb.body(errorList);

        }
        return reb.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleBindException(MethodArgumentNotValidException e){
        List<?> errorsList = e.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();
        return ResponseEntity.badRequest()
                .body(errorsList);
    }
}

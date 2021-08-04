package ua.com.poseal.library.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.poseal.library.exeptions.ConflictException;
import ua.com.poseal.library.exeptions.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.toList());

        Map<String, Object> body = new HashMap<String, Object>(){{
            put("timestamp", new Date());
            put("status", status);
            put("errors", errors);
        }};

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        HttpStatus code = HttpStatus.NOT_FOUND;
        Map<String, Object> body = fillMapException(ex, code);
        return new ResponseEntity<>(body, code);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex) {
        HttpStatus code = HttpStatus.CONFLICT;
        Map<String, Object> body = fillMapException(ex, code);
        return new ResponseEntity<>(body, code);
    }

    private Map<String, Object> fillMapException(RuntimeException ex, HttpStatus code) {
        Map<String, Object> body = new HashMap<String, Object>(){{
            put("timestamp", new Date());
            put("status", code);
            put("errors", Arrays.asList(ex.getMessage()));
        }};
        return body;
    }
}

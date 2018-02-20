package pl.pcyza.messenger.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.pcyza.messenger.exception.DataNotFoundException;
import pl.pcyza.messenger.exception.DataViolationException;

@ControllerAdvice
public class MessengerExceptionHandler {
	
	@ResponseStatus(HttpStatus.NOT_FOUND) 
    @ExceptionHandler(DataNotFoundException.class)
    public void handleDataNotFound() {
    }
	
	@ResponseStatus(HttpStatus.CONFLICT) 
    @ExceptionHandler(DataViolationException.class)
    public void handleConflict() {
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST) 
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleBadRequest() {
    }
}

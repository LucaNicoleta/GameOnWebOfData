package uaic.fii.MarvelMonPlay.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uaic.fii.MarvelMonPlay.exceptions.MarvelNotSetException;
import uaic.fii.MarvelMonPlay.exceptions.PlayerAlreadyRegisteredException;
import uaic.fii.MarvelMonPlay.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handlerResourceNotFoundException(Exception e, WebRequest webRequest){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerAlreadyRegisteredException.class)
    public ResponseEntity<String> handlerPlayerAlreadyRegisteredException(Exception e, WebRequest webRequest){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MarvelNotSetException.class)
    public ResponseEntity<String> handlerMarvelNotSetException(Exception e, WebRequest webRequest){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}

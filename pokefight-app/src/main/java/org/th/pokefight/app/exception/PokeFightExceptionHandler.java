package org.th.pokefight.app.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.th.pokefight.api.error.dto.ClientErrorDTO;

/**
 * Handle exceptions
 * 
 * @author th
 */
@RestControllerAdvice
public class PokeFightExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ClientErrorDTO> handleHttpClientErrorException(HttpClientErrorException e) {
        ClientErrorDTO clientError = new ClientErrorDTO(e.getMessage(), e.getStatusCode()
                                                                         .value(),
                                                        Instant.now());
        return ResponseEntity.status(clientError.status())
                             .body(clientError);
    }

    /**
     * Handle validation errors
     * 
     * @param e
     *            the exception that was thrown
     * @return response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ClientErrorDTO> handleValidation(MethodArgumentNotValidException e) {

        String msg = e.getBindingResult()
                      .getFieldErrors()
                      .stream()
                      .map(field -> field.getField() + ": " + field.getDefaultMessage())
                      .findFirst()
                      .orElse("Validation failed");

        ClientErrorDTO error = new ClientErrorDTO(msg, 400, Instant.now());
        return ResponseEntity.status(400)
                             .body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ClientErrorDTO> handleNoSuchPokemonException(RuntimeException e) {
        ClientErrorDTO clientError = new ClientErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value(), Instant.now());
        return ResponseEntity.status(clientError.status())
                             .body(clientError);
    }
}
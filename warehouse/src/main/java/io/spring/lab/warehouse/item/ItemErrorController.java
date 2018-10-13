package io.spring.lab.warehouse.item;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author created: skramer on 08.10.18 23:50
 */
@RestControllerAdvice
class ItemErrorController {
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    ErrorMessage itemNotFound(ItemNotFound e) {
        return ErrorMessage.messageOf(e);
    }

    @ExceptionHandler
    ResponseEntity<ErrorMessage> outOfStock(OutOfStock e) {
        return ErrorMessage.messageResponseOf(BAD_REQUEST, e);
    }

    @ExceptionHandler
    ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException e) {
        return ErrorMessage.messageResponseOf(BAD_REQUEST, e);
    }
}

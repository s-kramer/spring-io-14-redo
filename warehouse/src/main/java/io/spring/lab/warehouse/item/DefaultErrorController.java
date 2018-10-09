package io.spring.lab.warehouse.item;

import static io.spring.lab.warehouse.item.ErrorMessage.messageResponseOf;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author created: skramer on 08.10.18 18:40
 */
@RestController
class DefaultErrorController implements ErrorController {

    private ErrorProperties errorProperties;

    public DefaultErrorController(ServerProperties serverProperties) {
        this.errorProperties = serverProperties.getError();
    }

    @Override
    public String getErrorPath() {
        return errorProperties.getPath();
    }

    @GetMapping("${error.path:/error}")
    public ResponseEntity<ErrorMessage> getErrorMessage() {
        return messageResponseOf(INTERNAL_SERVER_ERROR, "Unexpected error");
    }
}

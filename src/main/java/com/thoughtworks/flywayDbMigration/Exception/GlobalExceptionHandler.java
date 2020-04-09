package com.thoughtworks.flywayDbMigration.Exception;

import com.thoughtworks.flywayDbMigration.controller.UserController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice()
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotPresentException.class)
    public final ResponseEntity<Response> handleUserNotFoundException(MethodArgumentNotValidException ex, WebRequest request){
        Response response = new Response();
        response.setMessage(ex.getMessage());
        response.setStatusCode(HttpStatus.NO_CONTENT.value());
        return ResponseEntity.ok(response);

    }


}

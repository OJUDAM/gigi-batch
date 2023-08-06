package com.ujo.gigiScheduler.common.exception;

import com.ujo.gigiScheduler.common.filter.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    private final RequestContext requestContext;

    public ControllerAdvice(final RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final CustomException e) {
        log.info(extractStackTrace(e));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(final CustomException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructureException(final CustomException e) {
        return ResponseEntity.internalServerError().body(ErrorResponse.from(e.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(final Exception e) {
        log.error("Stack Trace : {}", extractStackTrace(e));
        return ResponseEntity.internalServerError().body(ErrorResponse.from(ErrorCode.E001));
    }

    private String extractStackTrace(final Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}

package jpa.study.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        String message = e.getErrorCode().getDetail();
        log.error("[CustomException] : {}", message, e);
        return ErrorResponse.toResponseEntity(e);
    }
}

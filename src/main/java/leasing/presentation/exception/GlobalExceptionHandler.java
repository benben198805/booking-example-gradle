package leasing.presentation.exception;

import leasing.presentation.dto.response.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerOtherException(Exception exception) {
        log.warn(exception.getMessage());
        exception.printStackTrace();
        return ErrorResponse.of(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "UNKNOWN_ERROR",
            "unknown internal server error");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.of(HttpStatus.NOT_FOUND.value(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(CreateModelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCreateModelException(CreateModelException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(NotMatchException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNotMatchException(NotMatchException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.of(HttpStatus.CONFLICT.value(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(InvalidFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidFileException(InvalidFileException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(InvalidParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidParamException(InvalidParamException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistsException(AlreadyExistsException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.of(HttpStatus.CONFLICT.value(), exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage() + ex.getCause());
        String message = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));

        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "INVALID_PARAM", message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage() + ex.getCause());
        String message = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));

        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "INVALID_PARAM", message);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException exception) {
        log.warn(exception.getMessage());
        ErrorResponse response = ErrorResponse.of(exception.getStatusCode(),
            exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.getStatusCode()));
    }
}

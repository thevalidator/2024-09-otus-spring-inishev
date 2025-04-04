package ru.thevalidator.timeattackracing.exception.handler;

import com.nimbusds.jose.proc.BadJWSException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import ru.thevalidator.timeattackracing.exception.ConstraintViolationErrorException;
import ru.thevalidator.timeattackracing.exception.InvalidRefreshTokenException;
import ru.thevalidator.timeattackracing.exception.ItemNotFoundException;
import ru.thevalidator.timeattackracing.exception.LapFileParsingException;
import ru.thevalidator.timeattackracing.exception.UserAlreadyExistsException;

import java.util.stream.Collectors;

import static ru.thevalidator.timeattackracing.exception.handler.ErrorCode.BAD_MULTIPART;
import static ru.thevalidator.timeattackracing.exception.handler.ErrorCode.CONSTRAINT_VIOLATION;
import static ru.thevalidator.timeattackracing.exception.handler.ErrorCode.GENERIC_ERROR;
import static ru.thevalidator.timeattackracing.exception.handler.ErrorCode.UNAUTHORIZED;

@ControllerAdvice
public class RestApiErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestApiErrorHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex) {
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        log.error("{} :: [{}:{}] - message: {}", getExceptionClassSimpleName(ex), method, url, ex.getMessage(), ex);
        Error error = new Error.Builder()
                .errorCode(GENERIC_ERROR.getErrorCode())
                .message(GENERIC_ERROR.getErrorMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .url(url)
                .method(method)
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MultipartException.class, LapFileParsingException.class})
    public ResponseEntity<Error> handleMultipartException(HttpServletRequest request, Exception ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(BAD_MULTIPART.getErrorCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({JwtValidationException.class, InvalidRefreshTokenException.class, BadJWSException.class})
    public ResponseEntity<Error> handleJwtValidationException(HttpServletRequest request, Exception ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(UNAUTHORIZED.getErrorCode())
                .message("Invalid or expired token")
                .status(HttpStatus.UNAUTHORIZED.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Error> handleConstraintViolationException(HttpServletRequest request, Exception ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        String errorMessage = null;
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) ex;
            errorMessage = cve.getConstraintViolations()
                    .stream()
                    .map(constraintViolation ->
                            constraintViolation.getPropertyPath().toString() + constraintViolation.getMessage())
                    .collect(Collectors.joining(","));
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mve = (MethodArgumentNotValidException) ex;
            errorMessage = mve.getBindingResult().getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(","));
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException mam = (MethodArgumentTypeMismatchException) ex;
            errorMessage = "Invalid parameter name: " + mam.getName();
        }

        Error error = new Error.Builder()
                .errorCode(CONSTRAINT_VIOLATION.getErrorCode())
                .message(String.format("%s %s", ErrorCode.CONSTRAINT_VIOLATION.getErrorMessage(), errorMessage))
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Error> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request,
                                                                           HttpMediaTypeNotAcceptableException ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrorCode())
                .message(ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE.getErrorMessage())
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Error> handleUnsupportedOperationException(HttpServletRequest request,
                                                                     UnsupportedOperationException ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(ErrorCode.UNSUPPORTED_OPERATION.getErrorCode())
                .message(ex.getMessage())
                .status(HttpStatus.NOT_IMPLEMENTED.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Error> handleUserAlreadyExistsException(HttpServletRequest request,
                                                                  UserAlreadyExistsException ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(ErrorCode.USER_ALREADY_EXISTS.getErrorCode())
                .message(ErrorCode.USER_ALREADY_EXISTS.getErrorMessage())
                .status(HttpStatus.CONFLICT.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> handleBadCredentialsException(HttpServletRequest request, BadCredentialsException ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(ErrorCode.BAD_CREDENTIALS.getErrorCode())
                .message(ErrorCode.BAD_CREDENTIALS.getErrorMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Error> handleItemNotFoundException(HttpServletRequest request, ItemNotFoundException ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(ErrorCode.ITEM_NOT_FOUND.getErrorCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationErrorException.class)
    public ResponseEntity<Error> handleConstraintViolationErrorException(HttpServletRequest request,
                                                                         ConstraintViolationErrorException ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(ErrorCode.CONSTRAINT_VIOLATION.getErrorCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<Error> handleAuthorizationDeniedException(HttpServletRequest request,
                                                                    AuthorizationDeniedException ex) {
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        log.error("{} :: [{}:{}] - message: {}", getExceptionClassSimpleName(ex), method, url, ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(ErrorCode.ACCESS_DENIED.getErrorCode())
                .message(ex.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Error> handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                                              HttpRequestMethodNotSupportedException ex) {
        log.error("{} :: message: {}", getExceptionClassSimpleName(ex), ex.getMessage());
        Error error = new Error.Builder()
                .errorCode(ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrorCode())
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private static String getExceptionClassSimpleName(Exception ex) {
        return ex.getClass().getSimpleName();
    }

}

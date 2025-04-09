package ru.thevalidator.timeattackracing.exception.handler;

public enum ErrorCode {

    GENERIC_ERROR("E-001", "The system is unable to complete the request. Contact support."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("E-002", "Request method not supported."),
    HTTP_MEDIA_TYPE_NOT_SUPPORTED("E-003", "Requested media type is not supported. Please use application/json as 'Content-Type' header value"),
    CONSTRAINT_VIOLATION("E-004", "Validation failed."),
    JSON_PARSE_ERROR("E-005", "Make sure request payload should be a valid JSON object."),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE("E-006", "Requested 'Accept' header value is not supported. Please use application/json as 'Accept' value"),
    BAD_CREDENTIALS("E-007", "Wrong username or password"),
    USER_ALREADY_EXISTS("E-008", "User already exists."),
    ITEM_NOT_FOUND("E-009", "Requested item not found."),
    ACCESS_DENIED("E-010", "Access Denied."),
    UNAUTHORIZED("E-011", "Unauthorized"),
    UNSUPPORTED_OPERATION("E-012", "Not supported yet"),
    BAD_MULTIPART("E-013", "Bad multipart request");

    private final String errorCode;

    private final String errorMessage;

    ErrorCode(final String errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

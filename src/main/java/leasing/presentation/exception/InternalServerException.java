package leasing.presentation.exception;

public class InternalServerException extends AppException {

    private int statusCode;

    public InternalServerException(int statusCode, String code, String message) {
        super(code, message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

package warehouse.domain.token.exception.tokenexception;

public class ValidTokenException extends RuntimeException {

    public ValidTokenException() {
        super();
    }

    public ValidTokenException(String message) {
        super(message);
    }

    public ValidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidTokenException(Throwable cause) {
        super(cause);
    }

    protected ValidTokenException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

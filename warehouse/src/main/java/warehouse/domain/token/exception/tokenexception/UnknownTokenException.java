package warehouse.domain.token.exception.tokenexception;

public class UnknownTokenException extends RuntimeException {

    public UnknownTokenException() {
        super();
    }

    public UnknownTokenException(String message) {
        super(message);
    }

    public UnknownTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTokenException(Throwable cause) {
        super(cause);
    }

    protected UnknownTokenException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

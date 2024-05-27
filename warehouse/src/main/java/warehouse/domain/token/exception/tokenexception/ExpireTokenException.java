package warehouse.domain.token.exception.tokenexception;

public class ExpireTokenException extends RuntimeException {

    public ExpireTokenException() {
        super();
    }

    public ExpireTokenException(String message) {
        super(message);
    }

    public ExpireTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpireTokenException(Throwable cause) {
        super(cause);
    }

    protected ExpireTokenException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package warehouse.domain.token.exception.tokenexception;

public class ExpireAccessTokenException extends ExpireTokenException {

    public ExpireAccessTokenException() {
        super();
    }

    public ExpireAccessTokenException(String message) {
        super(message);
    }

    public ExpireAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpireAccessTokenException(Throwable cause) {
        super(cause);
    }

    protected ExpireAccessTokenException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

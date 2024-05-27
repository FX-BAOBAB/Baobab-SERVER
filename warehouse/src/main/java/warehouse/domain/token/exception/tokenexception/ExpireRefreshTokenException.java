package warehouse.domain.token.exception.tokenexception;

public class ExpireRefreshTokenException extends ExpireTokenException {

    public ExpireRefreshTokenException() {
        super();
    }

    public ExpireRefreshTokenException(String message) {
        super(message);
    }

    public ExpireRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpireRefreshTokenException(Throwable cause) {
        super(cause);
    }

    protected ExpireRefreshTokenException(String message, Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

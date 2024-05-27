package warehouse.domain.token.exception.tokenexception;

public class AuthorizationTokenNotFoundException extends RuntimeException {

    public AuthorizationTokenNotFoundException() {
        super();
    }

    public AuthorizationTokenNotFoundException(String message) {
        super(message);
    }

    public AuthorizationTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationTokenNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AuthorizationTokenNotFoundException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package warehouse.domain.address.exception.addressexception;

public class NotFoundAddressException extends RuntimeException {

    public NotFoundAddressException() {
        super();
    }

    public NotFoundAddressException(String message) {
        super(message);
    }

    public NotFoundAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundAddressException(Throwable cause) {
        super(cause);
    }

    protected NotFoundAddressException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

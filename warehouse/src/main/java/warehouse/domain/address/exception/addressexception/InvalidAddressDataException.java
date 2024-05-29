package warehouse.domain.address.exception.addressexception;

public class InvalidAddressDataException extends RuntimeException {

    public InvalidAddressDataException() {
        super();
    }

    public InvalidAddressDataException(String message) {
        super(message);
    }

    public InvalidAddressDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAddressDataException(Throwable cause) {
        super(cause);
    }

    protected InvalidAddressDataException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

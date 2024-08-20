package delivery.common.exception.shipping;

import global.errorcode.ErrorCodeIfs;

public class ShippingNotInRegisteredException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public ShippingNotInRegisteredException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ShippingNotInRegisteredException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public ShippingNotInRegisteredException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ShippingNotInRegisteredException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
package delivery.common.exception.receiving;

import global.errorcode.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ReceivingNotInDeliveryException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public ReceivingNotInDeliveryException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ReceivingNotInDeliveryException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public ReceivingNotInDeliveryException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ReceivingNotInDeliveryException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
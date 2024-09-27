package delivery.common.exception.receiving;

import global.errorcode.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ReceivingNotInRegisteredException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public ReceivingNotInRegisteredException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ReceivingNotInRegisteredException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public ReceivingNotInRegisteredException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ReceivingNotInRegisteredException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
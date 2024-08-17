package warehouse.common.exception.usedGoods;

import global.errorcode.ErrorCodeIfs;

public class InvalidOrderAuthorityException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public InvalidOrderAuthorityException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public InvalidOrderAuthorityException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public InvalidOrderAuthorityException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public InvalidOrderAuthorityException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
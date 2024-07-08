package warehouse.common.exception.user;

import global.errorcode.ErrorCodeIfs;

public class ExistUserException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public ExistUserException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ExistUserException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public ExistUserException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public ExistUserException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }
}

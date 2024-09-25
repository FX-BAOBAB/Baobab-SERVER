package store.common.exception.goodsledger;

import global.errorcode.ErrorCodeIfs;

public class GoodsLedgerNotFoundException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public GoodsLedgerNotFoundException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public GoodsLedgerNotFoundException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public GoodsLedgerNotFoundException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public GoodsLedgerNotFoundException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }
}

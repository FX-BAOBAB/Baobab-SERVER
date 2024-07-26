package warehouse.common.exception.usedGoods;

import global.errorcode.ErrorCodeIfs;

public class GoodsNotInUsedStatus extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public GoodsNotInUsedStatus(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public GoodsNotInUsedStatus(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public GoodsNotInUsedStatus(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public GoodsNotInUsedStatus(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
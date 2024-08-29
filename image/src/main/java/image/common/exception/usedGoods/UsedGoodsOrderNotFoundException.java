package image.common.exception.usedGoods;

import global.errorcode.ErrorCodeIfs;

public class UsedGoodsOrderNotFoundException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public UsedGoodsOrderNotFoundException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public UsedGoodsOrderNotFoundException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public UsedGoodsOrderNotFoundException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public UsedGoodsOrderNotFoundException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable);
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}
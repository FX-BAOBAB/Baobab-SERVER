package warehouse.common.exception.image;

import global.errorcode.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();

    String getDescription();

}

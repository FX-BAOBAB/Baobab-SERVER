package warehouse.common.exception.user;

import global.errorcode.ErrorCodeIfs;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNameNotFoundException extends UsernameNotFoundException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String description;

    public UserNameNotFoundException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public UserNameNotFoundException(ErrorCodeIfs errorCodeIfs, String errorDescription) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

    public UserNameNotFoundException(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
        super(throwable.toString());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorCodeIfs.getDescription();
    }

    public UserNameNotFoundException(ErrorCodeIfs errorCodeIfs, Throwable throwable,
        String errorDescription) {
        super(throwable.toString());
        this.errorCodeIfs = errorCodeIfs;
        this.description = errorDescription;
    }

}

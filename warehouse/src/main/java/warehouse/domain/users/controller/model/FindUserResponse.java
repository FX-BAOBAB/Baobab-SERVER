package warehouse.domain.users.controller.model;

import db.domain.users.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindUserResponse {

    private Long id;

    private String email;

    private String password;

    private UserRole role;
}

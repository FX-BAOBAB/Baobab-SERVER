package warehouse.domain.users.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindUserResponse {

    private Long id;

    private String email;

    private String password;

    private String role;
}

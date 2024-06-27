package warehouse.domain.users.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpResponse {

    private String email;
    private String name;
}

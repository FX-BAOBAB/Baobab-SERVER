package warehouse.domain.users.controller.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {

    private String email;
    private String password;
    private String name;
    private String address;
    private String detail_address;
    private boolean basic_address;
}

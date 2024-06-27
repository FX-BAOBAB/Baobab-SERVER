package warehouse.domain.users.controller.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    private String email;
    private String password;
    private String name;
    private String address;
    private String detailAddress;
    private int post;
    private boolean basicAddress;

}

package warehouse.domain.users.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersRegisterRequest {

    private String email;
    private String password;
    private String name;
    private String address;
    private String detailAddress;
    private boolean basicAddress;

}

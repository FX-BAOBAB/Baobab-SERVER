package warehouse.domain.users.controller.model.login;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @Pattern(regexp = "^[0-9a-zA-Z]{1,100}@[0-9a-zA-Z]+(\\.[0-9a-zA-Z]+)+$")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?!.*[\\\\[\\\\]{}()<>#$%^&*_=|~`]).{8,100}$")
    private String password;

}

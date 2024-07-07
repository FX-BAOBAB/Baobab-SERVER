package warehouse.domain.users.controller.model.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersRegisterRequest {

    @Pattern(regexp = "^[0-9a-zA-Z]{1,100}@[0-9a-zA-Z]+(\\.[0-9a-zA-Z]+)+$")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?!.*[\\\\[\\\\]{}()<>#$%^&*_=|~`]).{8,100}$")
    private String password;

    @Pattern(regexp = "^[가-힣]{1,50}$")
    private String name;

    @NotBlank
    @Size(max = 200)
    private String address;

    @NotBlank
    @Size(max = 200)
    private String detailAddress;

    @NotNull
    private boolean basicAddress;

    @Pattern(regexp = "^[0-9]{5}$")
    private int post;

}

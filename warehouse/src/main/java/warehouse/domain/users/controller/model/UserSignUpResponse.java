package warehouse.domain.users.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpResponse {
    private Long userId;
    private String message;
}

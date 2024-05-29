package warehouse.domain.users.model;

import db.domain.users.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    //TODO SessionUser 에 UserStatus 포함?
    private Long id;
    private UserStatus userStatus;

}

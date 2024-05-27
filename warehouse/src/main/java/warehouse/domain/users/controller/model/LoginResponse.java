package warehouse.domain.users.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;

    private LocalDateTime expiredAt;

    @JsonIgnore
    private String refreshToken; // JSON 직렬화에서 제외되는 필드

}

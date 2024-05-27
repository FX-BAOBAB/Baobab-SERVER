package warehouse.domain.token.controller.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReIssueAccessTokenResponse {

    private String accessToken;
    private LocalDateTime expiredAt;

}

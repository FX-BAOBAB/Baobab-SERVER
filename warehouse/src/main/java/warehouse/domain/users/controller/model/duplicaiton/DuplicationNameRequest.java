package warehouse.domain.users.controller.model.duplicaiton;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuplicationNameRequest {

    @Pattern(regexp = "^[가-힣]{1,50}$")
    private String name;

}

package warehouse.domain.users.controller.model.duplicaiton;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuplicationRequest {

    @Pattern(regexp = "^[0-9a-zA-Z]{1,50}@[0-9a-zA-Z]{1,24}+(\\.[0-9a-zA-Z]+){1,24}$")
    private String email;

}

package warehouse.domain.address.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    @NotBlank
    @Size(max = 200)
    private String address;

    @NotBlank
    @Size(max = 200)
    private String detailAddress;

    @Pattern(regexp = "^[0-9]{5}$")
    private String post;

    @NotNull
    private boolean basicAddress;

}

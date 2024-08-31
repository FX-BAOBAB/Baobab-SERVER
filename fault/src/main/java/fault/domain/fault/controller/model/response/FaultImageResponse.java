package fault.domain.fault.controller.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultImageResponse {

    private Long imageId;

    private String imageUrl;

    private String caption;

}

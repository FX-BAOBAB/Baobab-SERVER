package warehouse.domain.takeback.controller;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.takeback.business.TakeBackBusiness;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/takeback")
public class TakeBackController {

    private final TakeBackBusiness takeBackBusiness;

    @PostMapping("/{receivingId}")
    public Api<ReceivingResponse> takeBackRequest(@PathVariable Long receivingId) {
        ReceivingResponse response = takeBackBusiness.takeBackRequest(receivingId);
        return Api.OK(response);
    }

}

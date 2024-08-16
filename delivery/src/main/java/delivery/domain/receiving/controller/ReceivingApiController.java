package delivery.domain.receiving.controller;

import delivery.domain.receiving.business.ReceivingBusiness;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReceivingApiController {

    private final ReceivingBusiness receivingBusiness;

    @GetMapping
    public Api<ReceivingResponseList> showReservationList(){
        ReceivingResponseList response = receivingBusiness.getReservationList();
        return Api.OK(response);
    }

}

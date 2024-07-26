package warehouse.domain.takeback.controller;

import global.api.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.takeback.business.TakeBackBusiness;
import warehouse.domain.takeback.controller.model.TakeBackListResponse;
import warehouse.domain.takeback.controller.model.TakeBackResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/takeback")
public class TakeBackController {

    private final TakeBackBusiness takeBackBusiness;

    @PostMapping("/{receivingId}")
    public Api<TakeBackResponse> takeBackRequest(
        @PathVariable Long receivingId,
        @AuthenticationPrincipal User user
    ) {
        TakeBackResponse response = takeBackBusiness.takeBackRequest(
            receivingId,user.getUsername());
        return Api.OK(response);
    }

    @PostMapping
    public Api<TakeBackResponse> takeBackRequest(
        @RequestBody Api<List<Long>> goodsIdList,
        @AuthenticationPrincipal User user
    ){
        TakeBackResponse response = takeBackBusiness.takeBackRequest(goodsIdList.getBody(),user.getUsername());
        return Api.OK(response);
    }

    @GetMapping
    public Api<TakeBackListResponse> getTakeBackRequestList(
        @AuthenticationPrincipal User user
    ){
        TakeBackListResponse response = takeBackBusiness.getTakeBackListBy(user.getUsername());
        return Api.OK(response);
    }

}

package warehouse.domain.users.controller;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.users.business.UsersBusiness;
import warehouse.domain.users.controller.model.UsersRegisterRequest;
import warehouse.domain.users.controller.model.UsersRegisteredResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/users")
public class UsersController {

    private final UsersBusiness usersBusiness;

    @PostMapping()
    public Api<UsersRegisteredResponse> register(
        @RequestBody Api<UsersRegisterRequest> request
    ){
        UsersRegisteredResponse response = usersBusiness.register(request.getBody());
        return Api.OK(response);
    }

}

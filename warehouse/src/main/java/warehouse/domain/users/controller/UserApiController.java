package warehouse.domain.users.controller;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.address.controller.model.AddressResponse;
import warehouse.domain.address.controller.model.AddressResponseList;
import warehouse.domain.users.business.UsersBusiness;
import warehouse.domain.users.controller.model.login.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UsersBusiness usersBusiness;

    @GetMapping()
    public Api<UserResponse> getUserInformation(@AuthenticationPrincipal User user){
        UserResponse response = usersBusiness.getUserInformation(user.getUsername());
        return Api.OK(response);
    }

}

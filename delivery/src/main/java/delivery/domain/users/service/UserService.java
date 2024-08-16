package delivery.domain.users.service;

import db.domain.users.UserEntity;
import db.domain.users.UsersRepository;
import delivery.common.error.UserErrorCode;
import delivery.common.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public UserEntity getUserBy(Long userId) {
        return usersRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
    }
}

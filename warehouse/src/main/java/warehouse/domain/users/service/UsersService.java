package warehouse.domain.users.service;

import db.domain.users.UserEntity;
import db.domain.users.UsersRepository;
import db.domain.users.enums.UserStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import warehouse.common.error.UserErrorCode;
import warehouse.common.exception.user.ExistUserException;
import warehouse.common.exception.jwt.TokenException;
import warehouse.common.exception.user.LogInException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public void validationUserId(String email) {
        boolean existsByEmail = usersRepository.existsByEmail(email);
        if (existsByEmail){
            throw new ExistUserException(UserErrorCode.EXIST_USER);
        }
    }

    public UserEntity save(UserEntity userEntity) {
        userEntity.setRegisteredAt(LocalDateTime.now());
        userEntity.setStatus(UserStatus.REGISTERED);
        return usersRepository.save(userEntity);
    }

    public UserEntity login(String email, String password) {

        UserEntity userEntity = usersRepository.findFirstByEmailOrderByEmailDesc(email)
            .orElseThrow(() ->  new LogInException(UserErrorCode.LOGIN_FAIL));

        if (BCrypt.checkpw(password, userEntity.getPassword())){
            userEntity.setLastLoginAt(LocalDateTime.now());
            usersRepository.save(userEntity);
            return userEntity;
        }

        throw new LogInException(UserErrorCode.LOGIN_FAIL);

    }

    public UserEntity getUserWithThrow(Long userId) {
        log.info("userid : {} in UsersService", userId);
        return usersRepository.findFirstByIdAndStatusOrderByIdDesc(userId,UserStatus.REGISTERED).orElseThrow(() -> new TokenException(
            UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(String email) {
        log.info("userid : {} in UsersService", email);
        return usersRepository.findFirstByEmailAndStatusOrderByIdDesc(email,UserStatus.REGISTERED).orElseThrow(() -> new TokenException(
            UserErrorCode.USER_NOT_FOUND));
    }
}
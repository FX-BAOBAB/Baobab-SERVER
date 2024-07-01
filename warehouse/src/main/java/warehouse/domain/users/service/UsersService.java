package warehouse.domain.users.service;

import db.domain.users.UserEntity;
import db.domain.users.UsersRepository;
import db.domain.users.enums.UserStatus;
import global.errorcode.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import warehouse.common.error.TokenErrorCode;
import warehouse.common.exception.ApiException;
import warehouse.common.exception.jwt.TokenException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public void validationUserId(String email) {
        boolean existsByEmail = usersRepository.existsByEmail(email);
        if (existsByEmail){
            throw new ApiException(TokenErrorCode.EXIST_USER);
        }
    }

    public UserEntity save(UserEntity userEntity) {
        userEntity.setRegisteredAt(LocalDateTime.now());
        userEntity.setStatus(UserStatus.REGISTERED);
        return usersRepository.save(userEntity);
    }

    public UserEntity login(String email, String password) {

        UserEntity userEntity = usersRepository.findFirstByEmailOrderByEmailDesc(email)
            .orElseThrow(() -> new TokenException(TokenErrorCode.USER_NOT_FOUNT));

        if (BCrypt.checkpw(password, userEntity.getPassword())){
            userEntity.setLastLoginAt(LocalDateTime.now());
            usersRepository.save(userEntity);
            return userEntity;
        }

        throw new TokenException(TokenErrorCode.USER_NOT_FOUNT);

    }
    public UserEntity getUserWithThrow(Long userId) {
        log.info("userid : {} in UsersService", userId);
        return usersRepository.findFirstByIdAndStatusOrderByIdDesc(userId,UserStatus.REGISTERED).orElseThrow(() -> new TokenException(TokenErrorCode.USER_NOT_FOUNT));
    }

    public UserEntity getUserWithThrow(String email) {
        log.info("userid : {} in UsersService", email);
        return usersRepository.findFirstByEmailAndStatusOrderByIdDesc(email,UserStatus.REGISTERED).orElseThrow(() -> new TokenException(TokenErrorCode.USER_NOT_FOUNT));
    }
}
package delivery.domain.users.security.jwt.service;

import db.domain.users.UserEntity;
import db.domain.users.UsersRepository;
import db.domain.users.enums.UserStatus;
import delivery.common.error.UserErrorCode;
import delivery.common.exception.jwt.TokenException;
import delivery.common.exception.user.ExistUserException;
import delivery.common.exception.user.LogInException;
import delivery.common.exception.user.UserNotFoundException;
import delivery.common.exception.user.UserUnregisterException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public boolean existsByEmail(String email){
        return usersRepository.existsByEmail(email);
    }

    public boolean existsByName(String name){
        return usersRepository.existsByName(name);
    }

    public void existsByEmailThrowEx(String email) {

        boolean existsByEmail = existsByEmail(email);

        if (existsByEmail){
            throw new ExistUserException(UserErrorCode.EXIST_USER);
        }

    }


    public void notExistsByEmailThrowEx(String email) {

        boolean existsByEmail = existsByEmail(email);

        if (!existsByEmail){
            throw new ExistUserException(UserErrorCode.USER_NOT_FOUND);
        }

    }

    public UserEntity save(UserEntity userEntity) {
        userEntity.setRegisteredAt(LocalDateTime.now());
        userEntity.setStatus(UserStatus.REGISTERED);
        return usersRepository.save(userEntity);
    }

    public UserEntity login(String email, String password) {

        UserEntity userEntity = usersRepository.findFirstByEmailAndStatusNotOrderByEmailDesc(email,UserStatus.UNREGISTERED)
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

    public void unregister(String email) {
        UserEntity userEntity = usersRepository.findFirstByEmailAndStatusOrderByIdDesc(email,
                UserStatus.REGISTERED)
            .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        userEntity.setStatus(UserStatus.UNREGISTERED);
        userEntity.setUnRegisteredAt(LocalDateTime.now());
        UserEntity unRegisterdUserEntity = usersRepository.save(userEntity);
        if (!unRegisterdUserEntity.getStatus().equals(UserStatus.UNREGISTERED)){
            throw new UserUnregisterException(UserErrorCode.FAILED_TO_UNREGISTER);
        }

    }
}
package warehouse.domain.users.service;

import db.domain.address.AddressEntity;
import db.domain.address.AddressRepository;
import db.domain.users.UserEntity;
import db.domain.users.UserRepository;
import db.domain.users.enums.UserRole;
import db.domain.users.enums.UserStatus;
import global.api.Api;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import db.domain.account.AccountEntity;
import db.domain.account.AccountRepository;
import warehouse.domain.users.controller.model.FindUserResponse;
import warehouse.domain.users.controller.model.UserSignUpRequest;
import warehouse.domain.users.exception.userexception.UserNotFoundException;
import warehouse.domain.users.exception.userexception.DuplicateUserException;
import warehouse.domain.users.errorcode.UserErrorCode;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AccountEntity login(String email, String password) {
        //TODO UserStatus 에 따른 로직 처리
        AccountEntity accountEntity = getUser(email, password);

        // UserEntity의 lastLoginAt 업데이트
        UserEntity userEntity = accountEntity.getUser();
        userEntity.setLastLoginAt(LocalDateTime.now());
        userRepository.save(userEntity);

        return accountEntity;
    }

    public AccountEntity getUser(String email, String password) {
        //등록된 USER 조회
        return accountRepository.findByEmailAndPasswordAndUserStatus(email, password, UserStatus.REGISTERED)
            .orElseThrow(UserNotFoundException::new);
    }

    public AccountEntity getUser(Long userId) {
        return accountRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    // 회원가입
    public Api<Object> signUp(UserSignUpRequest request) {
        try {
            // 이메일 중복 검사
            if (accountRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateUserException(String.valueOf(UserErrorCode.USER_ALREADY_EXISTS));
            }

            UserEntity userEntity = UserEntity.builder()
                .role(UserRole.BASIC)
                .status(UserStatus.REGISTERED)
                .registeredAt(LocalDateTime.now())
                .build();
            userRepository.save(userEntity);

            AccountEntity accountEntity = AccountEntity.builder()
                .email(request.getEmail())
                // TODO 패스워드 암호화 보류
//            .password(passwordEncoder.encode(request.getPassword()))
                .password(request.getPassword())
                .name(request.getName())
                .user(userEntity)
                .build();
            accountRepository.save(accountEntity);

            AddressEntity addressEntity = AddressEntity.builder()
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .basicAddress(request.isBasicAddress())
                .user(userEntity)
                .build();
            addressRepository.save(addressEntity);

            return Api.OK("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            throw new DuplicateUserException(String.valueOf(UserErrorCode.INVALID_USER_DATA));
        }
    }

    public FindUserResponse findUserById(Long userId) {
        AccountEntity accountEntity = accountRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        UserEntity userEntity = accountEntity.getUser();

        return FindUserResponse.builder()
            .id(userEntity.getId())
            .email(accountEntity.getEmail())
            .password(accountEntity.getPassword())
            .role(String.valueOf(userEntity.getRole()))
            .build();
    }

}
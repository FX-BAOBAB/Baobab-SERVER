package warehouse.domain.users.service;

import db.domain.address.AddressEntity;
import db.domain.address.AddressRepository;
import db.domain.users.UserEntity;
import db.domain.users.UserRepository;
import db.domain.users.enums.UserRole;
import db.domain.users.enums.UserStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import db.domain.account.AccountEntity;
import db.domain.account.AccountRepository;
import warehouse.domain.users.controller.model.UserDetailsResponse;
import warehouse.domain.users.controller.model.SignUpRequest;
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
        UserEntity userEntity = getUser(accountEntity.getUserId());
        userEntity.setLastLoginAt(LocalDateTime.now());
        userRepository.save(userEntity);

        return accountEntity;
    }

    public AccountEntity getUser(String email, String password) {
        //등록된 USER 조회
        return accountRepository.findByEmailAndPassword(email, password)
            .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void checkEmailDuplication(String email) {
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new DuplicateUserException(String.valueOf(UserErrorCode.USER_ALREADY_EXISTS));
        }
    }

    public UserEntity createUser() {
        UserEntity userEntity = UserEntity.builder()
            .registeredAt(LocalDateTime.now())
            .role(UserRole.BASIC)
            .status(UserStatus.REGISTERED)
            .build();
        return userRepository.save(userEntity);
    }

    public AccountEntity createAccount(SignUpRequest request, Long userId) {
        AccountEntity accountEntity = AccountEntity.builder()
            .email(request.getEmail())
            // TODO 패스워드 암호화 보류
//            .password(passwordEncoder.encode(request.getPassword()))
            .password(request.getPassword())
            .name(request.getName())
            .userId(userId)
            .build();
        return accountRepository.save(accountEntity);
    }

    public AddressEntity createAddress(SignUpRequest request, Long userId) {
        AddressEntity addressEntity = AddressEntity.builder()
            .address(request.getAddress())
            .basicAddress(request.isBasicAddress())
            .detailAddress(request.getDetailAddress())
            .post(request.getPost())
            .userId(userId)
            .build();
        return addressRepository.save(addressEntity);
    }

    public UserDetailsResponse findUserById(Long userId) {
        AccountEntity accountEntity = accountRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        UserEntity userEntity = getUser(accountEntity.getUserId());

        return UserDetailsResponse.builder()
            .id(userEntity.getId())
            .email(accountEntity.getEmail())
            .password(accountEntity.getPassword())
            .role((userEntity.getRole()))
            .build();
    }

}
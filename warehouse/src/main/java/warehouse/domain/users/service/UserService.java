package warehouse.domain.users.service;

import db.domain.address.AddressEntity;
import db.domain.address.AddressRepository;
import db.domain.users.enums.UserStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import db.domain.account.AccountEntity;
import db.domain.account.AccountRepository;
import warehouse.domain.users.exception.userexception.UserNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final AccountRepository accountRepository;

    public AccountEntity login(String email, String password) {
        //TODO UserStatus 에 따른 로직 처리
        AccountEntity user = getUser(email, password);
        return user;
    }

    public AccountEntity getUser(String email, String password) {
        //등록된 USER 조회
        return accountRepository.findByEmailAndPasswordAndUserStatus(email, password, UserStatus.REGISTERED)
            .orElseThrow(UserNotFoundException::new);
    }

    public AccountEntity getUser(Long userId) {
        return accountRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
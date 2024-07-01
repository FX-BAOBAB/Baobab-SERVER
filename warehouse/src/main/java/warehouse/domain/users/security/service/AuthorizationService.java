package warehouse.domain.users.security.service;

import db.domain.users.UserEntity;
import db.domain.users.UsersRepository;
import db.domain.users.enums.UserStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Optional<UserEntity> account = usersRepository.findFirstByIdAndStatusOrderByIdDesc(
            Long.parseLong(userId), UserStatus.REGISTERED);

        return account.map(it -> User.builder().username(it.getEmail()).password(it.getPassword())
                .roles(it.getRole().toString()).build())
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

    }
}

package warehouse.domain.address.service;

import db.domain.address.AddressEntity;
import db.domain.address.AddressRepository;
import db.domain.users.UserRepository;
import db.domain.users.enums.UserStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warehouse.domain.address.exception.addressexception.NotFoundAddressException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public List<AddressEntity> findAddressList(Long userId) {
        UserStatus userStatus = userRepository.findUserStatusById(userId);
        if (!userStatus.equals(UserStatus.REGISTERED)) {
            //TODO 유저 상태에 따른 로직 처리
        }

        List<AddressEntity> addressEntityList = addressRepository.findByUserId(userId);
        if (addressEntityList.isEmpty()) {
            throw new NotFoundAddressException();
        }
        return addressEntityList;
    }

    public AddressEntity findBasicAddress(Long userId) {
        UserStatus userStatus = userRepository.findUserStatusById(userId);
        if (!userStatus.equals(UserStatus.REGISTERED)) {
            //TODO 유저 상태에 따른 로직 처리
        }

        AddressEntity basicAddress = addressRepository.findByUserIdAndBasicAddress(
            userId, true);

        if (basicAddress == null) {
            throw new NotFoundAddressException();
        }

        return basicAddress;
    }

    @Transactional
    public AddressEntity updateAddress(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    @Transactional
    public void resetBasicAddress(Long userId) {
        AddressEntity addressEntity = addressRepository.findByIdAndBasicAddress(userId, true);
        if (addressEntity != null) {
            addressEntity.setBasicAddress(false);
            addressRepository.save(addressEntity);
        }
    }
}

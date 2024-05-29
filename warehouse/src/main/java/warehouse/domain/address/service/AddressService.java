package warehouse.domain.address.service;

import db.domain.address.AddressEntity;
import db.domain.address.AddressRepository;
import db.domain.users.UserEntity;
import db.domain.users.UserRepository;
import db.domain.users.enums.UserStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import warehouse.domain.address.errorcode.AddressErrorCode;
import warehouse.domain.address.exception.addressexception.InvalidAddressDataException;
import warehouse.domain.address.exception.addressexception.NotFoundAddressException;
import warehouse.domain.users.errorcode.UserErrorCode;
import warehouse.domain.users.exception.userexception.UserNotFoundException;

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

    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
            String.valueOf(UserErrorCode.NOT_FOUND_USER)));
    }

    public void updateExistingBasicAddressIfNecessary(Long userId, boolean isBasicAddress) {
        if (isBasicAddress) {
            AddressEntity existBasicAddress = addressRepository.findByUserIdAndBasicAddress(userId, true);
            if (existBasicAddress != null) {
                existBasicAddress.setBasicAddress(false);
                addressRepository.save(existBasicAddress);
            }
        }
    }

    public AddressEntity saveAddressEntity(AddressEntity addressEntity) {
        try {
            return addressRepository.save(addressEntity);
        } catch (Exception e) {
            throw new InvalidAddressDataException(
                String.valueOf(AddressErrorCode.INVALID_ADDRESS_DATA));
        }
    }
}

package warehouse.domain.address.service;

import db.domain.users.address.AddressEntity;
import db.domain.users.address.AddressRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.error.UserErrorCode;
import warehouse.common.exception.user.AddressNotFoundException;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressEntity save(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    public List<AddressEntity> getAddressList(Long userId) {
        return addressRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    public AddressEntity getBasicAddress(Long userId) {
        boolean basic = true;
        return addressRepository.findFirstByUserIdAndBasicAddressOrderByIdDesc(userId,basic).orElseThrow(() -> new AddressNotFoundException(
            UserErrorCode.ADDRESS_NOT_FOUND));
    }

    public AddressEntity setAddress(AddressEntity addressEntity) {

        if (addressEntity.isBasicAddress()){
            setFalseOldBasicAddress(addressEntity.getUserId());
        }

        return addressRepository.save(addressEntity);

    }

    private void setFalseOldBasicAddress(Long userId) {
        AddressEntity oldBasicEntity = addressRepository.findFirstByUserIdAndBasicAddressOrderByIdDesc(
            userId, true).orElseThrow(() -> new AddressNotFoundException(UserErrorCode.ADDRESS_NOT_FOUND));

        oldBasicEntity.setBasicAddress(false);

        addressRepository.save(oldBasicEntity);
    }

}

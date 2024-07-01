package warehouse.domain.address.service;

import db.domain.users.address.AddressEntity;
import db.domain.users.address.AddressRepository;
import global.errorcode.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.exception.ApiException;

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

}

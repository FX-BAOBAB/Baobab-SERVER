package warehouse.domain.address.service;

import db.domain.users.address.AddressEntity;
import db.domain.users.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressEntity save(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

}

package org.example.adreessmanagement.service;


import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.model.Adress;

import java.util.List;

public interface AddressServiceInterface {
    List<Adress> getAllAddresses();

    Adress getAddressById(Long id);

    Adress createAddress(AddressDTO adressDTO);

    Adress updateAddress(Long id, Adress adressDTO);

    void deleteAddress(Long id);
}

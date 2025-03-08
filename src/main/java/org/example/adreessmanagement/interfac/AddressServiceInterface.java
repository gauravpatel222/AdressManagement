package org.example.adreessmanagement.interfac;

import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.model.Address;

import java.util.List;

public interface AddressServiceInterface {

    List<Address> getAllAddresses();

    Address getAddressById(Long id);

    Address createAddress(AddressDTO addressDTO);

    Address updateAddress(Long id, Address addressDTO);

    void deleteAddress(Long id);
}

package org.example.adreessmanagement.service;

import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.interfac.AddressServiceInterface;
import org.example.adreessmanagement.model.Address;
import org.example.adreessmanagement.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService implements AddressServiceInterface {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setName(addressDTO.getName());
        address.setAddress(addressDTO.getAddress());
        address.setContactNumber(addressDTO.getContactNumber());
        address.setEmail(addressDTO.getEmail());
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, Address addressDTO) {
        return addressRepository.findById(id).map(address -> {
            address.setName(addressDTO.getName());
            address.setAddress(addressDTO.getAddress());
            address.setContactNumber(addressDTO.getContactNumber());
            address.setEmail(addressDTO.getEmail());
            return addressRepository.save(address);
        }).orElse(null);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}

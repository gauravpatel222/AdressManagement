package org.example.adreessmanagement.service;

import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.model.Address;
import org.example.adreessmanagement.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    // Cache all addresses
    @Cacheable(value = "addresses")
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // Cache a specific address by ID
    @Cacheable(value = "addresses", key = "#id")
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    // Create new address and clear cache
    @CacheEvict(value = "addresses", allEntries = true)
    public Address createAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setName(addressDTO.getName());
        address.setAddress(addressDTO.getAddress());
        address.setContactNumber(addressDTO.getContactNumber());
        address.setEmail(addressDTO.getEmail());
        return addressRepository.save(address);
    }

    // Update address and clear cache
    @CacheEvict(value = "addresses", allEntries = true)
    public Address updateAddress(Long id, Address addressDTO) {
        return addressRepository.findById(id).map(address -> {
            address.setName(addressDTO.getName());
            address.setAddress(addressDTO.getAddress());
            address.setContactNumber(addressDTO.getContactNumber());
            address.setEmail(addressDTO.getEmail());
            return addressRepository.save(address);
        }).orElse(null);
    }

    // Delete address and clear cache
    @CacheEvict(value = "addresses", allEntries = true)
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}

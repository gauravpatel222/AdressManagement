package org.example.adreessmanagement.controller;

import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.interfac.AddressServiceInterface;
import org.example.adreessmanagement.model.Address;
import org.example.adreessmanagement.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addressbook")
public class AddressController {
    
    private final AddressServiceInterface addressService;

    public AddressController() {
        addressService = null;
    }


    @GetMapping("/all")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/get/{id}")
    public Address getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PostMapping("/create")
    public Address createAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.createAddress(addressDTO);
    }

    @PutMapping("/update/{id}")

    public Address updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
        return addressService.updateAddress(id, updatedAddress);
    }


}

package org.example.adreessmanagement.controller;

import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.service.AddressService; // Assuming you have a Service class
import org.example.adreessmanagement.model.Address;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addressbook")
public class AddressController {

    private final AddressService addressService; // Use Service instead of Interface directly

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
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
    public Address updateAddress(@PathVariable Long id, @RequestBody AddressDTO updatedAddressDTO) {
        return addressService.updateAddress(id, updatedAddressDTO);
    }
}

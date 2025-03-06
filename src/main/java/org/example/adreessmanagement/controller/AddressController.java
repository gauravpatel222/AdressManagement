package org.example.adreessmanagement.controller;

import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.model.Adress;
import org.example.adreessmanagement.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/all")
    public List<Adress> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/get/{id}")
    public Adress getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PostMapping("/create")
    public Adress createAddress(@RequestBody AddressDTO addressDTO) {
        return addressService.createAddress(addressDTO);
    }

    @PutMapping("/update/{id}")

    public Adress updateAddress(@PathVariable Long id, @RequestBody Adress updatedAddress) {
        return addressService.updateAddress(id, updatedAddress);
    }


}

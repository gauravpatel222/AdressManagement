package org.example.adreessmanagement.service;

import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.model.Adress;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    private List<Adress> adressList = new ArrayList<>();

    public List<Adress> getAllAddresses() {
        return adressList;
    }

    public Adress getAddressById(Long id) {
        return adressList.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    public Adress createAddress(AddressDTO adressDTO) {
        Adress adress = new Adress((long) (adressList.size() + 1), adressDTO.getName(), adressDTO.getCity(), adressDTO.getPhoneNumber());
        adressList.add(adress);
        return adress;
    }

    public Adress updateAddress(Long id, Adress adressDTO) {
        for (Adress adress : adressList) {
            if (adress.getId().equals(id)) {
                adress.setName(adressDTO.getName());
                adress.setCity(adressDTO.getCity());
                adress.setPhoneNumber(adressDTO.getPhoneNumber());
                return adress;
            }
        }
        return null;
    }

    public void deleteAddress(Long id) {
        adressList.removeIf(adress -> adress.getId().equals(id));
    }
}

package org.example.adreessmanagement.service;

import org.example.adreessmanagement.configuration.RabbitMQConfig;
import org.example.adreessmanagement.dto.AddressDTO;
import org.example.adreessmanagement.model.Address;
import org.example.adreessmanagement.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final AddressRepository addressRepository;
    private final RabbitTemplate rabbitTemplate;

    public AddressService(AddressRepository addressRepository, RabbitTemplate rabbitTemplate) {
        this.addressRepository = addressRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    // Fetch all addresses (cached)
    @Cacheable(value = "addresses")
    public List<Address> getAllAddresses() {
        logger.info("Fetching all addresses from the database...");
        return addressRepository.findAll();
    }

    // Fetch an address by ID (cached)
    @Cacheable(value = "addresses", key = "#id")
    public Optional<Address> getAddressById(Long id) {
        logger.info("Fetching address with ID: {}", id);
        return addressRepository.findById(id);
    }

    // Create a new address, update cache, and send a RabbitMQ message
    @CachePut(value = "addresses", key = "#result.id")
    public Address createAddress(AddressDTO addressDTO) {
        logger.info("Creating a new address...");
        Address address = new Address();
        address.setName(addressDTO.getName());
        address.setAddress(addressDTO.getAddress());
        address.setContactNumber(addressDTO.getContactNumber());
        address.setEmail(addressDTO.getEmail());
        Address savedAddress = addressRepository.save(address);
        logger.info("New address created with ID: {}", savedAddress.getId());

        // Send message to RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, savedAddress);
        logger.info("Message sent to RabbitMQ for Address ID: {}", savedAddress.getId());

        return savedAddress;
    }

    // Update an address, update cache, and notify RabbitMQ
    @CachePut(value = "addresses", key = "#id")
    public Optional<Address> updateAddress(Long id, AddressDTO addressDTO) {
        logger.info("Updating address with ID: {}", id);
        return addressRepository.findById(id).map(existingAddress -> {
            existingAddress.setName(addressDTO.getName());
            existingAddress.setAddress(addressDTO.getAddress());
            existingAddress.setContactNumber(addressDTO.getContactNumber());
            existingAddress.setEmail(addressDTO.getEmail());
            Address updatedAddress = addressRepository.save(existingAddress);
            logger.info("Updated address with ID: {}", id);

            // Send update message to RabbitMQ
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, updatedAddress);
            logger.info("Update message sent to RabbitMQ for Address ID: {}", id);

            return updatedAddress;
        });
    }

    // Delete an address, clear cache, and notify RabbitMQ
    @CacheEvict(value = "addresses", key = "#id")
    public void deleteAddress(Long id) {
        logger.info("Deleting address with ID: {}", id);
        addressRepository.deleteById(id);

        // Send delete event message to RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, "Deleted Address ID: " + id);
        logger.info("Delete message sent to RabbitMQ for Address ID: {}", id);
    }
}

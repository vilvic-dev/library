package com.vdev.library.rest.service;

import com.vdev.library.rest.controller.model.Customer;
import com.vdev.library.rest.exception.NotFoundException;
import com.vdev.library.rest.jpa.model.CustomerEntity;
import com.vdev.library.rest.jpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Optional<Customer> getCustomerById(final String id) {

        final var optionalCustomerEntity = customerRepository.getCustomerEntityById(id);
        if (optionalCustomerEntity.isPresent()) {
            final var customerEntity = optionalCustomerEntity.get();
            return Optional.of(Customer
                    .builder()
                    .id(customerEntity.getId())
                    .name(customerEntity.getName())
                    .address1(customerEntity.getAddress1())
                    .address2(customerEntity.getAddress2())
                    .address3(customerEntity.getAddress3())
                    .address4(customerEntity.getAddress4())
                    .postCode(customerEntity.getPostCode())
                    .telephone(customerEntity.getTelephone())
                    .email(customerEntity.getEmail())
                    .version(customerEntity.getVersion())
                    .created(customerEntity.getCreated())
                    .updated(customerEntity.getUpdated())
                    .build());
        }

        return Optional.empty();
    }

    public List<Customer> findCustomersByName(final String name) {

        final var customers = customerRepository.findCustomerEntityByName(name);
        return customers.stream().map(customerEntity -> Customer
                        .builder()
                        .id(customerEntity.getId())
                        .name(customerEntity.getName())
                        .address1(customerEntity.getAddress1())
                        .address2(customerEntity.getAddress2())
                        .address3(customerEntity.getAddress3())
                        .address4(customerEntity.getAddress4())
                        .postCode(customerEntity.getPostCode())
                        .telephone(customerEntity.getTelephone())
                        .email(customerEntity.getEmail())
                        .version(customerEntity.getVersion())
                        .created(customerEntity.getCreated())
                        .updated(customerEntity.getUpdated())
                        .build())
                .collect(Collectors.toList());
    }

    public boolean doesEmailExist(final String email) {
        return customerRepository.countByEmail(email) > 0;
    }

    public boolean doesEmailExistForAnotherCustomer(final String id, final String email) {
        return customerRepository.countByIdNotAndEmail(id, email) > 0;
    }

    /**
     * Save edit customer.
     *
     * @param id       customer id
     * @param customer customer model
     * @return updated version number
     */
    @Transactional
    public Customer saveEdit(final String id, final Customer customer) {
        final var loadedCustomerEntity = customerRepository.getCustomerEntityById(id).orElseThrow(NotFoundException::new);
        final var createdDateTime = loadedCustomerEntity.getCreated();
        final var customerEntity = new CustomerEntity();
        customerEntity.setId(id);
        customerEntity.setName(customer.getName());
        customerEntity.setAddress1(customer.getAddress1());
        customerEntity.setAddress2(customer.getAddress2());
        customerEntity.setAddress3(customer.getAddress3());
        customerEntity.setAddress4(customer.getAddress4());
        customerEntity.setPostCode(customer.getPostCode());
        customerEntity.setTelephone(customer.getTelephone());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setVersion(customer.getVersion());
        final var savedCustomerEntity = customerRepository.saveAndFlush(customerEntity);
        savedCustomerEntity.setCreated(createdDateTime);
        return buildCustomer(savedCustomerEntity);
    }

    /**
     * Save new customer.
     *
     * @param customer customer model
     * @return customer id
     */
    @Transactional
    public Customer saveNew(final Customer customer) {
        final var customerEntity = new CustomerEntity();
        customerEntity.setName(customer.getName());
        customerEntity.setAddress1(customer.getAddress1());
        customerEntity.setAddress2(customer.getAddress2());
        customerEntity.setAddress3(customer.getAddress3());
        customerEntity.setAddress4(customer.getAddress4());
        customerEntity.setPostCode(customer.getPostCode());
        customerEntity.setTelephone(customer.getTelephone());
        customerEntity.setEmail(customer.getEmail());
        final var savedCustomerEntity = customerRepository.saveAndFlush(customerEntity);
        return buildCustomer(savedCustomerEntity);
    }

    private Customer buildCustomer(final CustomerEntity customerEntity) {
        return Customer
                .builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .address1(customerEntity.getAddress1())
                .address2(customerEntity.getAddress2())
                .address3(customerEntity.getAddress3())
                .address4(customerEntity.getAddress4())
                .postCode(customerEntity.getPostCode())
                .telephone(customerEntity.getTelephone())
                .email(customerEntity.getEmail())
                .version(customerEntity.getVersion())
                .created(customerEntity.getCreated())
                .updated(customerEntity.getUpdated())
                .build();
    }

}

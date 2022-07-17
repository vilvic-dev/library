package com.vdev.library.rest.service;

import com.vdev.library.rest.controller.model.Customer;
import com.vdev.library.rest.jpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Optional<Customer> getCustomerById(final String id) {

        final var optionalCustomer = customerRepository.getCustomerEntityById(id);
        if (optionalCustomer.isPresent()) {
            final var customer = optionalCustomer.get();
            return Optional.of(Customer
                    .builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .address1(customer.getAddress1())
                    .address2(customer.getAddress2())
                    .address3(customer.getAddress3())
                    .address4(customer.getAddress4())
                    .postCode(customer.getPostCode())
                    .telephone(customer.getTelephone())
                    .email(customer.getEmail())
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
                .build())
                .collect(Collectors.toList());
    }


}

package com.vdev.library.rest.service;

import com.vdev.library.rest.controller.model.Customer;
import com.vdev.library.rest.jpa.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.vdev.library.rest.TestConstants.CUSTOMER_ANDREW_SMITH_ID;
import static com.vdev.library.rest.TestConstants.CUSTOMER_LAURA_SMITH_ID;
import static com.vdev.library.rest.TestConstants.CUSTOMER_LEE_JONES_ID;
import static com.vdev.library.rest.TestConstants.ID_INVALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void GetCustomerEntityByName_CustomerExist_RecordReturned() {
        final var optionalCustomer = customerService.getCustomerById(CUSTOMER_LAURA_SMITH_ID);
        assertTrue(optionalCustomer.isPresent());

        final var customer = optionalCustomer.get();
        assertEquals("7408fe93-7557-4281-99ff-f3c2fd38354d", customer.getId());
        assertEquals("Laura Smith", customer.getName());
        assertEquals("Address 1", customer.getAddress1());
        assertEquals("Address 2", customer.getAddress2());
        assertEquals("Address 3", customer.getAddress3());
        assertEquals("Address 4", customer.getAddress4());
        assertEquals("PC12 1AB", customer.getPostCode());
        assertEquals("020 100 2000", customer.getTelephone());
        assertEquals("laura.smith@email.com", customer.getEmail());

    }

    @Test
    void GetCustomerEntityByName_CustomerIdInvalid_NoRecordReturned() {
        final var optionalCustomer = customerService.getCustomerById(ID_INVALID);
        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void FindCustomersByName_NameExist_RecordReturned() {
        final var customers = customerService.findCustomersByName("Smith");
        assertFalse(customers.isEmpty());
        assertEquals(2, customers.size());

        final var customer = customers.get(1);
        assertEquals("7408fe93-7557-4281-99ff-f3c2fd38354d", customer.getId());
        assertEquals("Laura Smith", customer.getName());
        assertEquals("Address 1", customer.getAddress1());
        assertEquals("Address 2", customer.getAddress2());
        assertEquals("Address 3", customer.getAddress3());
        assertEquals("Address 4", customer.getAddress4());
        assertEquals("PC12 1AB", customer.getPostCode());
        assertEquals("020 100 2000", customer.getTelephone());
        assertEquals("laura.smith@email.com", customer.getEmail());

    }

    @Test
    void FindCustomersByName_NameInvalid_NoRecordReturned() {
        final var customers = customerService.getCustomerById(ID_INVALID);
        assertTrue(customers.isEmpty());
    }

    @Test
    void DoesEmailExistForAnotherCustomer_EmailExistsOnAnother_RecordsFound() {
        final var emailExists = customerService.doesEmailExistForAnotherCustomer(CUSTOMER_LEE_JONES_ID, "andrew.smith@email.com");
        assertTrue(emailExists);
    }

    @Test
    void DoesEmailExistForAnotherCustomer_EmailDoesNotExistOnAnother_RecordsFound() {
        final var emailExists = customerService.doesEmailExistForAnotherCustomer(CUSTOMER_ANDREW_SMITH_ID, "andrew.smith@email.com");
        assertFalse(emailExists);
    }

    @Test
    void saveEdit_Valid_DataSaved() {
        final var customer = Customer
                .builder()
                .id(CUSTOMER_ANDREW_SMITH_ID)
                .name("Andrew Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 1AA")
                .telephone("020 100 1000")
                .email("andy.smith@email.com")
                .build();
        customerService.saveEdit(CUSTOMER_ANDREW_SMITH_ID, customer);

        final var optionalCustomerEntity = customerRepository.getCustomerEntityById(CUSTOMER_ANDREW_SMITH_ID);
        assertTrue(optionalCustomerEntity.isPresent());

        final var customerEntity = optionalCustomerEntity.get();
        assertEquals(CUSTOMER_ANDREW_SMITH_ID, customerEntity.getId());
        assertEquals("Andrew Smith", customerEntity.getName());
        assertEquals("Address 1", customerEntity.getAddress1());
        assertEquals("Address 2", customerEntity.getAddress2());
        assertEquals("Address 3", customerEntity.getAddress3());
        assertEquals("Address 4", customerEntity.getAddress4());
        assertEquals("PC12 1AA", customerEntity.getPostCode());
        assertEquals("020 100 1000", customerEntity.getTelephone());
        assertEquals("andy.smith@email.com", customerEntity.getEmail());
    }

}

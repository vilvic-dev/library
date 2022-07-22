package com.vdev.library.rest.service;

import com.vdev.library.rest.controller.model.Customer;
import com.vdev.library.rest.jpa.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static com.vdev.library.rest.TestConstants.CUSTOMER_ANDREW_SMITH_ID;
import static com.vdev.library.rest.TestConstants.CUSTOMER_LAURA_SMITH_ID;
import static com.vdev.library.rest.TestConstants.CUSTOMER_LEE_JONES_ID;
import static com.vdev.library.rest.TestConstants.ID_INVALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        assertEquals(1, customer.getVersion());
        assertEquals(LocalDateTime.of(2022, 7, 22, 12, 0), customer.getCreated());
        assertEquals(LocalDateTime.of(2022, 7, 22, 14, 0), customer.getUpdated());

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
        assertEquals(1, customer.getVersion());
        assertEquals(LocalDateTime.of(2022, 7, 22, 12, 0), customer.getCreated());
        assertEquals(LocalDateTime.of(2022, 7, 22, 14, 0), customer.getUpdated());

    }

    @Test
    void FindCustomersByName_NameInvalid_NoRecordReturned() {
        final var customers = customerService.getCustomerById(ID_INVALID);
        assertTrue(customers.isEmpty());
    }

    @Test
    void DoesEmailExist_EmailExistsOnAnother_RecordsFound() {
        final var emailExists = customerService.doesEmailExist("andrew.smith@email.com");
        assertTrue(emailExists);
    }

    @Test
    void DoesEmailExist_EmailDoesNotExistOnAnother_RecordsFound() {
        final var emailExists = customerService.doesEmailExist("test@email.com");
        assertFalse(emailExists);
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
                .name("Andrew Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 1AA")
                .telephone("020 100 1000")
                .email("andy.smith@email.com")
                .version(1)
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
        assertEquals(2, customerEntity.getVersion());
        assertNotNull(customerEntity.getCreated());
        assertNotNull(customerEntity.getUpdated());
    }

    @Test
    void saveEdit_Stale_DataNotSaved() {
        final var customer = Customer
                .builder()
                .name("Andrew Smith")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 1AA")
                .telephone("020 100 1000")
                .email("andy.smith@email.com")
                .version(0)
                .build();
        Assertions.assertThrows(ObjectOptimisticLockingFailureException.class, () ->
                customerService.saveEdit(CUSTOMER_ANDREW_SMITH_ID, customer)
        );
    }

    @Test
    void saveNew_Valid_DataSaved() {
        final var customer = Customer
                .builder()
                .name("Sarah Doe")
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .address4("Address 4")
                .postCode("PC12 4AA")
                .telephone("020 100 4000")
                .email("sarah.doe@email.com")
                .build();
        final var savedCustomer = customerService.saveNew(customer);

        final var optionalCustomerEntity = customerRepository.getCustomerEntityById(savedCustomer.getId());
        assertTrue(optionalCustomerEntity.isPresent());

        final var customerEntity = optionalCustomerEntity.get();
        assertEquals(savedCustomer.getId(), customerEntity.getId());
        assertEquals("Sarah Doe", customerEntity.getName());
        assertEquals("Address 1", customerEntity.getAddress1());
        assertEquals("Address 2", customerEntity.getAddress2());
        assertEquals("Address 3", customerEntity.getAddress3());
        assertEquals("Address 4", customerEntity.getAddress4());
        assertEquals("PC12 4AA", customerEntity.getPostCode());
        assertEquals("020 100 4000", customerEntity.getTelephone());
        assertEquals("sarah.doe@email.com", customerEntity.getEmail());
        assertEquals(0, customerEntity.getVersion());
        assertNotNull(customerEntity.getCreated());
        assertNotNull(customerEntity.getUpdated());

    }

}

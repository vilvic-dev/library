package com.vdev.library.rest.jpa.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.vdev.library.rest.TestConstants.CUSTOMER_ANDREW_SMITH_ID;
import static com.vdev.library.rest.TestConstants.ID_INVALID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void GetCustomerEntityById_CustomerExist_RecordReturned() {
        final var optionalCustomer = customerRepository.getCustomerEntityById(CUSTOMER_ANDREW_SMITH_ID);
        assertTrue(optionalCustomer.isPresent());
    }

    @Test
    void GetCustomerEntityById_CustomerIdNull_NoRecordReturned() {
        final var optionalCustomer = customerRepository.getCustomerEntityById(null);
        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void GetCustomerEntityById_CustomerIdInvalid_NoRecordReturned() {
        final var optionalCustomer = customerRepository.getCustomerEntityById(ID_INVALID);
        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void FindCustomerEntityByName_SmithExists_RecordsReturned() {
        final var customers = customerRepository.findCustomerEntityByName("Smith");
        assertEquals(2, customers.size());
    }

}

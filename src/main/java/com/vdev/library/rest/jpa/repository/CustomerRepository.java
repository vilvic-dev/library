package com.vdev.library.rest.jpa.repository;

import com.vdev.library.rest.jpa.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    Optional<CustomerEntity> getCustomerEntityById(String id);

    @Query("SELECT c FROM CustomerEntity c WHERE c.name like %?1%")
    List<CustomerEntity> findCustomerEntityByName(String name);

}

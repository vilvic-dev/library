package com.vdev.library.rest.jpa.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
        name = "customer"
)
@Data
public class CustomerEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, length = 40)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String address1;

    @Column(nullable = false, length = 100)
    private String address2;

    @Column(length = 100)
    private String address3;

    @Column(length = 100)
    private String address4;

    @Column(nullable = false, length = 16)
    private String postCode;

    @Column(length = 16)
    private String telephone;

    @Column(nullable = false)
    private String email;

}

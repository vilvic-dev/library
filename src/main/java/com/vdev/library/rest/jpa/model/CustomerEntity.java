package com.vdev.library.rest.jpa.model;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "emailAK", columnNames = {"email"})
        }
)
@Data
public class CustomerEntity implements Serializable {

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

    @Version
    @Column(nullable = false)
    private long version;

    @Column(updatable = false, nullable = false)
    @Generated(GenerationTime.INSERT)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime updated;

    @PrePersist
    public void prePersist() {
        setUpdated(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        setUpdated(LocalDateTime.now());
    }

}

package com.vdev.library.rest.jpa.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(
        name = "book"
)
@Data
public class BookEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, length = 40)
    private String id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 13)
    private String isbn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "authorIdFK"), nullable = false)
    @ToString.Exclude
    private AuthorEntity author;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "categoryIdFK"), nullable = false)
    @ToString.Exclude
    private CategoryEntity category;

}

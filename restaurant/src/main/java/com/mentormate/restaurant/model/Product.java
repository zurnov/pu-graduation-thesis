package com.mentormate.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @NotNull(message = "Name cannot be null!")
    @Size(max = 100, message = "Name cannot be longer thant 100 symbols!")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Description cannot be null!")
    @Size(max = 500, message = "Description cannot be longer thant 500 symbols!")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Price cannot be null!")
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @OneToOne
    private ProductCategory productCategory;

}

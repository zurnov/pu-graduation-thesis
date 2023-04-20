package com.zurnov.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory extends BaseEntity {

    @NotNull(message = "Category name cannot be null!")
    @Size(max = 100, message = "Category name cannot be longer than 100 symbols!")
    @Column(name = "category_name", unique = true)
    private String name;

}

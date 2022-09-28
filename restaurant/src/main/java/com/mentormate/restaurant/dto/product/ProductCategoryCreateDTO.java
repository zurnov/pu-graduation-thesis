package com.mentormate.restaurant.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryCreateDTO implements Serializable {

    @NotNull(message = "Category name cannot be null!")
    @Size(max = 100, message = "Category name cannot be longer than 100 symbols!")
    private String name;
}

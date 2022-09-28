package com.mentormate.restaurant.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO implements Serializable {

    @NotNull(message = "Name cannot be null!")
    @Size(max = 100, message = "Name cannot be longer thant 100 symbols!")
    private String name;

    @NotNull(message = "Description cannot be null!")
    @Size(max = 500, message = "Description cannot be longer thant 500 symbols!")
    private String description;

    @NotNull(message = "Price cannot be null!")
    private BigDecimal price;

    private ProductCategoryDTO productCategory;
}

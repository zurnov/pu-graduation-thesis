package com.mentormate.restaurant.dto.product;

import com.mentormate.restaurant.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO extends BaseDTO {

    @NotNull(message = "Category name cannot be null!")
    @Size(max = 100, message = "Category name cannot be longer than 100 symbols!")
    private String name;

}

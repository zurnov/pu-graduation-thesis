package com.mentormate.restaurant.dto.product;

import com.mentormate.restaurant.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends BaseDTO {

    private String name;

    private String description;

    private BigDecimal price;

    private Boolean isDeleted;

    private ProductCategoryDTO productCategory;
}

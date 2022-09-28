package com.mentormate.restaurant.dto.order_product;

import com.mentormate.restaurant.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO implements Serializable {
    
    @NotNull(message = "Product cannot be null!")
    private ProductDTO product;

    @Min(value = 1, message = "Quantity cannot be less than 1!")
    @NotNull(message = "Quantity cannot be null!")
    private Integer quantity;
}

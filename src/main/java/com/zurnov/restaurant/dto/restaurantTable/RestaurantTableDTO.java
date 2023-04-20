package com.zurnov.restaurant.dto.restaurantTable;

import com.zurnov.restaurant.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantTableDTO extends BaseDTO {

    @NotNull(message = "Restaurant table number cannot be null!")
    private int tableNumber;
}

package com.zurnov.restaurant.dto.order;

import com.zurnov.restaurant.dto.BaseDTO;
import com.zurnov.restaurant.model.enumeration.OrderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO extends BaseDTO {

    @NotNull(message = "Status type cannot be null!")
    private OrderEnum orderEnum;
}

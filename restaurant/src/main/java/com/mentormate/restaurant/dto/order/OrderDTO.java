package com.mentormate.restaurant.dto.order;

import com.mentormate.restaurant.dto.BaseDTO;
import com.mentormate.restaurant.dto.order_product.OrderProductDTO;
import com.mentormate.restaurant.dto.restaurantTable.RestaurantTableDTO;
import com.mentormate.restaurant.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO {

    private RestaurantTableDTO restaurantTable;

    private UserDTO user;

    @NotNull(message = "Date cannot be null!")
    private OffsetDateTime createDate;

    private OrderStatusDTO orderStatus;

    private BigDecimal totalPrice;

    private List<OrderProductDTO> orderProduct;
}

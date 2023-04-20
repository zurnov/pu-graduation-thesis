package com.zurnov.restaurant.dto.order;

import com.zurnov.restaurant.dto.order_product.OrderProductDTO;
import com.zurnov.restaurant.dto.restaurantTable.RestaurantTableDTO;
import com.zurnov.restaurant.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDTO implements Serializable {

    @NotNull(message = "Restaurant table cannot be null!")
    private RestaurantTableDTO restaurantTable;

    @NotNull(message = "User cannot be null!")
    private UserDTO user;

    @NotNull(message = "Order status cannot be null!")
    private OrderStatusDTO orderStatus;

    @NotNull(message = "Products cannot be null!")
    @NotEmpty(message = "Order products cannot be empty!")
    private List<OrderProductDTO> orderProducts;
}

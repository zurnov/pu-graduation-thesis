package com.zurnov.restaurant.util.converter.dto;

import com.zurnov.restaurant.dto.order.OrderStatusDTO;
import com.zurnov.restaurant.model.OrderStatus;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderStatusDtoConverter {

    /**
     * Converts the orderStatus Entity to DTO Object
     *
     * @param orderStatus Entity to be converted
     * @return orderStatusDTO the converted DTO
     */
    public static OrderStatusDTO convertEntityToDto(OrderStatus orderStatus) {
        
        if (orderStatus == null) {
            return null;
        }
        
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        
        orderStatusDTO.setId(orderStatus.getId());
        orderStatusDTO.setOrderEnum(orderStatus.getOrderEnum());
        
        return orderStatusDTO;
    }

    /**
     * Converts the orderStatus DTO Object to Entity
     *
     * @param orderStatusDTO to be converted
     * @return orderStatus the converted Entity
     */
    public static OrderStatus convertDtoToEntity(OrderStatusDTO orderStatusDTO) {
        OrderStatus orderStatus = new OrderStatus();
        
        orderStatus.setId(orderStatusDTO.getId());
        orderStatus.setOrderEnum(orderStatusDTO.getOrderEnum());
        
        return orderStatus;
    }
}

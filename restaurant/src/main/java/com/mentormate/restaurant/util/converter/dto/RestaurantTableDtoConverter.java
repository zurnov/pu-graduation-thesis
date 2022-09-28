package com.mentormate.restaurant.util.converter.dto;

import com.mentormate.restaurant.dto.restaurantTable.RestaurantTableDTO;
import com.mentormate.restaurant.model.RestaurantTable;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RestaurantTableDtoConverter {

    /**
     * Converts the restaurantTable Entity to DTO Object
     *
     * @param restaurantTable Entity to be converted
     * @return restaurantTableDTO the converted DTO
     */
    public static RestaurantTableDTO convertEntityToDto(RestaurantTable restaurantTable) {
        
        if (restaurantTable == null) {
            return null;
        }
        
        RestaurantTableDTO restaurantTableDTO = new RestaurantTableDTO();
        
        restaurantTableDTO.setId(restaurantTable.getId());
        restaurantTableDTO.setTableNumber(restaurantTable.getTableNumber());
        
        return restaurantTableDTO;
    }

    /**
     * Converts the restaurantTableDTO DTO Object to Entity
     *
     * @param restaurantTableDTO to be converted
     * @return restaurantTable the converted Entity
     */
    public static RestaurantTable convertDtoToEntity(RestaurantTableDTO restaurantTableDTO) {
        RestaurantTable restaurantTable = new RestaurantTable();
        
        restaurantTable.setId(restaurantTableDTO.getId());
        restaurantTable.setTableNumber(restaurantTableDTO.getTableNumber());
        
        return restaurantTable;
    }
}

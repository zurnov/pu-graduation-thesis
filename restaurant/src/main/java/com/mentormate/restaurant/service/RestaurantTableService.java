package com.mentormate.restaurant.service;

import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.RestaurantTable;

import java.util.List;

public interface RestaurantTableService {

    /**
     * This method finds and returns RestaurantTable that corresponds to the tableNumber or else list of all RestaurantTables
     *
     * @param tableNumber table number with which we want to filter all restaurant tables
     * @return List of {@link RestaurantTable} objects
     */
    List<RestaurantTable> findTables(Integer tableNumber);

    /**
     * This method finds and returns RestaurantTable or else ApiRequestNotFoundException will be thrown
     * if none of them matches the restaurant table id.
     *
     * @param id id with which we want to filter all restaurant tables
     * @return The found {@link RestaurantTable} object
     * @throws NotFoundException when there is no such restaurant table with the given id
     */
    RestaurantTable findById(Integer id);

    /**
     * Checks if restaurant table with the tableId provided exist.
     *
     * @param tableId tableId for which we want to filter restaurant tables
     * @return true if restaurant table exists, otherwise false
     */
    boolean existsById(int tableId);
}

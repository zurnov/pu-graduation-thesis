package com.mentormate.restaurant.service.impl;

import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.RestaurantTable;
import com.mentormate.restaurant.repository.RestaurantTableRepository;
import com.mentormate.restaurant.service.RestaurantTableService;
import com.mentormate.restaurant.util.HttpStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {


    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableServiceImpl(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    @Override
    public List<RestaurantTable> findTables(Integer tableNumber) {

        List<RestaurantTable> restaurantTables = new ArrayList<>();

        if (tableNumber != null) {
            Optional<RestaurantTable> optionalRestaurantTable = restaurantTableRepository.findRestaurantTableByTableNumber(tableNumber);

            optionalRestaurantTable.ifPresent(restaurantTables::add);
        } else {
            restaurantTables.addAll(restaurantTableRepository.findAll());
        }

        return restaurantTables;
    }

    @Override
    public RestaurantTable findById(Integer id) {
        Optional<RestaurantTable> optionalRestaurantTable = restaurantTableRepository.findById(id);

        return optionalRestaurantTable.orElseThrow(
                () -> new NotFoundException(String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Restaurant table", id)));
    }

    @Override
    public boolean existsById(int tableId) {
        return restaurantTableRepository.existsById(tableId);
    }
}

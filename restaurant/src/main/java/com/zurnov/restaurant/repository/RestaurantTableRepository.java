package com.zurnov.restaurant.repository;

import com.zurnov.restaurant.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {

    Optional<RestaurantTable> findRestaurantTableByTableNumber(Integer tableNumber);
    
    boolean existsById(int tableId);
}

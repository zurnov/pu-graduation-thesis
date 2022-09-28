package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {

    Optional<RestaurantTable> findRestaurantTableByTableNumber(Integer tableNumber);
    
    boolean existsById(int tableId);
}

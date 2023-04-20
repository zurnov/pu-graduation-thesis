package com.zurnov.restaurant.repository;

import com.zurnov.restaurant.model.OrderStatus;
import com.zurnov.restaurant.model.enumeration.OrderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    
    Optional<OrderStatus> findOrderStatusByOrderEnum(OrderEnum orderEnum);
    
    boolean existsById(int orderStatusId);
}

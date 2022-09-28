package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.model.OrderStatus;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    
    Optional<OrderStatus> findOrderStatusByOrderEnum(OrderEnum orderEnum);
    
    boolean existsById(int orderStatusId);
}

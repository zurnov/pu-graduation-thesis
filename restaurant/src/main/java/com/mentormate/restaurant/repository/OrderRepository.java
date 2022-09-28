package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.model.Order;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findByRestaurantTableTableNumber(int restaurantTable, Pageable pageable);

    Page<Order> findOrderByOrderStatusOrderEnumIn(List<OrderEnum> orderEnums, Pageable pageable);

    Page<Order> findByUserId(int userId, Pageable pageable);

    Page<Order> findByCreateDateBetween(OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable);
}

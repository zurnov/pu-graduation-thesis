package com.zurnov.restaurant.service;

import com.zurnov.restaurant.model.OrderStatus;
import com.zurnov.restaurant.model.enumeration.OrderEnum;

import java.util.List;

public interface OrderStatusService {

    /**
     * This method finds and returns OrderStatus or else null if none of them matches the order status id.
     *
     * @param id order id with which we want to filter all order statuses
     * @return The found {@link OrderStatus} object
     */
    OrderStatus findById(Integer id);

    /**
     * This method finds and returns OrderStatus that corresponds to the orderEnum or else list of all OrderStatuses
     *
     * @param orderEnum order enum with which we want to filter all order statuses
     * @return List of {@link OrderStatus} objects
     */
    List<OrderStatus> findOrderStatuses(OrderEnum orderEnum);

    /**
     * Checks if order status with the id provided exist.
     * @param orderStatusId orderStatusId for which we want to filter order statuses
     * @return true if order status exists, otherwise false
     */
    boolean existsById(int orderStatusId);
}

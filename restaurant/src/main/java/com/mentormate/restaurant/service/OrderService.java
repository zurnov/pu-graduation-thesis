package com.mentormate.restaurant.service;

import com.mentormate.restaurant.exception.BadRequestException;
import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.Order;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import org.springframework.data.domain.Page;

import java.time.OffsetDateTime;
import java.util.List;

public interface OrderService {

    /**
     * This method finds and returns Order or else ApiRequestNotFoundException will be thrown
     * if none of them matches the order id.
     *
     * @param orderId order id with which we want to filter all orders
     * @return The found {@link Order} object
     * @throws NotFoundException when there is no such object with the given orderId
     */
    Order findById(int orderId);

    /**
     * This method finds and returns Order that corresponds to the table number or else list of all Orders
     *
     * @param tableNumber table number with which we want to filter all orders
     * @param page        zero-based page index, must not be negative.
     * @param pageSize    the size of the page to be returned, must be greater than 0.
     * @return List of {@link Order} objects
     */
    Page<Order> findByTableNumber(Integer tableNumber, int page, int pageSize);

    /**
     * This method finds and returns Order that corresponds to the order enum or else list of all Orders
     *
     * @param orderEnums order enum with which we want to filter all orders
     * @param page       zero-based page index, must not be negative.
     * @param pageSize   the size of the page to be returned, must be greater than 0.
     * @return List of {@link Order} objects
     */
    Page<Order> findByOrderStatuses(List<OrderEnum> orderEnums, int page, int pageSize);

    /**
     * This method finds and returns Order that corresponds to the user id or else list of all Orders
     *
     * @param userId   user id with which we want to filter all orders
     * @param page     zero-based page index, must not be negative.
     * @param pageSize the size of the page to be returned, must be greater than 0.
     * @return List of {@link Order} objects
     */
    Page<Order> findByUserId(Integer userId, int page, int pageSize);

    /**
     * This method finds and returns list of Orders that corresponds to the startDate and endDate
     * or else throws ApiRequestBadRequestException
     *
     * @param startDate start date with which we want to filter all orders
     * @param endDate   end date with which we want to filter all orders
     * @param page      zero-based page index, must not be negative.
     * @param pageSize  the size of the page to be returned, must be greater than 0.
     * @return List of {@link Order} objects
     * @throws BadRequestException when startDate is after the endDate
     */
    Page<Order> findByCreationDate(OffsetDateTime startDate, OffsetDateTime endDate, int page, int pageSize);

    /**
     * Saves and returns the saved Order or else throws ApiRequestBadRequestException if the Order is null.
     *
     * @param order order to be saved
     * @return The saved {@link Order} object
     * @throws BadRequestException when the given order object is null
     */
    Order create(Order order);

    /**
     * Finds and updates the Order by given order id. If there is no such element ApiRequestNotFoundException will be thrown
     *
     * @param orderId id of the order that we want to update
     * @param order   order object that will be overriding the existing one
     * @return The updated {@link Order} object
     * @throws BadRequestException when given order object is null
     *                             or the given id is different from given order object id.
     * @throws NotFoundException   when there is no such object with id of the given id
     *                             or given object order objects are invalid.
     */
    Order update(int orderId, Order order);

    /**
     * Finds and soft deletes the Order by given id. If there is no such element ApiRequestNotFoundException will be thrown.
     *
     * @param orderId id of the order that we want to soft delete
     * @return The deleted {@link Order} object
     * @throws NotFoundException when there is no such object with the given orderId
     */
    Order delete(int orderId);
}

package com.mentormate.restaurant.service.impl;

import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.OrderStatus;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import com.mentormate.restaurant.repository.OrderStatusRepository;
import com.mentormate.restaurant.service.OrderStatusService;
import com.mentormate.restaurant.util.HttpStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {


    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public OrderStatus findById(Integer id) {
        Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findById(id);
        
        return optionalOrderStatus.orElseThrow(
                () -> new NotFoundException(String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order status", id)));
        
    }

    @Override
    public List<OrderStatus> findOrderStatuses(OrderEnum orderEnum) {

        List<OrderStatus> orderStatuses = new ArrayList<>();

        if (orderEnum != null) {
            Optional<OrderStatus> optionalOrderStatus = orderStatusRepository.findOrderStatusByOrderEnum(orderEnum);

            optionalOrderStatus.ifPresent(orderStatuses::add);
        } else {
            orderStatuses.addAll(orderStatusRepository.findAll());
        }

        return orderStatuses;
    }

    @Override
    public boolean existsById(int orderStatusId) {
        return orderStatusRepository.existsById(orderStatusId);
    }
}

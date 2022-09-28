package com.mentormate.restaurant.service;

import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.OrderStatus;
import com.mentormate.restaurant.repository.OrderStatusRepository;
import com.mentormate.restaurant.service.impl.OrderStatusServiceImpl;
import com.mentormate.restaurant.util.HttpStatusHelper;
import com.mentormate.restaurant.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderStatusServiceTests {

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusServiceImpl orderStatusServiceImpl;


    @Test
    void whenGetOrderStatusById_thenOk() {
        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1, TestHelper.ORDER_STATUS_ENUM);

        when(orderStatusRepository.findById(orderStatus.getId())).thenReturn(Optional.of(orderStatus));

        OrderStatus actualOrderStatus = orderStatusServiceImpl.findById(orderStatus.getId());

        assertEquals(orderStatus.getId(), actualOrderStatus.getId());
        assertEquals(orderStatus.getOrderEnum(), actualOrderStatus.getOrderEnum());
    }

    @Test
    void whenFindOrderStatusById_thenNotFoundException() {

        when(orderStatusRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> orderStatusServiceImpl.findById(TestHelper.ID_1),
                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order status", TestHelper.ID_1));
    }

    @Test
    void whenGetOrderStatusByOrderEnum_thenOk() {

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1, TestHelper.ORDER_STATUS_ENUM);

        List<OrderStatus> expectedOrderStatuses = List.of(orderStatus);

        when(orderStatusRepository.findOrderStatusByOrderEnum(orderStatus.getOrderEnum()))
                .thenReturn(Optional.of(orderStatus));

        List<OrderStatus> actualOrderStatus = orderStatusServiceImpl.findOrderStatuses(orderStatus.getOrderEnum());

        assertEquals(expectedOrderStatuses.size(), actualOrderStatus.size());
        assertEquals(expectedOrderStatuses.get(0).getOrderEnum(), actualOrderStatus.get(0).getOrderEnum());
    }

    @Test
    void whenGetOrderStatusByOrderEnumWithoutEnum_thenOk() {

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1, TestHelper.ORDER_STATUS_ENUM);

        List<OrderStatus> expectedOrderStatuses = List.of(orderStatus);
        when(orderStatusRepository.findAll()).thenReturn(expectedOrderStatuses);

        List<OrderStatus> actualOrderStatus = orderStatusServiceImpl.findOrderStatuses(null);

        assertEquals(expectedOrderStatuses.get(0).getOrderEnum(), actualOrderStatus.get(0).getOrderEnum());
        assertEquals(expectedOrderStatuses.size(), actualOrderStatus.size());
    }
}

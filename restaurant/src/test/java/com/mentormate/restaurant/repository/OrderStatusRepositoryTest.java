package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.config.PostgreSQLExtension;
import com.mentormate.restaurant.config.RestaurantPostgreSQLContainer;
import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.OrderStatus;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import com.mentormate.restaurant.util.HttpStatusHelper;
import com.mentormate.restaurant.util.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(value = PostgreSQLExtension.class)
class OrderStatusRepositoryTest extends RestaurantPostgreSQLContainer {

    private final OrderStatusRepository orderStatusRepository;

    @Autowired
    OrderStatusRepositoryTest(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Test
    void givenOrderStatus_findOrderStatusByOrderEnum_thenReturnOrderStatus() {
        OrderStatus expectedOrderStatus = 
                orderStatusRepository.findOrderStatusByOrderEnum(OrderEnum.ACTIVE)
                .orElseThrow(() -> new NotFoundException(
                        String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order Status", 1)));

        Optional<OrderStatus> actualOrderStatus = orderStatusRepository.findOrderStatusByOrderEnum(
                expectedOrderStatus.getOrderEnum());
        
        Assertions.assertFalse(actualOrderStatus.isEmpty());
        Assertions.assertEquals(expectedOrderStatus.getOrderEnum(), actualOrderStatus.get().getOrderEnum());
    }

    @Test
    void givenOrderStatus_existsById_thenReturnTrue() {
        OrderStatus expectedOrderStatus = 
                orderStatusRepository.findOrderStatusByOrderEnum(TestHelper.ORDER_STATUS_ENUM)
                .orElseThrow(() -> new NotFoundException(
                        String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order Status", 1)));
        
        boolean actualOrderStatus = orderStatusRepository.existsById(expectedOrderStatus.getId());

        assertTrue(actualOrderStatus);
    }
}

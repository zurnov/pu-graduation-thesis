package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.config.PostgreSQLExtension;
import com.mentormate.restaurant.config.RestaurantPostgreSQLContainer;
import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.*;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import com.mentormate.restaurant.model.enumeration.RoleEnum;
import com.mentormate.restaurant.util.HttpStatusHelper;
import com.mentormate.restaurant.util.TestHelper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(value = PostgreSQLExtension.class)
class OrderRepositoryTest extends RestaurantPostgreSQLContainer {

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    OrderRepositoryTest(OrderRepository orderRepository,
                        OrderStatusRepository orderStatusRepository,
                        RestaurantTableRepository restaurantTableRepository,
                        ProductCategoryRepository productCategoryRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository,
                        RoleRepository roleRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Test
    void whenFindByRestaurantTableTableNumber_thenOk() {

        RestaurantTable restaurantTable = 
                restaurantTableRepository.findRestaurantTableByTableNumber(TestHelper.RESTAURANT_TABLE_NUMBER)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", TestHelper.RESTAURANT_TABLE_NUMBER)));
        
        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));
        
        User user = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(user);
        
        ProductCategory productCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));
        
        productCategoryRepository.saveAndFlush(productCategory);

        Product product = TestHelper.createProduct(
                null,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        productRepository.saveAndFlush(product);
        
        OrderStatus orderStatus =
                orderStatusRepository.findOrderStatusByOrderEnum(OrderEnum.ACTIVE)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order Status", 1)));
        
        Order expectedOrder = TestHelper.createOrder(
                null,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);
        
        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder.setOrderProducts(orderProducts);
        
        orderRepository.saveAndFlush(expectedOrder);
        
            Page<Order> actualOrders = orderRepository.findByRestaurantTableTableNumber(
                expectedOrder.getRestaurantTable().getTableNumber(), PageRequest.of(0, 1));
            
            Assertions.assertFalse(actualOrders.isEmpty());
            MatcherAssert.assertThat(actualOrders.getTotalElements(), is(1L));
    }

    @Test
    void whenFindOrderByOrderStatusOrderEnumIn_thenOk() {

        RestaurantTable restaurantTable =
                restaurantTableRepository.findRestaurantTableByTableNumber(TestHelper.RESTAURANT_TABLE_NUMBER)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", TestHelper.RESTAURANT_TABLE_NUMBER)));

        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));

        User user = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(user);

        ProductCategory productCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));

        productCategoryRepository.saveAndFlush(productCategory);

        Product product = TestHelper.createProduct(
                null,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        productRepository.saveAndFlush(product);

        OrderStatus orderStatus =
                orderStatusRepository.findOrderStatusByOrderEnum(OrderEnum.ACTIVE)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order Status", 1)));

        Order expectedOrder = TestHelper.createOrder(
                null,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder.setOrderProducts(orderProducts);

        orderRepository.saveAndFlush(expectedOrder);

        Page<Order> actualOrders = orderRepository.findOrderByOrderStatusOrderEnumIn(
                List.of(expectedOrder.getOrderStatus().getOrderEnum()), PageRequest.of(0, 1));

        Assertions.assertFalse(actualOrders.isEmpty());
        MatcherAssert.assertThat(actualOrders.getTotalElements(), is(1L));
    }

    @Test
    void whenFindByUserId_thenOk() {

        RestaurantTable restaurantTable =
                restaurantTableRepository.findRestaurantTableByTableNumber(TestHelper.RESTAURANT_TABLE_NUMBER)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", TestHelper.RESTAURANT_TABLE_NUMBER)));

        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));

        User user = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(user);

        ProductCategory productCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));

        productCategoryRepository.saveAndFlush(productCategory);

        Product product = TestHelper.createProduct(
                null,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        productRepository.saveAndFlush(product);

        OrderStatus orderStatus =
                orderStatusRepository.findOrderStatusByOrderEnum(OrderEnum.ACTIVE)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order Status", 1)));

        Order expectedOrder = TestHelper.createOrder(
                null,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder.setOrderProducts(orderProducts);

        orderRepository.saveAndFlush(expectedOrder);

        Page<Order> actualOrders = orderRepository.findByUserId(
                expectedOrder.getUser().getId(), PageRequest.of(0, 1));

        Assertions.assertFalse(actualOrders.isEmpty());
        MatcherAssert.assertThat(actualOrders.getTotalElements(), is(1L));
    }

    @Test
    void whenFindByCreateDateBetween_thenOk() {
        
        RestaurantTable restaurantTable =
                restaurantTableRepository.findRestaurantTableByTableNumber(TestHelper.RESTAURANT_TABLE_NUMBER)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", TestHelper.RESTAURANT_TABLE_NUMBER)));

        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));

        User user = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(user);

        ProductCategory productCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));

        productCategoryRepository.saveAndFlush(productCategory);

        Product product = TestHelper.createProduct(
                null,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        productRepository.saveAndFlush(product);

        OrderStatus orderStatus =
                orderStatusRepository.findOrderStatusByOrderEnum(OrderEnum.ACTIVE)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order Status", 1)));

        Order expectedOrder = TestHelper.createOrder(
                null,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder.setOrderProducts(orderProducts);

        orderRepository.saveAndFlush(expectedOrder);

        Page<Order> actualOrders = orderRepository.findByCreateDateBetween(
                expectedOrder.getCreateDate().minusDays(1), expectedOrder.getCreateDate(), PageRequest.of(0, 1));

        Assertions.assertFalse(actualOrders.isEmpty());
        MatcherAssert.assertThat(actualOrders.getTotalElements(), is(1L));
    }
}

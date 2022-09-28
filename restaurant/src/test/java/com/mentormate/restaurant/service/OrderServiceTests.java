package com.mentormate.restaurant.service;

import com.mentormate.restaurant.exception.BadRequestException;
import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.exception.ValidationException;
import com.mentormate.restaurant.model.*;
import com.mentormate.restaurant.repository.OrderRepository;
import com.mentormate.restaurant.service.impl.OrderServiceImpl;
import com.mentormate.restaurant.service.impl.ProductServiceImpl;
import com.mentormate.restaurant.service.impl.UserServiceImpl;
import com.mentormate.restaurant.util.HttpStatusHelper;
import com.mentormate.restaurant.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    RestaurantTableService restaurantTableService;

    @Mock
    OrderStatusService orderStatusService;

    @Mock
    ProductServiceImpl productService;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void whenGetOrderByIdWithExistingOrder_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder = TestHelper.createOrder(
                TestHelper.ID_1,
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

        when(orderRepository.findById(expectedOrder.getId())).thenReturn(Optional.of(expectedOrder));

        Order actualOrder = orderService.findById(expectedOrder.getId());

        assertEquals(expectedOrder.getId(), actualOrder.getId());
    }

    @Test
    void whenGetOrderByIdWithoutExistingOrder_thenNotFoundException() {

        when(orderRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> orderService.findById(TestHelper.ID_1),
                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order", TestHelper.ID_1));
    }

    @Test
    void whenGetOrderByTableNumberWithExistingTableNumber_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder = TestHelper.createOrder(
                TestHelper.ID_1,
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

        List<Order> expectedOrders = List.of(expectedOrder);

        Page<Order> expectedPageOrders = new PageImpl<>(expectedOrders);

        when(orderRepository.findByRestaurantTableTableNumber(
                expectedOrder.getRestaurantTable().getTableNumber(), TestHelper.PAGE_REQUEST))
                .thenReturn(expectedPageOrders);

        Page<Order> actualOrders = orderService.findByTableNumber(
                expectedOrder.getRestaurantTable().getTableNumber(), TestHelper.PAGE_0, TestHelper.PAGE_SIZE_1);

        assertEquals(
                expectedPageOrders.getTotalElements(),
                actualOrders.getTotalElements());
    }

    @Test
    void whenGetOrderByTableNumberWithNotExistingPage_thenBadRequestException() {

        Page<Order> expectedPage = new PageImpl<>(Collections.emptyList(), Pageable.ofSize(1), 1);

        PageRequest pageRequest = PageRequest.of(TestHelper.PAGE_5, TestHelper.PAGE_SIZE_1);

        when(orderRepository.findByRestaurantTableTableNumber(TestHelper.RESTAURANT_TABLE_NUMBER, pageRequest))
                .thenReturn(expectedPage);
        
        assertThrows(BadRequestException.class,
                () -> orderService.findByTableNumber(
                        TestHelper.RESTAURANT_TABLE_NUMBER, TestHelper.PAGE_5, TestHelper.PAGE_SIZE_1),
                String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_PAGE, TestHelper.PAGE_0));
    }

    @Test
    void whenGetOrderByTableNumberWithoutExistingTableNumber_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder1 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        Order expectedOrder2 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder1,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder1.setOrderProducts(orderProducts);

        expectedOrder2.setOrderProducts(orderProducts);

        List<Order> expectedOrders = List.of(expectedOrder1, expectedOrder2);

        Page<Order> expectedPageOrders = new PageImpl<>(expectedOrders);

        when(orderRepository.findAll(TestHelper.PAGE_REQUEST)).thenReturn(expectedPageOrders);

        Page<Order> actualOrders = orderService.findByTableNumber(
                null, TestHelper.PAGE_0, TestHelper.PAGE_SIZE_1);

        assertEquals(expectedPageOrders.getTotalElements(), actualOrders.getTotalElements());
    }

    @Test
    void whenGetOrderByOrderStatusWithoutExistingOrderStatus_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder1 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        Order expectedOrder2 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder1,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder1.setOrderProducts(orderProducts);

        expectedOrder2.setOrderProducts(orderProducts);

        List<Order> expectedOrders = List.of(expectedOrder1, expectedOrder2);

        Page<Order> expectedPaginatedOrders = new PageImpl<>(expectedOrders);

        when(orderRepository.findOrderByOrderStatusOrderEnumIn(
                Collections.emptyList(), TestHelper.PAGE_REQUEST)).thenReturn(Page.empty());

        when(orderRepository.findAll(TestHelper.PAGE_REQUEST)).thenReturn(expectedPaginatedOrders);

        Page<Order> actualOrders = orderService.findByOrderStatuses(
                Collections.emptyList(), TestHelper.PAGE_0, TestHelper.PAGE_SIZE_1);

        assertEquals(expectedPaginatedOrders.getTotalElements(), actualOrders.getTotalElements());
        assertEquals(expectedPaginatedOrders, actualOrders);
    }

    @Test
    void whenGetOrderByOrderStatusWithExistingOrderStatus_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder1 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        Order expectedOrder2 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder1,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder1.setOrderProducts(orderProducts);

        expectedOrder2.setOrderProducts(orderProducts);

        List<Order> expectedOrders = List.of(expectedOrder1, expectedOrder2);

        Page<Order> expectedPageOrders = new PageImpl<>(expectedOrders);

        when(orderRepository.findOrderByOrderStatusOrderEnumIn
                (List.of(orderStatus.getOrderEnum()), TestHelper.PAGE_REQUEST)).thenReturn(expectedPageOrders);

        Page<Order> actualOrders = orderService.findByOrderStatuses(
                List.of(expectedOrder1.getOrderStatus().getOrderEnum()), TestHelper.PAGE_0, TestHelper.PAGE_SIZE_1);

        assertEquals(expectedPageOrders.getTotalElements(), actualOrders.getTotalElements());
        assertEquals(expectedPageOrders, actualOrders);
    }

    @Test
    void whenGetOrderByUserIdWithExistingUserId_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder1 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        Order expectedOrder2 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder1,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder1.setOrderProducts(orderProducts);

        expectedOrder2.setOrderProducts(orderProducts);

        List<Order> expectedOrders = List.of(expectedOrder1, expectedOrder2);

        Page<Order> expectedPageOrders = new PageImpl<>(expectedOrders);

        when(orderRepository.findByUserId
                (expectedOrder1.getUser().getId(), TestHelper.PAGE_REQUEST)).thenReturn(expectedPageOrders);

        Page<Order> actualOrders = orderService.findByUserId(
                expectedOrder1.getUser().getId(), TestHelper.PAGE_0, TestHelper.PAGE_SIZE_1);

        assertEquals(expectedPageOrders.getTotalElements(), actualOrders.getTotalElements());
        assertEquals(expectedPageOrders, actualOrders);
    }

    @Test
    void whenGetOrderByUserIdWithoutExistingUserId_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder1 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        Order expectedOrder2 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder1,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder1.setOrderProducts(orderProducts);

        expectedOrder2.setOrderProducts(orderProducts);

        List<Order> expectedOrders = List.of(expectedOrder1, expectedOrder2);

        Page<Order> expectedPageOrders = new PageImpl<>(expectedOrders);

        when(orderRepository.findAll(TestHelper.PAGE_REQUEST)).thenReturn(expectedPageOrders);

        Page<Order> actualOrders = orderService.findByUserId(null, TestHelper.PAGE_0, TestHelper.PAGE_SIZE_1);

        assertEquals(expectedPageOrders.getTotalElements(), actualOrders.getTotalElements());
        assertEquals(expectedPageOrders, actualOrders);
    }

    @Test
    void whenGetOrderBetweenDatesWithoutExistingDates_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder1 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        Order expectedOrder2 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder1,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder1.setOrderProducts(orderProducts);

        expectedOrder2.setOrderProducts(orderProducts);

        List<Order> expectedOrders = List.of(expectedOrder1, expectedOrder2);

        Page<Order> expectedPageOrders = new PageImpl<>(expectedOrders);

        when(orderRepository.findAll(TestHelper.PAGE_REQUEST)).thenReturn(expectedPageOrders);

        Page<Order> actualOrders = orderService.findByCreationDate(
                null, null, TestHelper.PAGE_0, TestHelper.PAGE_SIZE_1);

        assertEquals(expectedPageOrders.getTotalElements(), actualOrders.getTotalElements());
        assertEquals(expectedPageOrders, actualOrders);
    }

    @Test
    void whenGetOrderBetweenDatesWithIncorrectDates_thenBadRequestException() {

        assertThrows(BadRequestException.class,
                () -> orderService.findByCreationDate(
                        TestHelper.OFFSET_DATE_TIME,
                        TestHelper.OFFSET_DATE_TIME_MINUS_TWO_DAYS,
                        TestHelper.PAGE_0,
                        TestHelper.PAGE_SIZE_1),
                String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_DATE, "Orders"));

    }

    @Test
    void whenGetOrderBetweenDatesWithCorrectDates_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder1 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        Order expectedOrder2 = TestHelper.createOrder(
                TestHelper.ID_1,
                restaurantTable, user,
                TestHelper.OFFSET_DATE_TIME,
                orderStatus,
                Collections.emptyList());

        OrderProductId orderProductId = TestHelper.createOrderProductId(
                TestHelper.ID_1,
                TestHelper.ID_2);

        OrderProduct orderProduct = TestHelper.createOrderProduct(
                orderProductId,
                expectedOrder1,
                product,
                TestHelper.ORDER_PRODUCTS_QUANTITY);

        List<OrderProduct> orderProducts = List.of(orderProduct);

        expectedOrder1.setOrderProducts(orderProducts);

        expectedOrder2.setOrderProducts(orderProducts);

        List<Order> expectedOrders = List.of(expectedOrder1, expectedOrder2);

        Page<Order> expectedPageOrders = new PageImpl<>(expectedOrders);

        when(orderRepository.findByCreateDateBetween(
                TestHelper.OFFSET_DATE_TIME_MINUS_TWO_DAYS,
                TestHelper.OFFSET_DATE_TIME, TestHelper.PAGE_REQUEST)).thenReturn(expectedPageOrders);

        Page<Order> actualOrders = orderService.findByCreationDate(
                TestHelper.OFFSET_DATE_TIME_MINUS_TWO_DAYS,
                TestHelper.OFFSET_DATE_TIME,
                TestHelper.PAGE_0,
                TestHelper.PAGE_SIZE_1);

        assertEquals(expectedPageOrders.getTotalElements(), actualOrders.getTotalElements());
        assertEquals(expectedPageOrders, actualOrders);
    }

    @Test
    void whenCreateOrderWithoutOrderProvided_thenBadRequestException() {

        assertThrows(BadRequestException.class,
                () -> orderService.create(null),
                String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_CREATE, "Order"));
    }

    @Test
    void whenCreateOrderWithOrderProvidedAndObjectsNotExists_thenValidationException() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder = TestHelper.createOrder(
                TestHelper.ID_1,
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

        when(userService.existsById(expectedOrder.getId()))
                .thenReturn(TestHelper.IS_DELETED_FALSE);
        when(restaurantTableService.existsById(expectedOrder.getRestaurantTable().getId()))
                .thenReturn(TestHelper.IS_DELETED_FALSE);
        when(orderStatusService.existsById(expectedOrder.getOrderStatus().getId()))
                .thenReturn(TestHelper.IS_DELETED_FALSE);
        when(productService.existsByIdIn(List.of(product.getId())))
                .thenReturn(TestHelper.IS_DELETED_FALSE);

        assertThrows(ValidationException.class,
                () -> orderService.create(expectedOrder),
                HttpStatusHelper.NOT_FOUND_EXCEPTION_ID);
    }

    @Test
    void whenCreateOrderWithOrderProvidedAndObjectsExists_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder = TestHelper.createOrder(
                TestHelper.ID_1,
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

        when(userService.existsById(expectedOrder.getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(restaurantTableService.existsById(expectedOrder.getRestaurantTable().getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(orderStatusService.existsById(expectedOrder.getOrderStatus().getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(productService.existsByIdIn(List.of(product.getId())))
                .thenReturn(TestHelper.IS_DELETED_TRUE);

        when(orderRepository.saveAndFlush(expectedOrder)).thenReturn(expectedOrder);

        Order actualOrder = orderService.create(expectedOrder);

        assertEquals(expectedOrder.getId(), actualOrder.getId());
    }

    @Test
    void whenDeleteOrderWithoutOrderProvided_thenNotFoundException() {

        when(orderRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> orderService.delete(TestHelper.ID_1),
                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order", TestHelper.ID_1));

    }

    @Test
    void whenDeleteOrderWithOrderProvided_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder = TestHelper.createOrder(
                TestHelper.ID_1,
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

        when(orderRepository.findById(expectedOrder.getId())).thenReturn(Optional.of(expectedOrder));

        Order actualOrder = orderService.delete(expectedOrder.getId());

        assertEquals(expectedOrder.getId(), actualOrder.getId());
    }

    @Test
    void whenUpdateOrderWithOrderProvidedAndObjectsExists_thenOk() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder = TestHelper.createOrder(
                TestHelper.ID_1,
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

        when(userService.existsById(expectedOrder.getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(restaurantTableService.existsById(expectedOrder.getRestaurantTable().getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(orderStatusService.existsById(expectedOrder.getOrderStatus().getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(productService.existsByIdIn(List.of(product.getId())))
                .thenReturn(TestHelper.IS_DELETED_TRUE);

        when(orderRepository.findById(expectedOrder.getId())).thenReturn(Optional.of(expectedOrder));
        when(orderRepository.saveAndFlush(expectedOrder)).thenReturn(expectedOrder);

        Order actualOrder = orderService.update(expectedOrder.getId(), expectedOrder);

        assertEquals(expectedOrder.getId(), actualOrder.getId());
    }

    @Test
    void whenUpdateOrderWithOrderProvidedAndObjectsNotExist_thenValidationException() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder = TestHelper.createOrder(
                TestHelper.ID_1,
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

        when(userService.existsById(expectedOrder.getId()))
                .thenReturn(TestHelper.IS_DELETED_FALSE);
        when(restaurantTableService.existsById(expectedOrder.getRestaurantTable().getId()))
                .thenReturn(TestHelper.IS_DELETED_FALSE);
        when(orderStatusService.existsById(expectedOrder.getOrderStatus().getId()))
                .thenReturn(TestHelper.IS_DELETED_FALSE);
        when(productService.existsByIdIn(List.of(product.getId())))
                .thenReturn(TestHelper.IS_DELETED_FALSE);

        assertThrows(ValidationException.class,
                () -> orderService.update(expectedOrder.getId(), expectedOrder),
                HttpStatusHelper.NOT_FOUND_EXCEPTION_ID);
    }

    @Test
    void whenUpdateOrderWithoutOrderProvided_thenBadRequestException() {

        assertThrows(BadRequestException.class,
                () -> orderService.update(TestHelper.ID_1, null),
                String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_UPDATE, "Order", TestHelper.ID_1));
    }

    @Test
    void whenUpdateOrderWithOrderProvidedAndNotExistingOrder_thenNotFoundException() {

        RestaurantTable restaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User user = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product product = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        OrderStatus orderStatus = TestHelper.createOrderStatus(TestHelper.ID_1,
                TestHelper.ORDER_STATUS_ENUM);

        Order expectedOrder = TestHelper.createOrder(
                TestHelper.ID_1,
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

        when(userService.existsById(expectedOrder.getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(restaurantTableService.existsById(expectedOrder.getRestaurantTable().getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(orderStatusService.existsById(expectedOrder.getOrderStatus().getId()))
                .thenReturn(TestHelper.IS_DELETED_TRUE);
        when(productService.existsByIdIn(List.of(product.getId())))
                .thenReturn(TestHelper.IS_DELETED_TRUE);

        when(orderRepository.findById(expectedOrder.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> orderService.update(expectedOrder.getId(), expectedOrder),
                String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_UPDATE, "Order", expectedOrder.getId()));
    }
}

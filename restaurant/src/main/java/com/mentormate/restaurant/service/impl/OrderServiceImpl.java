package com.mentormate.restaurant.service.impl;

import com.mentormate.restaurant.exception.BadRequestException;
import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.exception.ValidationException;
import com.mentormate.restaurant.model.Order;
import com.mentormate.restaurant.model.OrderProduct;
import com.mentormate.restaurant.model.OrderProductId;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import com.mentormate.restaurant.repository.OrderRepository;
import com.mentormate.restaurant.service.OrderService;
import com.mentormate.restaurant.service.OrderStatusService;
import com.mentormate.restaurant.service.RestaurantTableService;
import com.mentormate.restaurant.service.UserService;
import com.mentormate.restaurant.util.HttpStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RestaurantTableService restaurantTableService;

    private final UserService userService;

    private final OrderStatusService orderStatusService;

    private final ProductServiceImpl productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            RestaurantTableService restaurantTableService,
                            UserService userService,
                            OrderStatusService orderStatusService, ProductServiceImpl productService) {
        this.orderRepository = orderRepository;
        this.restaurantTableService = restaurantTableService;
        this.userService = userService;
        this.orderStatusService = orderStatusService;
        this.productService = productService;
    }

    @Override
    public Order findById(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        return optionalOrder.orElseThrow(
                () -> new NotFoundException(String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order", orderId))
        );
    }

    @Override
    public Page<Order> findByTableNumber(Integer tableNumber, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Order> searchedOrders;

        if (tableNumber != null) {
            searchedOrders = orderRepository.findByRestaurantTableTableNumber(tableNumber, pageRequest);

        } else {
            searchedOrders = orderRepository.findAll(pageRequest);
            
        }

        validatePage(page, searchedOrders);

        return searchedOrders;
    }

    @Override
    public Page<Order> findByOrderStatuses(List<OrderEnum> orderEnums, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        Page<Order> searchedOrders;

        searchedOrders = orderRepository.findOrderByOrderStatusOrderEnumIn(orderEnums, pageRequest);

        if (orderEnums == null || searchedOrders.isEmpty()) {
            searchedOrders = orderRepository.findAll(pageRequest);

        }

        validatePage(page, searchedOrders);

        return searchedOrders;
    }

    @Override
    public Page<Order> findByUserId(Integer userId, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);

        Page<Order> searchedOrders;

        if (userId != null) {
            searchedOrders = orderRepository.findByUserId(userId, pageRequest);

        } else {
            searchedOrders = orderRepository.findAll(pageRequest);
        }

        validatePage(page, searchedOrders);

        return searchedOrders;
    }

    @Override
    public Page<Order> findByCreationDate(OffsetDateTime startDate, OffsetDateTime endDate, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Order> searchedOrders;

        if (startDate == null || endDate == null) {
            return orderRepository.findAll(pageRequest);
        }

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException(String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_DATE, "Orders"));
        }

        searchedOrders = orderRepository.findByCreateDateBetween(startDate, endDate, pageRequest);

        validatePage(page, searchedOrders);

        return searchedOrders;
    }

    @Override
    public Order create(Order order) {

        if (order == null) {
            throw new BadRequestException(
                    String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_CREATE, "Order"));
        }

        validateOrderObjects(order);

        List<OrderProduct> orderProducts = new ArrayList<>(order.getOrderProducts());

        order.setOrderProducts(new ArrayList<>());

        order.setCreateDate(OffsetDateTime.now());

        Order savedOrder = orderRepository.saveAndFlush(order);

        orderProducts.forEach(op -> {
            OrderProductId orderProductId = new OrderProductId();

            orderProductId.setOrderId(savedOrder.getId());
            orderProductId.setProductId(op.getProduct().getId());

            op.setId(orderProductId);
            op.setOrder(savedOrder);
        });

        savedOrder.setOrderProducts(orderProducts);

        return orderRepository.saveAndFlush(savedOrder);
    }

    @Override
    public Order update(int orderId, Order order) {

        if (order == null || orderId != order.getId()) {
            throw new BadRequestException(
                    String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_UPDATE, "Order", orderId));
        }

        validateOrderObjects(order);

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new NotFoundException(
                    String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order", orderId));
        }

        Order existingOrder = optionalOrder.get();
        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setUser(order.getUser());
        existingOrder.setRestaurantTable(order.getRestaurantTable());

        List<OrderProduct> orderProducts = new ArrayList<>(order.getOrderProducts());

        orderProducts.forEach(op -> {
            OrderProductId orderProductId = new OrderProductId();

            orderProductId.setOrderId(existingOrder.getId());
            orderProductId.setProductId(op.getProduct().getId());

            op.setId(orderProductId);
            op.setOrder(existingOrder);
        });

        existingOrder.setOrderProducts(order.getOrderProducts());

        existingOrder.setCreateDate(optionalOrder.get().getCreateDate());

        return orderRepository.saveAndFlush(existingOrder);
    }

    @Override
    public Order delete(int orderId) {
        Optional<Order> existingOrder = orderRepository.findById(orderId);

        if (existingOrder.isEmpty()) {
            throw new NotFoundException(
                    String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order", orderId));
        }

        orderRepository.delete(existingOrder.get());

        return existingOrder.get();
    }

    /**
     * This method find and ensures if the user, orderStatus, restaurantTable and orderProducts are existing objects
     * from the given {@link Order} object
     *
     * @param order order object for whom we want to validate inner objects
     * @return true if the objects exists or else false
     */
    private void validateOrderObjects(Order order) {

        List<String> errors = new ArrayList<>();

        if (!orderStatusService.existsById(order.getOrderStatus().getId())) {
            errors.add(String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Order Status", order.getOrderStatus().getId()));
        }

        if (!userService.existsById(order.getUser().getId())) {
            errors.add(String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "User", order.getUser().getId()));
        }

        if (!restaurantTableService.existsById(order.getRestaurantTable().getId())) {
            errors.add(String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Restaurant Table", order.getRestaurantTable().getId()));
        }

        if (!validateOrderProducts(order.getOrderProducts())) {
            errors.add("Product do not exists or quantities are less than 0!");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

    }

    /**
     * This method ensures if the orderProducts object exists
     *
     * @param orderProducts orderProducts that we want to check if they exists
     * @return true if the objects exists or else false
     */
    private boolean validateOrderProducts(List<OrderProduct> orderProducts) {
        boolean productsExist = validateOrderProductsExistence(orderProducts);
        boolean productQuantitiesAreValid = validateOrderProductsQuantity(orderProducts);

        return productsExist && productQuantitiesAreValid;
    }

    /**
     * This method finds and ensures if the given orderProducts are existing objects
     *
     * @param orderProducts orderProducts for whom we want to check existence from all orderProducts
     * @return true if the objects exists or else false
     */
    private boolean validateOrderProductsExistence(List<OrderProduct> orderProducts) {
        List<Integer> productsIds = orderProducts.stream()
                .map(op -> op.getProduct().getId())
                .collect(Collectors.toList());

        return productService.existsByIdIn(productsIds);
    }

    /**
     * This method check the given orderProducts quantity if is more than 0
     *
     * @param orderProducts orderProducts for whom we want to check quantity
     * @return true if the quantity is more than 0 else false
     */
    private boolean validateOrderProductsQuantity(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .allMatch(op -> op.getQuantity() >= 1);
    }

    /**
     * This method check the page if the more than the last page of the given Pagination Orders
     *
     * @param page   page that we want to check
     * @param orders orders that we want to check total pages
     * @throws BadRequestException if the total pages are exceeded.
     */
    private void validatePage(int page, Page<Order> orders) {

        int lastPage = orders.getTotalPages();

        if (page > lastPage || page < 0) {

            throw new BadRequestException(String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_PAGE, lastPage));
        }
    }
}

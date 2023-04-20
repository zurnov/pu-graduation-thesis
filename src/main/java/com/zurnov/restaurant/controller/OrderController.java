package com.zurnov.restaurant.controller;

import com.zurnov.restaurant.dto.order.OrderCreateDTO;
import com.zurnov.restaurant.dto.order.OrderDTO;
import com.zurnov.restaurant.model.Order;
import com.zurnov.restaurant.model.enumeration.OrderEnum;
import com.zurnov.restaurant.service.OrderService;
import com.zurnov.restaurant.util.PathHelper;
import com.zurnov.restaurant.util.converter.dto.OrderDtoConverter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PathHelper.BASE_PATH + PathHelper.ORDER_PATH)
@Validated
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @Operation(
            tags = "Order",
            operationId = "getOrderById",
            summary = "get Order object by id",
            description = "Finds and returns Order or else throws ApiRequestNotFoundException if none of them matches the order id.",
            parameters = @Parameter(
                    name = "orderId",
                    description = "order id with which we want to filter all orders",
                    example = "2"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Order.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    @GetMapping("/{orderId}")
    public OrderDTO findById(@PathVariable int orderId) {
        Order order = orderService.findById(orderId);

        return OrderDtoConverter.toOrderDTO(order);
    }
    
    @Operation(
            tags = "Order",
            operationId = "getOrderByRestaurantTable",
            summary = "get Order object by restaurant table",
            description = "Finds and returns Order that corresponds to the table number" +
                    " or else list of all Orders",
            parameters = {
                    @Parameter(
                            name = "tableNumber",
                            description = "tableNumber with which we want to filter all orders",
                            example = "8"
                    ),
                    @Parameter(
                            name = "page",
                            description = "zero-based page index, must not be negative.",
                            example = "0"
                    ),
                    @Parameter(
                            name = "pageSize",
                            description = "the size of the page to be returned, must be greater than 0",
                            example = "1"
                    )
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Order.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    @GetMapping("/table-number")
    public Page<OrderDTO> findByRestaurantTable(@RequestParam(required = false) Integer tableNumber,
                                                @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                                @RequestParam(required = false, defaultValue = "1") @Min(1) int pageSize) {
        Page<Order> orderPage = orderService.findByTableNumber(tableNumber, page, pageSize);
        
        List<OrderDTO> orders = orderPage.stream()
                .map(OrderDtoConverter::toOrderDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(orders, orderPage.getPageable(), orderPage.getTotalElements());
    }
    
    @Operation(
            tags = "Order",
            operationId = "getOrderByOrderStatus",
            summary = "get Order object by orderStatus",
            description = "Finds and returns Order that corresponds to the order enum" +
                    " or else list of all Orders",
            parameters = {
                    @Parameter(
                            name = "orderStatuses",
                            description = "order enum with which we want to filter all orders"
                    ),
                    @Parameter(
                            name = "page",
                            description = "zero-based page index, must not be negative.",
                            example = "0"
                    ),
                    @Parameter(
                            name = "pageSize",
                            description = "the size of the page to be returned, must be greater than 0",
                            example = "1"
                    )
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Order.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    @GetMapping("/orderStatus")
    public Page<OrderDTO> findByOrderStatus(
            @RequestParam(name = "orderStatuses", required = false) List<OrderEnum> orderEnum,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageSize) {

        Page<Order> orderPage = orderService.findByOrderStatuses(orderEnum, page, pageSize);

        List<OrderDTO> orders = orderPage.stream()
                .map(OrderDtoConverter::toOrderDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(orders, orderPage.getPageable(), orderPage.getTotalElements());
    }

    @Operation(
            tags = "Order",
            operationId = "getOrderByUser",
            summary = "get Order object by userId",
            description = "Finds and returns Order that corresponds to the user id" +
                    " or else list of all Orders",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "userId with which we want to filter all orders",
                            example = "1"
                    ),
                    @Parameter(
                            name = "page",
                            description = "zero-based page index, must not be negative.",
                            example = "0"
                    ),
                    @Parameter(
                            name = "pageSize",
                            description = "the size of the page to be returned, must be greater than 0",
                            example = "1"
                    )
                    
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Order.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    @GetMapping("/user")
    public Page<OrderDTO> findByUser(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageSize) {
        Page<Order> orderPage = orderService.findByUserId(userId, page, pageSize);

        List<OrderDTO> orders = orderPage.stream()
                .map(OrderDtoConverter::toOrderDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(orders, orderPage.getPageable(), orderPage.getTotalElements());
    }
    
    @Operation(
            tags = "Order",
            operationId = "getOrderByBetweenDates",
            summary = "get Order object between dates",
            description = "Finds and returns list of Orders that corresponds to the startDate and endDate" +
                    " or else throws ApiRequestBadRequestException when startDate is after the endDate.",
            parameters = {
                    @Parameter(
                            name = "startDate",
                            description = "start date with which we want to filter all orders.",
                            example = "2022-03-24T20:50:54.114+00:00"
                    ),
                    @Parameter(
                            name = "endDate",
                            description = "end date with which we want to filter all orders",
                            example = "2022-03-28T10:38:27.809-00:00"
                    ),
                    @Parameter(
                            name = "page",
                            description = "zero-based page index, must not be negative.",
                            example = "0"
                    ),
                    @Parameter(
                            name = "pageSize",
                            description = "the size of the page to be returned, must be greater than 0",
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Order.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Success Response"
                    ),
                    @ApiResponse(
                            responseCode = "402",
                            content = @Content(
                                    schema = @Schema(implementation = Order.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Bad Request"
                    )
            }
    )
    @GetMapping("/dates")
    public Page<OrderDTO> findByCreationDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate,
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "1") @Min(1) int pageSize) {

        Page<Order> orderPage = orderService.findByCreationDate(startDate, endDate, page, pageSize);
        
        List<OrderDTO> orders = orderPage.stream()
                .map(OrderDtoConverter::toOrderDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(orders, orderPage.getPageable(), orderPage.getTotalElements());
    }
    
    @Operation(
            tags = "Order",
            operationId = "createOrder",
            summary = "create Order",
            description = "Saves and returns the saved Order or else " +
                    "throws ApiRequestBadRequestException when the given order object is null",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "order object to be saved"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Order.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Order created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    schema = @Schema(implementation = Order.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Order Cannot be created!"
                    )
            }
    )
    @PostMapping()
    public OrderDTO createOrder(@Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        Order order = OrderDtoConverter.toOrderEntity(orderCreateDTO);
        Order createdOrder = orderService.create(order);

        return OrderDtoConverter.toOrderDTO(createdOrder);
    }
    
    @Operation(
            tags = "Order",
            operationId = "updateOrder",
            summary = "update Order",
            description = "Finds and updates the Order by given order id." +
                    " If there is no such element will throw ApiRequestBadRequestException when given order object is null" +
                    " or the given id is different from given order object id or ApiRequestNotFoundException will be thrown" +
                    "when there is no such object with id of the given id or given object order objects are invalid.",
            parameters = @Parameter(
                    name = "orderId",
                    description = "id of the order that we want to update",
                    example = "1"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "order object that will be overriding the existing one"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Order.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Order updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = Order.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Order Cannot be updated!"
                    )
            }
    )
    @PutMapping("/{orderId}")
    public OrderDTO updateOrder(@PathVariable int orderId, @Valid @RequestBody OrderDTO orderDTO) {
        Order order = OrderDtoConverter.toOrderEntity(orderDTO);
        Order updatedOrder = orderService.update(orderId, order);

        return OrderDtoConverter.toOrderDTO(updatedOrder);
    }
    
    @Operation(
            tags = "Order",
            operationId = "deleteOrder",
            summary = "delete Order",
            description = "Finds and soft deletes the Order by given id. If there is no such element" +
                    " ApiRequestNotFoundException will be thrown when there is no such object with the given orderId",
            parameters = @Parameter(
                    name = "orderId",
                    description = "id of the order that we want to soft delete",
                    example = "2"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = Order.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Order deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = Order.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Order Cannot be found!"
                    )
            }
    )
    @DeleteMapping("/{orderId}")
    public OrderDTO deleteOrder(@PathVariable int orderId) {
        Order deletedOrder = orderService.delete(orderId);

        return OrderDtoConverter.toOrderDTO(deletedOrder);
    }
}

package com.mentormate.restaurant.controller;

import com.mentormate.restaurant.dto.order.OrderStatusDTO;
import com.mentormate.restaurant.model.OrderStatus;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import com.mentormate.restaurant.service.OrderStatusService;
import com.mentormate.restaurant.util.PathHelper;
import com.mentormate.restaurant.util.converter.dto.OrderStatusDtoConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PathHelper.BASE_PATH + PathHelper.ORDER_STATUS_PATH)
public class OrderStatusController {

    OrderStatusService orderStatusService;

    @Autowired
    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("/{orderStatusId}")
    @Operation(
            tags = "Order Status",
            operationId = "getOrderStatusById",
            summary = "get OrderStatus object by id",
            description = "Finds and returns OrderStatus that corresponds to the given id " +
                    "or else null if none of them matches the order status id.",
            parameters = @Parameter(
                    name = "orderStatusId",
                    description = "order id with which we want to filter all order statuses",
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = OrderStatus.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Order status found"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = OrderStatus.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "OrderStatus is not found!"
                    )
            }
    )
    public OrderStatusDTO getOrderStatuses(@PathVariable int orderStatusId) {
        OrderStatus orderStatus = orderStatusService.findById(orderStatusId);

        return OrderStatusDtoConverter.convertEntityToDto(orderStatus);
    }

    @GetMapping()
    @Operation(
            tags = "Order Status",
            operationId = "getOrderStatusByOrderStatusName",
            summary = "get OrderStatus object by orderStatusName",
            description = "Finds and returns OrderStatus that corresponds to the orderEnum" +
                    " or else list of all OrderStatuses",
            parameters = @Parameter(
                    name = "orderStatusName",
                    description = "order enum with which we want to filter all order statuses",
                    example = "ACTIVE"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = OrderStatus.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE),
                    description = "Success Response"
            )
    )
    public List<OrderStatusDTO> getOrderStatuses(@RequestParam(name = "orderStatusName", required = false) OrderEnum orderEnum) {
        List<OrderStatus> orderStatuses = orderStatusService.findOrderStatuses(orderEnum);

        return orderStatuses.stream()
                .map(OrderStatusDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }
}

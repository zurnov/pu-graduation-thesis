package com.zurnov.restaurant.controller;

import com.zurnov.restaurant.dto.restaurantTable.RestaurantTableDTO;
import com.zurnov.restaurant.model.RestaurantTable;
import com.zurnov.restaurant.service.RestaurantTableService;
import com.zurnov.restaurant.util.PathHelper;
import com.zurnov.restaurant.util.converter.dto.RestaurantTableDtoConverter;
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
@RequestMapping(PathHelper.BASE_PATH + PathHelper.RESTAURANT_TABLE_PATH)
public class RestaurantTableController {

    private final RestaurantTableService restaurantTableService;

    @Autowired
    public RestaurantTableController(RestaurantTableService restaurantTableService) {
        this.restaurantTableService = restaurantTableService;
    }

    @GetMapping("/{restaurantTableId}")
    @Operation(
            tags = "Restaurant Table",
            operationId = "getRestaurantTableById",
            summary = "get RestaurantTable object by id",
            description = "Finds and returns RestaurantTable that corresponds to the given id" +
                    " or else throws ApiRequestNotFoundException if none of them matches the restaurant table id.",
            parameters = @Parameter(
                    name = "restaurantTableId",
                    description = "id with which we want to filter all restaurant tables",
                    example = "4"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantTable.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Restaurant Table found"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = RestaurantTable.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE),
                            description = "Restaurant Table not found!"
                    )
            }

    )
    public RestaurantTableDTO getRestaurantTableById(@PathVariable int restaurantTableId) {
        RestaurantTable restaurantTable = restaurantTableService.findById(restaurantTableId);

        return RestaurantTableDtoConverter.convertEntityToDto(restaurantTable);
    }

    @GetMapping()
    @Operation(
            tags = "Restaurant Table",
            operationId = "getRestaurantTableByTableNumber",
            summary = "get RestaurantTable object by table number",
            description = "Finds and returns RestaurantTable that corresponds to the tableNumber" +
                    " or else list of all RestaurantTables",
            parameters = @Parameter(
                    name = "tableNumber",
                    description = "table number with which we want to filter all restaurant tables",
                    example = "5"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = RestaurantTable.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE),
                    description = "Success Response"
            )
    )
    public List<RestaurantTableDTO> getRestaurantTables(@RequestParam(name = "tableNumber", required = false) Integer restaurantTableNumber) {
        List<RestaurantTable> restaurantTables = restaurantTableService.findTables(restaurantTableNumber);

        return restaurantTables.stream()
                .map(RestaurantTableDtoConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }
}

package com.mentormate.restaurant.service;

import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.RestaurantTable;
import com.mentormate.restaurant.repository.RestaurantTableRepository;
import com.mentormate.restaurant.service.impl.RestaurantTableServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantTableServiceTests {

    @Mock
    RestaurantTableRepository restaurantTableRepository;

    @InjectMocks
    private RestaurantTableServiceImpl restaurantTableServiceImpl;

    @Test
    void whenFindRestaurantTableById_thenOk() {
        RestaurantTable expectedRestaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        when(restaurantTableRepository.findById(expectedRestaurantTable.getId()))
                .thenReturn(Optional.of(expectedRestaurantTable));

        RestaurantTable actualRestaurantTable = restaurantTableServiceImpl.findById(expectedRestaurantTable.getId());

        assertEquals(expectedRestaurantTable.getId(), actualRestaurantTable.getId());
    }

    @Test
    void whenFindRestaurantTableById_thenNotFoundException() {
        when(restaurantTableRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> restaurantTableServiceImpl.findById(TestHelper.ID_1),
                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Restaurant table", TestHelper.ID_1));
    }

    @Test
    void whenFindRestaurantTableByTableNumberWithTableNumberProvided_thenOk() {

        RestaurantTable expectedRestaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        List<RestaurantTable> expectedRestaurantTables = List.of(expectedRestaurantTable);

        when(restaurantTableRepository.findRestaurantTableByTableNumber(expectedRestaurantTable.getTableNumber()))
                .thenReturn(Optional.of(expectedRestaurantTable));

        List<RestaurantTable> actualRestaurantTables = restaurantTableServiceImpl.findTables(expectedRestaurantTable.getTableNumber());

        assertEquals(expectedRestaurantTables.size(), actualRestaurantTables.size());
        assertEquals(expectedRestaurantTables.get(0).getTableNumber(), actualRestaurantTables.get(0).getTableNumber());
    }

    @Test
    void whenFindRestaurantTableByTableNumberWithoutTableNumberProvided_thenOk() {

        RestaurantTable expectedRestaurantTable = TestHelper.createRestaurantTable(
                TestHelper.ID_1,
                TestHelper.RESTAURANT_TABLE_NUMBER);

        List<RestaurantTable> expectedRestaurantTables = List.of(expectedRestaurantTable);

        when(restaurantTableRepository.findAll()).thenReturn(List.of(expectedRestaurantTable));

        List<RestaurantTable> actualRestaurantTables = restaurantTableServiceImpl.findTables(null);

        assertEquals(expectedRestaurantTables.size(), actualRestaurantTables.size());
        assertEquals(expectedRestaurantTables.get(0).getTableNumber(), actualRestaurantTables.get(0).getTableNumber());
    }
}

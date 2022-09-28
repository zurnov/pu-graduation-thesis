package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.config.PostgreSQLExtension;
import com.mentormate.restaurant.config.RestaurantPostgreSQLContainer;
import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.RestaurantTable;
import com.mentormate.restaurant.util.HttpStatusHelper;
import com.mentormate.restaurant.util.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(value = PostgreSQLExtension.class)
class RestaurantTableRepositoryTest extends RestaurantPostgreSQLContainer {

    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    RestaurantTableRepositoryTest(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    @Test
    void givenRestaurantTable_whenFindRestaurantTableByTableNumber_thenReturnRestaurantTable() {

        RestaurantTable expectedRestaurantTable =
                restaurantTableRepository.findRestaurantTableByTableNumber(TestHelper.RESTAURANT_TABLE_NUMBER)
                        .orElseThrow(() -> new NotFoundException(String.format(
                                HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", TestHelper.RESTAURANT_TABLE_NUMBER)));

        Optional<RestaurantTable> actualRestaurantTable = restaurantTableRepository.findRestaurantTableByTableNumber(
                expectedRestaurantTable.getTableNumber());

        Assertions.assertFalse(actualRestaurantTable.isEmpty());
        Assertions.assertEquals(expectedRestaurantTable.getTableNumber(), actualRestaurantTable.get().getTableNumber());
    }

    @Test
    void givenRestaurantTable_existsById_thenReturnTrue() {

        boolean actualRestaurantTable = restaurantTableRepository.existsById(TestHelper.ID_1);

        Assertions.assertTrue(actualRestaurantTable);
    }
}

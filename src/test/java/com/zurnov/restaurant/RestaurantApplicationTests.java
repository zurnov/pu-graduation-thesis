package com.zurnov.restaurant;

import com.zurnov.restaurant.config.PostgreSQLExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(value = PostgreSQLExtension.class)
class RestaurantApplicationTests {

    @Test
    void contextLoads() {
    }

}

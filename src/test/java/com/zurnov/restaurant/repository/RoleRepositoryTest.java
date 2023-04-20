package com.zurnov.restaurant.repository;

import com.zurnov.restaurant.config.PostgreSQLExtension;
import com.zurnov.restaurant.config.RestaurantPostgreSQLContainer;
import com.zurnov.restaurant.exception.NotFoundException;
import com.zurnov.restaurant.model.Role;
import com.zurnov.restaurant.model.enumeration.RoleEnum;
import com.zurnov.restaurant.util.HttpStatusHelper;
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
class RoleRepositoryTest extends RestaurantPostgreSQLContainer {

    private final RoleRepository roleRepository;

    @Autowired
    RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    void givenRoles_whenGetByRoleEnum_thenReturnRole() {

        Role expectedRole = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));
        
        Optional<Role> actualRole = roleRepository.findByRoleEnum(expectedRole.getRoleEnum());

        Assertions.assertFalse(actualRole.isEmpty());
        Assertions.assertEquals(expectedRole.getRoleEnum(), actualRole.get().getRoleEnum());
    }
}

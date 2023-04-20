package com.zurnov.restaurant.repository;

import com.zurnov.restaurant.config.PostgreSQLExtension;
import com.zurnov.restaurant.config.RestaurantPostgreSQLContainer;
import com.zurnov.restaurant.exception.NotFoundException;
import com.zurnov.restaurant.model.Role;
import com.zurnov.restaurant.model.User;
import com.zurnov.restaurant.model.enumeration.RoleEnum;
import com.zurnov.restaurant.util.HttpStatusHelper;
import com.zurnov.restaurant.util.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(value = PostgreSQLExtension.class)
class UserRepositoryTest extends RestaurantPostgreSQLContainer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Test
    void givenUser_whenFindById_thenReturnUser() {

        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));
        
        User expectedUser = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(expectedUser);

        Optional<User> actualUser = userRepository.findById(expectedUser.getId());

        Assertions.assertFalse(actualUser.isEmpty());
        Assertions.assertEquals(expectedUser.getName(), actualUser.get().getName());
    }

    @Test
    void givenUser_whenFindByEmail_thenReturnUser() {

        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));
        
        User expectedUser = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(expectedUser);
        
        Optional<User> actualUser = userRepository.findByEmail(expectedUser.getEmail());

        Assertions.assertFalse(actualUser.isEmpty());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.get().getEmail());
    }

    @Test
    void givenUser_whenFindByUserRolesRoleEnumIn_thenReturnUser() {

        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));

        User expectedUser = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(expectedUser);

        List<User> actualUsers = userRepository.findByUserRolesRoleEnumIn(List.of(role.getRoleEnum()));

        Assertions.assertFalse(actualUsers.isEmpty());
        Assertions.assertEquals(expectedUser.getUserRoles(), actualUsers.get(0).getUserRoles());
    }

    @Test
    void findUserByName() {

        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));

        User expectedUser = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(expectedUser);

        List<User> actualUsers = userRepository.findUserByName(TestHelper.USER_NAME);

        Assertions.assertFalse(actualUsers.isEmpty());
        Assertions.assertEquals(expectedUser.getName(), actualUsers.get(0).getName());
    }

    @Test
    void existsById() {

        Role role = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseThrow(() -> new NotFoundException(String.format(
                        HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", 1)));

        User expectedUser = TestHelper.createUser(
                null,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        userRepository.saveAndFlush(expectedUser);

        boolean actualUser = userRepository.existsById(expectedUser.getId());

        Assertions.assertTrue(actualUser);
    }
}

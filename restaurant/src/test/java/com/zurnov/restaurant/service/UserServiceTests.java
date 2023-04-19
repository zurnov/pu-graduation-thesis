package com.zurnov.restaurant.service;

import com.zurnov.restaurant.exception.NotFoundException;
import com.zurnov.restaurant.model.Role;
import com.zurnov.restaurant.model.User;
import com.zurnov.restaurant.repository.UserRepository;
import com.zurnov.restaurant.service.impl.RoleServiceImpl;
import com.zurnov.restaurant.service.impl.UserServiceImpl;
import com.zurnov.restaurant.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    UserRepository userRepository;
    
    @Mock
    RoleServiceImpl roleService;
    
    @InjectMocks
    UserServiceImpl userService;
    
    @Test
    void whenGetUserByIdWithExistingUser_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        when(userRepository.findById(TestHelper.ID_1)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.findById(TestHelper.ID_1);

        assertEquals(expectedUser, actualUser);
    }
    
    @Test
    void whenGetUserByIdWithNotExistingUser_thenNull() {
        
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findById(TestHelper.ID_1)).thenReturn(optionalUser);
        
        assertThrows(NotFoundException.class, () -> userService.findById(TestHelper.ID_1));
        
    }

    @Test
    void whenGetUserByEmailWithEmailProvided_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        List<User> expectedUsers = List.of(expectedUser);

        when(userRepository.findByEmail(expectedUser.getEmail())).thenReturn(Optional.of(expectedUser));

        List<User> actualUsers = userService.findByEmail(expectedUser.getEmail());

        assertEquals(expectedUsers.get(0).getEmail(), actualUsers.get(0).getEmail());
    }

    @Test
    void whenGetUserByEmailWithoutEmailProvided_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        List<User> expectedUsers = List.of(expectedUser);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findByEmail(null);

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers.get(0).getEmail(), actualUsers.get(0).getEmail());
    }

    @Test
    void whenGetUserByRoleWithRoleProvided_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);


        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        when(userRepository.findByUserRolesRoleEnumIn(List.of(TestHelper.ROLE_ENUM_ADMIN))).thenReturn(List.of(expectedUser));


        List<User> actualUser = userService.findByRoles(List.of(TestHelper.ROLE_ENUM_ADMIN));

        assertEquals(expectedUser.getUserRoles(), actualUser.get(0).getUserRoles());
    }

    @Test
    void whenGetUserByRoleWithoutRoleProvided_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);
        Role role1 = TestHelper.createRole(TestHelper.ID_2, TestHelper.ROLE_ENUM_USER);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        User expectedUser1 = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role1);

        List<User> expectedUsers = List.of(expectedUser, expectedUser1);

        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.findByRoles(null);

        assertEquals(expectedUsers.size(), actualUsers.size());
    }

    @Test
    void whenDeleteUserWithExistingUser_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        when(userRepository.findById(TestHelper.ID_1)).thenReturn(Optional.of(expectedUser));
        when(userRepository.saveAndFlush(expectedUser)).thenReturn(expectedUser);

        User actualUser = userService.delete(expectedUser.getId());

        assertEquals(expectedUser.getIsDeleted(), actualUser.getIsDeleted());
    }

    @Test
    void whenDeleteUserWithoutExistingUser_thenNull() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        when(userRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        User actualUser = userService.delete(expectedUser.getId());

        assertNull(actualUser);
    }

    @Test
    void whenUpdateUserWithExistingUser_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        when(userRepository.findById(TestHelper.ID_2)).thenReturn(Optional.of(expectedUser));
        when(userRepository.saveAndFlush(expectedUser)).thenReturn(expectedUser);

        expectedUser.setIsDeleted(false);
        expectedUser.setId(TestHelper.ID_2);

        User actualUser = userService.update(expectedUser.getId(), expectedUser);

        expectedUser.setId(TestHelper.ID_1);
        expectedUser.setIsDeleted(true);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void whenUpdateUserWithoutUserProvided_thenNull() {

        User actualUser = userService.update(TestHelper.ID_1, null);

        assertNull(actualUser);
    }

    @Test
    void whenUpdateUserWithUserProvidedForNotExistingUser_thenNull() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        when(userRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        User actualUser = userService.update(expectedUser.getId(), expectedUser);

        assertNull(actualUser);
    }

    @Test
    void whenCreateUserWithUserProvided_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        when(roleService.findAll()).thenReturn(List.of(role));
        when(userRepository.saveAndFlush(expectedUser)).thenReturn(expectedUser);

        User actualUser = userService.create(expectedUser);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void whenCreateUserWithUserProvidedAndWithoutRolesExisting_thenNull() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        when(roleService.findAll()).thenReturn(new ArrayList<>());

        User actualUser = userService.create(expectedUser);

        assertNull(actualUser);
    }

    @Test
    void whenCreateUserWithoutUserProvided_thenNull() {

        User actualUser = userService.create(null);

        assertNull(actualUser);
    }

    @Test
    void whenGetUserByNameWithNameProvided_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        List<User> expectedUsers = List.of(expectedUser);

        when(userRepository.findUserByName(expectedUser.getName())).thenReturn(List.of(expectedUser));

        List<User> actualUsers = userService.findByName(expectedUser.getName());

        assertEquals(expectedUsers.get(0).getName(), actualUsers.get(0).getName());
    }

    @Test
    void whenGetUserByNameWithoutNameProvided_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        User expectedUser = TestHelper.createUser(
                TestHelper.ID_1,
                TestHelper.USER_NAME,
                TestHelper.USER_PASSWORD,
                TestHelper.USER_EMAIL,
                TestHelper.IS_DELETED_FALSE,
                role);

        List<User> expectedUsers = List.of(expectedUser);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findByName(null);

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertEquals(expectedUsers.get(0).getName(), actualUsers.get(0).getName());
    }
}

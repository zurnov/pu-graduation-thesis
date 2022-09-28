package com.mentormate.restaurant.service;

import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.Role;
import com.mentormate.restaurant.model.enumeration.RoleEnum;
import com.mentormate.restaurant.repository.RoleRepository;
import com.mentormate.restaurant.service.impl.RoleServiceImpl;
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
class RoleServiceTests {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl roleServiceImpl;

    @Test
    void whenFindRoleById_thenOk() {
        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        Role actualRole = roleServiceImpl.findById(role.getId());

        assertEquals(role.getId(), actualRole.getId());
    }

    @Test
    void whenGetRoleById_thenNotFoundException() {

        when(roleRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> roleServiceImpl.findById(TestHelper.ID_1),
                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", TestHelper.ID_1));
    }

    @Test
    void whenGetRoleByEnumWithEnumProvided_thenOk() {
        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        List<Role> expectedRoles = List.of(role);

        when(roleRepository.findByRoleEnum(role.getRoleEnum())).thenReturn(Optional.of(role));

        List<Role> actualRoles = roleServiceImpl.findRoles(RoleEnum.ADMIN);

        assertEquals(expectedRoles.get(0).getRoleEnum(), actualRoles.get(0).getRoleEnum());
        assertEquals(expectedRoles.size(), actualRoles.size());
    }

    @Test
    void whenGetRoleByEnumWithoutEnumProvided_thenOk() {

        Role role = TestHelper.createRole(TestHelper.ID_1, TestHelper.ROLE_ENUM_ADMIN);

        List<Role> expectedRoles = List.of(role);

        when(roleRepository.findAll()).thenReturn(expectedRoles);

        List<Role> actualRoles = roleServiceImpl.findRoles(null);

        assertEquals(expectedRoles.get(0).getRoleEnum(), actualRoles.get(0).getRoleEnum());
        assertEquals(expectedRoles.size(), actualRoles.size());
    }
}

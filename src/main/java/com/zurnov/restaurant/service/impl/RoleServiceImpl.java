package com.zurnov.restaurant.service.impl;


import com.zurnov.restaurant.exception.NotFoundException;
import com.zurnov.restaurant.model.Role;
import com.zurnov.restaurant.model.enumeration.RoleEnum;
import com.zurnov.restaurant.repository.RoleRepository;
import com.zurnov.restaurant.service.RoleService;
import com.zurnov.restaurant.util.HttpStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Integer id) {
        Optional<Role> optionalRole = roleRepository.findById(id);

        return optionalRole.orElseThrow(
                ()-> new NotFoundException(String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Role", id)));
    }

    @Override
    public List<Role> findRoles(RoleEnum roleEnum) {

        List<Role> roles = new ArrayList<>();

        if (roleEnum != null) {
            Optional<Role> optionalRole = roleRepository.findByRoleEnum(roleEnum);

            optionalRole.ifPresent(roles::add);
        } else {
            roles.addAll(roleRepository.findAll());
        }

        return roles;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}

package com.mentormate.restaurant.service;

import com.mentormate.restaurant.model.Role;
import com.mentormate.restaurant.model.enumeration.RoleEnum;

import java.util.List;

public interface RoleService {

    /**
     * Finds and returns Role that corresponds to the id of the Role or else returns null.
     *
     * @param id id with which we want to filter all roles
     * @return The found {@link Role} object
     */
    Role findById(Integer id);

    /**
     * This method finds and returns Role that corresponds to the roleEnum or else list of all Roles
     * if none of them matches the roleEnum.
     *
     * @param roleEnum role enum with which we want to filter all Roles
     * @return List of {@link Role} objects
     */
    List<Role> findRoles(RoleEnum roleEnum);

    /**
     * This method finds and returns all Roles in a list
     *
     * @return List of {@link Role} objects
     */
    List<Role> findAll();
}

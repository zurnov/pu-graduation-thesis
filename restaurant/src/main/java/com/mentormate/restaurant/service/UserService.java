package com.mentormate.restaurant.service;

import com.mentormate.restaurant.model.User;
import com.mentormate.restaurant.model.enumeration.RoleEnum;

import java.util.List;


public interface UserService {

    /**
     * This method finds and returns User or else null if none of them matches the user id.
     *
     * @param id user id with which we want to filter all users
     * @return The found {@link User} object
     */
    User findById(int id);

    /**
     * This method finds and returns User that corresponds to the email or else list of all Users
     *
     * @param email email with which we want to filter all users
     * @return List of {@link User} objects
     */
    List<User> findByEmail(String email);

    /**
     * This method finds and returns User that corresponds to the name or else list of all Users
     *
     * @param name name with which we want to filter all users
     * @return List of {@link User} objects
     */
    List<User> findByName(String name);

    /**
     * This method finds and returns User that corresponds to the userRole or else list of all Users
     *
     * @param userRole userRole with which we want to filter all users
     * @return List of {@link User} objects
     */
    List<User> findByRoles(List<RoleEnum> userRole);

    /**
     * Saves and returns the saved User or else returns null if the User is null.
     *
     * @param user user to be saved
     * @return The saved {@link User} object
     */
    User create(User user);

    /**
     * Finds and updates the User by given user id. If there is no such element null will be returned
     *
     * @param userId id of the user that we want to update
     * @param user   user object that will be overriding the existing one
     * @return The updated {@link User} object
     */
    User update(int userId, User user);

    /**
     * Finds and soft deletes the User by given id. If there is no such element null will be returned.
     *
     * @param userId id of the user that we want to soft delete
     * @return The deleted {@link User} object
     */
    User delete(int userId);

    /**
     * Checks if user with the id provided exist.
     * @param userId userId for which we want to filter users
     * @return true if user exists, otherwise false
     */
    boolean existsById(int userId);
}

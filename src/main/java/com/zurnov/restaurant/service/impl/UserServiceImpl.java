package com.zurnov.restaurant.service.impl;

import com.zurnov.restaurant.exception.NotFoundException;
import com.zurnov.restaurant.model.Role;
import com.zurnov.restaurant.model.User;
import com.zurnov.restaurant.model.enumeration.RoleEnum;
import com.zurnov.restaurant.repository.UserRepository;
import com.zurnov.restaurant.service.RoleService;
import com.zurnov.restaurant.service.UserService;
import com.zurnov.restaurant.util.HttpStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User findById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);

        return optionalUser.orElseThrow(
                ()-> new NotFoundException(String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "User", id)));
    }

    @Override
    public List<User> findByEmail(String email) {
        List<User> userList = new ArrayList<>();

        if (email != null) {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            optionalUser.ifPresent(userList::add);
        } else {
            userList.addAll(userRepository.findAll());
        }
        return userList;
    }

    @Override
    public List<User> findByName(String name) {
        List<User> userList = new ArrayList<>();

        if (name != null) {
            userList.addAll(userRepository.findUserByName(name));
            
        } else {
            userList.addAll(userRepository.findAll());
        }
        return userList;
    }

    @Override
    public List<User> findByRoles(List<RoleEnum> userRoles) {
        List<User> userList = new ArrayList<>();
        
        if (userRoles != null) {
            
            userList.addAll(userRepository.findByUserRolesRoleEnumIn(userRoles));
        } else {
            userList.addAll(userRepository.findAll());
        }
        
        return userList;
    }

    @Override
    public User create(User user) {

        if (user == null) {
            return null;
        }

        if (!validateRoleExistence(user)) {
            return null;
        }
        
        return userRepository.saveAndFlush(user);
    }

    private boolean validateRoleExistence(User user) {
        
        List<Role> existingRoles = roleService.findAll();
        
        return existingRoles.containsAll(user.getUserRoles());
    }

    @Override
    public User update(int userId, User user) {

        if (user == null || userId != user.getId()) {
            return null;
        }

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }
        
        User existingUser = optionalUser.get();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setUserRoles(user.getUserRoles());
        existingUser.setPassword(user.getPassword());
        existingUser.setIsDeleted(user.getIsDeleted());

        return userRepository.saveAndFlush(existingUser);
    }

    @Override
    public User delete(int userId) {
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isEmpty()) {
            return null;
        }

        existingUser.get().setIsDeleted(true);

        return userRepository.saveAndFlush(existingUser.get());
    }

    @Override
    public boolean existsById(int userId) {
        return userRepository.existsById(userId);
    }
}


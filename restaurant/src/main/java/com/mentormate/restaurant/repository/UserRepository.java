package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.model.User;
import com.mentormate.restaurant.model.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    List<User> findByUserRolesRoleEnumIn(List<RoleEnum> userRoles);
    
    List<User> findUserByName(String name);
    
    boolean existsById(int userId);
}

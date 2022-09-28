package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.model.Role;
import com.mentormate.restaurant.model.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleEnum(RoleEnum roleEnum);
    
    List<Role> findAll();
    
}

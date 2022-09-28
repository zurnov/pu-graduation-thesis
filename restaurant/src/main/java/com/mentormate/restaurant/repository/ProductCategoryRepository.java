package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {


    Optional<ProductCategory> findProductCategoryByName(String name);
    
    boolean existsById(int productCategoryId);
}

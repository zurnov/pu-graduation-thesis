package com.zurnov.restaurant.repository;

import com.zurnov.restaurant.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByIsDeleted(boolean isDeleted);

    Optional<Product> findByIdAndIsDeleted(int id, boolean isDeleted);
    
    boolean existsById(int productId);

    boolean existsByIdIn (List<Integer> productIds);
}

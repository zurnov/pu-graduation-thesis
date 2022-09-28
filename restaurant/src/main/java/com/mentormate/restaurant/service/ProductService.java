package com.mentormate.restaurant.service;

import com.mentormate.restaurant.model.Product;

import java.util.List;


public interface ProductService {

    /**
     * Finds and updates the Product by given product id. If there is no such element null will be returned
     *
     * @param productId id of the product that we want to update
     * @param product   product object that will be overriding the existing one
     * @return The updated {@link Product} object
     */
    Product update(int productId, Product product);
    
    /**
     * Finds and returns Product by some criteria or else return null if the criteria are not matched.
     *
     * @param isProductDeleted softDelete flag with which we want to filter all the Products
     * @param id               id with which we want to filter all the Products
     * @return The found {@link Product} object
     */
    Product findByIdAndIsDeleted(int id, boolean isProductDeleted);

    /**
     * Finds and returns Product by some criteria or else return null if the criteria are not matched
     *
     * @param isProductDeleted softDelete flag with which we want to filter the Products
     * @return List of {@link Product} objects
     */
    List<Product> findProducts(boolean isProductDeleted);

    /**
     * Saves and returns the saved Product or else returns null if the Product is null.
     *
     * @param product product to be saved
     * @return The saved {@link Product} object
     */
    Product save(Product product);

    /**
     * Finds and soft deletes the Product by some criteria or else return null if the criteria are not matched.
     *
     * @param productId id of the product that we want to soft delete
     * @return The deleted {@link Product} object
     */
    Product delete(int productId);

    /**
     * Checks if product with the id provided exist.
     * @param productId productId for which we want to filter products
     * @return true if product exists, otherwise false
     */
    boolean existsById(int productId);

    /**
     * Checks if products with the ids provided exists.
     * @param productIds productIds for which we want to filter products
     * @return true if products exists, otherwise false
     */
    boolean existsByIdIn(List<Integer> productIds);
}

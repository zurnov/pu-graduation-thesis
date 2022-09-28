package com.mentormate.restaurant.service;

import com.mentormate.restaurant.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /**
     * Finds and returns productCategory that corresponds to the id of the ProductCategory or else returns null
     *
     * @param id id for which we want to filter product categories
     * @return The found {@link ProductCategory} object
     */
    ProductCategory findById(Integer id);

    /**
     * Finds and returns productCategory that corresponds to the categoryName or else returns all product category names
     *
     * @param categoryName categoryName with which we want to filter product all categories
     * @return List of {@link ProductCategory} objects
     */
    List<ProductCategory> findProductCategories(String categoryName);

    /**
     * Saves and returns the saved ProductCategory or else returns null if the ProductCategory is null.
     *
     * @param productCategory productCategory to be saved
     * @return The saved {@link ProductCategory} object
     */
    ProductCategory create(ProductCategory productCategory);

    /**
     * Finds and updates the User by given product category id. If there is no such element null will be returned
     *
     * @param productCategoryId id of the product category that we want to update
     * @param productCategory   product category object that will be overriding the existing one
     * @return The updated {@link ProductCategory} object
     */
    ProductCategory update(int productCategoryId, ProductCategory productCategory);

    /**
     * Checks if product category with the id provided exist.
     * @param productCategoryId productCategoryId for which we want to filter product categories
     * @return true if product category exists, otherwise false
     */
    boolean existsById(int productCategoryId);
}

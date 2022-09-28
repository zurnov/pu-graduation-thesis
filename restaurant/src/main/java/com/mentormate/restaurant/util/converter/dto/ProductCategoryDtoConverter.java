package com.mentormate.restaurant.util.converter.dto;

import com.mentormate.restaurant.dto.product.ProductCategoryCreateDTO;
import com.mentormate.restaurant.dto.product.ProductCategoryDTO;
import com.mentormate.restaurant.model.ProductCategory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductCategoryDtoConverter {

    /**
     * Converts the productCategory Entity to DTO Object
     *
     * @param productCategory Entity to be converted
     * @return productCategoryDTO the converted DTO
     */
    public static ProductCategoryDTO toProductCategoryDTO(ProductCategory productCategory) {
        
        if (productCategory == null) {
            return null;
        }
        
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        
        productCategoryDTO.setId(productCategory.getId());
        productCategoryDTO.setName(productCategory.getName());
        
        return productCategoryDTO;
    }

    /**
     * Converts the productCategoryDTO DTO Object to Entity
     *
     * @param productCategoryDTO to be converted
     * @return productCategory the converted Entity
     */
    public static ProductCategory toProductCategoryEntity(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = new ProductCategory();
        
        productCategory.setId(productCategoryDTO.getId());
        productCategory.setName(productCategoryDTO.getName());
        
        return productCategory;
    }

    /**
     * Converts the productCategory DTO Object to Entity
     *
     * @param productCategoryCreateDTO to be converted
     * @return productCategory the converted Entity
     */
    public static ProductCategory toProductCategoryEntity(ProductCategoryCreateDTO productCategoryCreateDTO) {
        ProductCategory productCategory = new ProductCategory();
        
        productCategory.setName(productCategoryCreateDTO.getName());
        
        return productCategory;
    }
}

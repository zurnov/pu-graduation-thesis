package com.zurnov.restaurant.util.converter.dto;

import com.zurnov.restaurant.dto.product.ProductCategoryDTO;
import com.zurnov.restaurant.dto.product.ProductCreateDTO;
import com.zurnov.restaurant.dto.product.ProductDTO;
import com.zurnov.restaurant.model.Product;
import com.zurnov.restaurant.model.ProductCategory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductDtoConverter {

    /**
     * Converts the product Entity to DTO Object
     *
     * @param product Entity to be converted
     * @return productDTO the converted DTO
     */
    public static ProductDTO toProductDTO(Product product) {

        if (product == null) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setIsDeleted(product.getIsDeleted());
        ProductCategoryDTO productCategoryDTO = ProductCategoryDtoConverter.toProductCategoryDTO(product.getProductCategory());
        productDTO.setProductCategory(productCategoryDTO);

        return productDTO;
    }

    /**
     * Converts the productDTO DTO Object to Entity
     *
     * @param productDTO to be converted
     * @return product the converted Entity
     */
    public static Product toProductEntity(ProductDTO productDTO) {
        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setIsDeleted(productDTO.getIsDeleted());

        ProductCategory productCategory = ProductCategoryDtoConverter.toProductCategoryEntity(productDTO.getProductCategory());
        product.setProductCategory(productCategory);

        return product;
    }

    /**
     * Converts the productCreateDTO DTO Object to Entity
     *
     * @param productCreateDTO to be converted
     * @return product the converted Entity
     */
    public static Product toProductEntity(ProductCreateDTO productCreateDTO) {
        Product product = new Product();

        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());

        ProductCategory productCategory = ProductCategoryDtoConverter.toProductCategoryEntity(productCreateDTO.getProductCategory());
        product.setProductCategory(productCategory);

        product.setIsDeleted(false);

        return product;
    }
}

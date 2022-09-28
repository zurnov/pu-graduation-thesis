package com.mentormate.restaurant.service.impl;

import com.mentormate.restaurant.exception.BadRequestException;
import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.ProductCategory;
import com.mentormate.restaurant.repository.ProductCategoryRepository;
import com.mentormate.restaurant.service.ProductCategoryService;
import com.mentormate.restaurant.util.HttpStatusHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory findById(Integer id) {
        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(id);
        
        return optionalProductCategory.orElseThrow(
                () -> new NotFoundException(
                        String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product category", id))
        );
    }
    
    @Override
    public List<ProductCategory> findProductCategories(String categoryName) {
        List<ProductCategory> productCategories = new ArrayList<>();
        
        if (categoryName != null) {
            Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findProductCategoryByName(categoryName);
            
            optionalProductCategory.ifPresent(productCategories::add);
        } else {
            productCategories.addAll(productCategoryRepository.findAll());
        }
        
        return productCategories;
    }

    @Override
    public ProductCategory create(ProductCategory productCategory) {

        if (productCategory == null) {
            throw new BadRequestException(
                    String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_CREATE, "Product category")
            );
        }

        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory update(int productCategoryId, ProductCategory productCategory) {

        if (productCategory == null || productCategoryId != productCategory.getId()) {
            return null;
        }

        Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(productCategoryId);

        if (optionalProductCategory.isEmpty()) {
            return null;
        }

        ProductCategory existingProductCategory = optionalProductCategory.get();
        existingProductCategory.setName(productCategory.getName());
        
        return productCategoryRepository.saveAndFlush(existingProductCategory);
    }

    @Override
    public boolean existsById(int productCategoryId) {
        return productCategoryRepository.existsById(productCategoryId);
    }
}

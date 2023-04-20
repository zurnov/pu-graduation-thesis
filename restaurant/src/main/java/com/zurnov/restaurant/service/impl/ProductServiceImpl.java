package com.zurnov.restaurant.service.impl;

import com.zurnov.restaurant.model.Product;
import com.zurnov.restaurant.repository.ProductRepository;
import com.zurnov.restaurant.service.ProductCategoryService;
import com.zurnov.restaurant.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryService productCategoryService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
    }

    @Override
    public Product update(int productId, Product product) {

        if (!checkProductAndProductIdIsMatching(productId, product)) {
            return null;
        }

        if (!validateProductCategoryExistence(product)) {
            return null;
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            return null;
        }

        Product existingProduct = optionalProduct.get();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setProductCategory(product.getProductCategory());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setIsDeleted(product.getIsDeleted());

        return productRepository.saveAndFlush(existingProduct);
    }

    private boolean checkProductAndProductIdIsMatching(int productId, Product product) {
        if (product == null || productId != product.getId()) {
            return false;
        }
        return true;
    }

    private boolean validateProductCategoryExistence(Product product) {
        return productCategoryService.existsById(product.getProductCategory().getId());
    }

    @Override
    public Product save(Product product) {

        if (product != null && validateProductCategoryExistence(product)) {
            return productRepository.saveAndFlush(product);
        }

        return null;
    }

    @Override
    public Product findByIdAndIsDeleted(int id, boolean isProductDeleted) {
        Optional<Product> product = productRepository.findByIdAndIsDeleted(id, isProductDeleted);

        return product.orElse(null);
    }

    public List<Product> findProducts(boolean isProductDeleted) {

        return productRepository.findByIsDeleted(isProductDeleted);
    }

    @Override
    public Product delete(int productId) {

        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isEmpty()) {
            return null;
        }

        existingProduct.get().setIsDeleted(true);

        return productRepository.saveAndFlush(existingProduct.get());
    }

    @Override
    public boolean existsById(int productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public boolean existsByIdIn(List<Integer> productIds) {
        return productRepository.existsByIdIn(productIds);
    }
}

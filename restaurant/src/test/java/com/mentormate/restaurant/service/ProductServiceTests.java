package com.mentormate.restaurant.service;


import com.mentormate.restaurant.model.Product;
import com.mentormate.restaurant.model.ProductCategory;
import com.mentormate.restaurant.repository.ProductRepository;
import com.mentormate.restaurant.service.impl.ProductCategoryServiceImpl;
import com.mentormate.restaurant.service.impl.ProductServiceImpl;
import com.mentormate.restaurant.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Mock
    ProductCategoryServiceImpl productCategoryService;

    @Test
    void updateProductWithProductProvided_thenOk() {

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product expectedProduct = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);


        when(productRepository.findById(expectedProduct.getId())).thenReturn(Optional.of(expectedProduct));
        when(productCategoryService.existsById(productCategory.getId())).thenReturn(true);
        when(productRepository.saveAndFlush(expectedProduct)).thenReturn(expectedProduct);

        expectedProduct.setId(TestHelper.ID_2);
        expectedProduct.setIsDeleted(Boolean.TRUE);

        expectedProduct.setId(TestHelper.ID_1);
        Product actualProduct = productServiceImpl.update(expectedProduct.getId(), expectedProduct);

        expectedProduct.setId(TestHelper.ID_1);
        expectedProduct.setIsDeleted(TestHelper.IS_DELETED_FALSE);
        assertEquals(expectedProduct, actualProduct);
        assertEquals(expectedProduct.getId(), actualProduct.getId());
        assertEquals(expectedProduct.getIsDeleted(), actualProduct.getIsDeleted());
    }

    @Test
    void updateProductWithProductProvidedAndProductCategoryNotExisting_thenNull() {

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product expectedProduct = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);


        when(productCategoryService.existsById(productCategory.getId())).thenReturn(false);
        
        Product actualProduct = productServiceImpl.update(expectedProduct.getId(), expectedProduct);

        assertNull(actualProduct);
    }

    @Test
    void updateProductWithoutProductProvided_thenNull() {

        Product actualProduct = productServiceImpl.update(TestHelper.ID_1, null);

        assertNull(actualProduct);
    }

    @Test
    void updateProductWithProductProvidedAndExistingProductIsEmpty_thenNull() {

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product expectedProduct = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        when(productCategoryService.existsById(productCategory.getId())).thenReturn(true);
        when(productRepository.findById(expectedProduct.getId())).thenReturn(Optional.empty());

        Product actualProduct = productServiceImpl.update(expectedProduct.getId(), expectedProduct);

        assertNull(actualProduct);
    }

    @Test
    void whenFindProductByIdAndIsDeleted_thenOk() {
        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product expectedProduct = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        when(productRepository.findByIdAndIsDeleted(expectedProduct.getId(), TestHelper.IS_DELETED_TRUE))
                .thenReturn(Optional.of(expectedProduct));

        Product actualProduct = productServiceImpl.findByIdAndIsDeleted(expectedProduct.getId(), TestHelper.IS_DELETED_TRUE);

        assertEquals(expectedProduct.getId(), actualProduct.getId());
        assertEquals(expectedProduct.getIsDeleted(), actualProduct.getIsDeleted());
    }

    @Test
    void whenFindProductByIdAndIsDeleted_thenNull() {

        when(productRepository.findByIdAndIsDeleted(TestHelper.ID_1, TestHelper.IS_DELETED_TRUE))
                .thenReturn(Optional.empty());

        Product actualProduct = productServiceImpl.findByIdAndIsDeleted(TestHelper.ID_1, TestHelper.IS_DELETED_TRUE);

        assertNull(actualProduct);
    }

    @Test
    void whenFindProductByIsDeleted_thenOk() {
        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product expectedProduct = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        List<Product> expectedProducts = List.of(expectedProduct);

        when(productRepository.findByIsDeleted(TestHelper.IS_DELETED_TRUE)).thenReturn(List.of(expectedProduct));

        List<Product> actualProducts = productServiceImpl.findProducts(TestHelper.IS_DELETED_TRUE);

        assertEquals(expectedProducts.size(), actualProducts.size());
        assertEquals(expectedProducts.get(0).getIsDeleted(), actualProducts.get(0).getIsDeleted());
    }

    @Test
    void whenFindProductByIsDeleted_thenNull() {

        when(productRepository.findByIsDeleted(TestHelper.IS_DELETED_TRUE)).thenReturn(null);

        List<Product> actualProduct = productServiceImpl.findProducts(TestHelper.IS_DELETED_TRUE);

        assertNull(actualProduct);
    }

    @Test
    void whenSaveProductWithProductProvided_thenOk() {

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product expectedProduct = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        when(productCategoryService.existsById(productCategory.getId())).thenReturn(true);
        when(productRepository.saveAndFlush(expectedProduct)).thenReturn(expectedProduct);

        Product actualProduct = productServiceImpl.save(expectedProduct);

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void whenSaveProductWithoutProductProvided_thenNull() {
        Product actualProduct = productServiceImpl.save(null);

        assertNull(actualProduct);
    }

    @Test
    void whenDeleteProductForExistingProduct_thenOk() {

        ProductCategory productCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        Product expectedProduct = TestHelper.createProduct(
                TestHelper.ID_1,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        when(productRepository.findById(expectedProduct.getId())).thenReturn(Optional.of(expectedProduct));
        when(productRepository.saveAndFlush(expectedProduct)).thenReturn(expectedProduct);

        Product actualProduct = productServiceImpl.delete(expectedProduct.getId());

        assertEquals(expectedProduct.getIsDeleted(), actualProduct.getIsDeleted());
    }

    @Test
    void whenDeleteProductForNonExistingProduct_thenNull() {

        when(productRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        Product actualProduct = productServiceImpl.delete(TestHelper.ID_1);

        assertNull(actualProduct);
    }
}

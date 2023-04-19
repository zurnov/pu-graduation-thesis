package com.zurnov.restaurant.service;


import com.zurnov.restaurant.exception.BadRequestException;
import com.zurnov.restaurant.exception.NotFoundException;
import com.zurnov.restaurant.model.ProductCategory;
import com.zurnov.restaurant.repository.ProductCategoryRepository;
import com.zurnov.restaurant.service.impl.ProductCategoryServiceImpl;
import com.zurnov.restaurant.util.HttpStatusHelper;
import com.zurnov.restaurant.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceTests {

    @Mock
    ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    ProductCategoryServiceImpl productCategoryService;

    @Test
    void whenGetByProductCategoryIdWithIdProvided_thenOk() {

        ProductCategory expectedProductCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        when(productCategoryRepository.findById(expectedProductCategory.getId()))
                .thenReturn(Optional.of(expectedProductCategory));

        ProductCategory actualProductCategory = productCategoryService.findById(TestHelper.ID_1);

        assertEquals(expectedProductCategory.getId(), actualProductCategory.getId());
    }

    @Test
    void whenGetByProductCategoryIdWithoutIdProvided_thenNotFoundException() {

        when(productCategoryRepository.findById(TestHelper.ID_1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> productCategoryService.findById(TestHelper.ID_1),
                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product category", TestHelper.ID_1));
    }

    @Test
    void whenGetByProductCategoryNameWithNameProvided_thenOk() {

        ProductCategory expectedProductCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        List<ProductCategory> expectedProductCategories = List.of(expectedProductCategory);

        when(productCategoryRepository.findProductCategoryByName(expectedProductCategory.getName()))
                .thenReturn(Optional.of(expectedProductCategory));

        List<ProductCategory> actualProductCategory = productCategoryService
                .findProductCategories(expectedProductCategory.getName());

        assertEquals(expectedProductCategories.size(), actualProductCategory.size());
        assertEquals(expectedProductCategories.get(0).getName(), actualProductCategory.get(0).getName());
    }

    @Test
    void whenGetByProductCategoryNameWithoutNameProvided_thenOk() {

        ProductCategory expectedProductCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        ProductCategory secondExpectedProductCategory = TestHelper.createProductCategory(
                TestHelper.ID_2,
                TestHelper.PRODUCT_CATEGORY_NAME);

        List<ProductCategory> expectedProductCategories = List.of(expectedProductCategory, secondExpectedProductCategory);

        when(productCategoryRepository.findAll()).thenReturn(expectedProductCategories);

        List<ProductCategory> actualProductCategory = productCategoryService.findProductCategories(null);

        assertEquals(expectedProductCategories.size(), actualProductCategory.size());
        assertEquals(expectedProductCategories.get(0).getName(), actualProductCategory.get(0).getName());
        assertEquals(expectedProductCategories.get(1).getName(), actualProductCategory.get(1).getName());
    }

    @Test
    void whenSaveProductCategoryWithProductCategoryProvided_thenOk() {

        ProductCategory expectedProductCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        when(productCategoryRepository.save(expectedProductCategory)).thenReturn(expectedProductCategory);

        ProductCategory actualProductCategory = productCategoryService.create(expectedProductCategory);

        assertEquals(expectedProductCategory, actualProductCategory);
    }

    @Test
    void whenSaveProductCategoryWithoutProductCategoryProvided_thenBadRequestException() {

        assertThrows(BadRequestException.class,
                () -> productCategoryService.create(null),
                String.format(HttpStatusHelper.BAD_REQUEST_EXCEPTION_CREATE, "Product Category"));
    }

    @Test
    void whenUpdateProductCategoryWithProductCategoryProvided_thenOk() {

        ProductCategory expectedProductCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        when(productCategoryRepository.findById(expectedProductCategory.getId()))
                .thenReturn(Optional.of(expectedProductCategory));

        when(productCategoryRepository.saveAndFlush(expectedProductCategory))
                .thenReturn(expectedProductCategory);

        expectedProductCategory.setId(TestHelper.ID_2);

        expectedProductCategory.setId(TestHelper.ID_1);
        ProductCategory actualProductCategory = productCategoryService
                .update(expectedProductCategory.getId(), expectedProductCategory);

        expectedProductCategory.setId(TestHelper.ID_1);
        assertEquals(expectedProductCategory, actualProductCategory);
        assertEquals(expectedProductCategory.getId(), actualProductCategory.getId());
    }

    @Test
    void updateProductCategoryWithoutProductCategoryProvided_thenNull() {
        ProductCategory actualProductCategory = productCategoryService.update(TestHelper.ID_1, null);

        assertNull(actualProductCategory);
    }

    @Test
    void updateProductCategoryWithProductCategoryProvidedAndExistingProductCategoryIsEmpty_thenNull() {

        ProductCategory expectedProductCategory = TestHelper.createProductCategory(
                TestHelper.ID_1,
                TestHelper.PRODUCT_CATEGORY_NAME);

        when(productCategoryRepository.findById(expectedProductCategory.getId())).thenReturn(Optional.empty());

        ProductCategory actualProductCategory = productCategoryService
                .update(expectedProductCategory.getId(), expectedProductCategory);

        assertNull(actualProductCategory);
    }
}

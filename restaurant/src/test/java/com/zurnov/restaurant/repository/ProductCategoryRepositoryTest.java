package com.zurnov.restaurant.repository;

import com.zurnov.restaurant.config.PostgreSQLExtension;
import com.zurnov.restaurant.config.RestaurantPostgreSQLContainer;
import com.zurnov.restaurant.exception.NotFoundException;
import com.zurnov.restaurant.model.ProductCategory;
import com.zurnov.restaurant.util.HttpStatusHelper;
import com.zurnov.restaurant.util.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(value = PostgreSQLExtension.class)
class ProductCategoryRepositoryTest extends RestaurantPostgreSQLContainer {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductCategoryRepositoryTest(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Test
    void givenProductCategory_whenFindProductCategoryByName_thenReturnProductCategory() {
        ProductCategory expectedProductCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));
        
        Optional<ProductCategory> actualProductCategory = productCategoryRepository.findProductCategoryByName(
                expectedProductCategory.getName());

        Assertions.assertFalse(actualProductCategory.isEmpty());
        Assertions.assertEquals(expectedProductCategory.getName(), actualProductCategory.get().getName());
    }

    @Test
    void givenProductCategory_whenExistsById_thenReturnTrue() {

        ProductCategory expectedProductCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));
        

        boolean actualProductCategory = productCategoryRepository.existsById(expectedProductCategory.getId());

        Assertions.assertTrue(actualProductCategory);
    }
}

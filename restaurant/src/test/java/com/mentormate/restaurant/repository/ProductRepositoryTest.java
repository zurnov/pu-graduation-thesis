package com.mentormate.restaurant.repository;

import com.mentormate.restaurant.config.PostgreSQLExtension;
import com.mentormate.restaurant.config.RestaurantPostgreSQLContainer;
import com.mentormate.restaurant.exception.NotFoundException;
import com.mentormate.restaurant.model.Product;
import com.mentormate.restaurant.model.ProductCategory;
import com.mentormate.restaurant.util.HttpStatusHelper;
import com.mentormate.restaurant.util.TestHelper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(PostgreSQLExtension.class)
class ProductRepositoryTest extends RestaurantPostgreSQLContainer {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRepositoryTest(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Test
    void givenProduct_whenFindByIsDeleted_thenReturnProduct() {
        final int EXPECTED_NOT_DELETED_PRODUCTS = 3;

        List<Product> actualProducts = productRepository.findByIsDeleted(TestHelper.IS_DELETED_FALSE);

        Assertions.assertFalse(actualProducts.isEmpty());
        MatcherAssert.assertThat(actualProducts.size(), is(EXPECTED_NOT_DELETED_PRODUCTS));

    }

    @Test
    void givenProduct_whenFindByIdAndIsDeleted_thenReturnProduct() {

        ProductCategory productCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));

        Product expectedProduct = TestHelper.createProduct(
                null,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        productRepository.saveAndFlush(expectedProduct);

        Optional<Product> actualProduct = productRepository.findByIdAndIsDeleted(
                expectedProduct.getId(), TestHelper.IS_DELETED_FALSE);

        Assertions.assertFalse(actualProduct.isEmpty());
        Assertions.assertEquals(expectedProduct.getId(), actualProduct.get().getId());
    }

    @Test
    void givenProduct_whenExistsById_thenReturnTrue() {
        
        ProductCategory productCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));

        Product expectedProduct = TestHelper.createProduct(
                null,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        productRepository.saveAndFlush(expectedProduct);
        
        boolean actualProduct = productRepository.existsById(expectedProduct.getId());

        Assertions.assertTrue(actualProduct);
    }

    @Test
    void givenProduct_existsByIdIn_thenReturnTrue() {

        ProductCategory productCategory =
                productCategoryRepository.findProductCategoryByName(TestHelper.PRODUCT_CATEGORY_NAME)
                        .orElseThrow(() -> new NotFoundException(
                                String.format(HttpStatusHelper.NOT_FOUND_EXCEPTION_ID, "Product Category", 1)));

        Product expectedProduct = TestHelper.createProduct(
                null,
                TestHelper.PRODUCT_NAME,
                TestHelper.IS_DELETED_FALSE,
                TestHelper.PRODUCT_PRICE,
                TestHelper.PRODUCT_DESCRIPTION,
                productCategory);

        productRepository.saveAndFlush(expectedProduct);
        
        List<Integer> expectedProducts = List.of(expectedProduct.getId());

        boolean actualProduct = productRepository.existsByIdIn(expectedProducts);

        Assertions.assertTrue(actualProduct);
    }
}

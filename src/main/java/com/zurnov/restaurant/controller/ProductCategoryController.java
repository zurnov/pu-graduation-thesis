package com.zurnov.restaurant.controller;

import com.zurnov.restaurant.dto.product.ProductCategoryCreateDTO;
import com.zurnov.restaurant.dto.product.ProductCategoryDTO;
import com.zurnov.restaurant.model.ProductCategory;
import com.zurnov.restaurant.service.ProductCategoryService;
import com.zurnov.restaurant.util.PathHelper;
import com.zurnov.restaurant.util.converter.dto.ProductCategoryDtoConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PathHelper.BASE_PATH + PathHelper.PRODUCT_CATEGORY_PATH)
@Validated
public class ProductCategoryController {

    ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/{productCategoryId}")
    @Operation(
            tags = "Product Category",
            operationId = "getProductCategoryById",
            summary = "get ProductCategory object by id",
            description = "Finds and returns productCategory that corresponds to the id of the ProductCategory" +
                    " or else returns null",
            parameters = @Parameter(
                    name = "productCategoryId",
                    description = "id for which we want to filter product categories",
                    example = "2"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = ProductCategory.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Product Category found"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    schema = @Schema(implementation = ProductCategory.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            ),
                            description = "Product Category not found!"
                    )
            }
    )
    public ProductCategoryDTO getProductCategoryById(@PathVariable int productCategoryId) {
        ProductCategory productCategory = productCategoryService.findById(productCategoryId);
        return ProductCategoryDtoConverter.toProductCategoryDTO(productCategory);
    }

    @GetMapping()
    @Operation(
            tags = "Product Category",
            operationId = "getProductCategoryByCategoryName",
            summary = "get ProductCategory object by product category name",
            description = "Finds and returns productCategory that corresponds to the categoryName" +
                    " or else returns all product category names",
            parameters = @Parameter(
                    name = "productCategoryName",
                    description = "categoryName with which we want to filter product all categories",
                    example = "Salad"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = ProductCategory.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public List<ProductCategoryDTO> getProductCategories(
            @RequestParam(name = "productCategoryName", required = false) String productCategoryName) {
        List<ProductCategory> productCategories = productCategoryService.findProductCategories(productCategoryName);
        return productCategories.stream()
                .map(ProductCategoryDtoConverter::toProductCategoryDTO)
                .collect(Collectors.toList());
    }

    @PostMapping()
    @Operation(
            tags = "Product Category",
            operationId = "createProductCategory",
            summary = "create Product category",
            description = "Saves and returns the saved ProductCategory" +
                    " or else returns null if the ProductCategory is null",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "productCategory object to be saved"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = ProductCategory.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public ProductCategoryDTO createProductCategory(
            @Valid @RequestBody ProductCategoryCreateDTO productCategoryCreateDTO) {
        ProductCategory productCategory = ProductCategoryDtoConverter.toProductCategoryEntity(productCategoryCreateDTO);
        ProductCategory createdProductCategory = productCategoryService.create(productCategory);

        return ProductCategoryDtoConverter.toProductCategoryDTO(createdProductCategory);
    }

    @PutMapping("/{productCategoryId}")
    @Operation(
            tags = "Product Category",
            operationId = "updateProductCategory",
            summary = "update Product category",
            description = "Finds and updates the User by given product category id." +
                    " If there is no such element null will be returned",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "product category object that will be overriding the existing one"
            ),
            parameters = @Parameter(
                    name = "productCategoryId",
                    description = "id of the product category that we want to update",
                    example = "6"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = ProductCategory.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public ProductCategoryDTO updateProductCategory(@PathVariable int productCategoryId,
                                                    @Valid @RequestBody ProductCategoryDTO productCategoryDTO) {

        ProductCategory productCategory = ProductCategoryDtoConverter.toProductCategoryEntity(productCategoryDTO);
        ProductCategory updatedProductCategory = productCategoryService.update(productCategoryId, productCategory);

        return ProductCategoryDtoConverter.toProductCategoryDTO(updatedProductCategory);
    }
}

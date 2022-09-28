package com.mentormate.restaurant.controller;

import com.mentormate.restaurant.dto.product.ProductCreateDTO;
import com.mentormate.restaurant.dto.product.ProductDTO;
import com.mentormate.restaurant.model.Product;
import com.mentormate.restaurant.service.ProductService;
import com.mentormate.restaurant.util.PathHelper;
import com.mentormate.restaurant.util.converter.dto.ProductDtoConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(PathHelper.BASE_PATH + PathHelper.PRODUCT_PATH)
@Validated
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    @Operation(
            tags = "Product",
            operationId = "getProductsByIsDeleted",
            summary = "get List of Product objects by isDeleted",
            description = "Finds and returns Product objects list by isDeleted flag" +
                    " or else return null if there are no products matched",
            parameters = @Parameter(
                    name = "isDeleted",
                    description = "softDelete flag with which we want to filter the Products"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public List<ProductDTO> getProductsByIsDeleted(@RequestParam(required = false, defaultValue = "false") boolean isDeleted) {
        List<Product> products = productService.findProducts(isDeleted);

        return products.stream()
                .map(ProductDtoConverter::toProductDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{productId}")
    @Operation(
            tags = "Product",
            operationId = "getProductsByIdAndIsDeleted",
            summary = "get Product object by Id and isDeleted",
            description = "Finds and returns Product by some criteria" +
                    " or else return null if the criteria are not matched.",
            parameters = {
                    @Parameter(
                            name = "productId",
                            description = "id with which we want to filter all the Products",
                            example = "6"
                    ),
                    @Parameter(
                            name = "isDeleted",
                            description = "softDelete flag with which we want to filter the Products"
                    )
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public ProductDTO getProductsByIdAndIsDeleted(
            @PathVariable int productId, @RequestParam(required = false, defaultValue = "false") boolean isDeleted) {
        Product products = productService.findByIdAndIsDeleted(productId, isDeleted);

        return ProductDtoConverter.toProductDTO(products);
    }

    @PostMapping()
    @Operation(
            tags = "Product",
            operationId = "createProduct",
            summary = "create Product",
            description = "Saves and returns the saved Product" +
                    " or else returns null if the Product is null.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "product to be saved"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public ProductDTO createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        Product product = ProductDtoConverter.toProductEntity(productCreateDTO);
        Product createdProduct = productService.save(product);

        return ProductDtoConverter.toProductDTO(createdProduct);
    }

    @PutMapping("/{productId}")
    @Operation(
            tags = "Product",
            operationId = "updateProduct",
            summary = "update Product",
            description = "Finds and updates the Product by given product id." +
                    " If there is no such element null will be returned",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "product object that will be overriding the existing one"
            ),
            parameters = @Parameter(
                    name = "productId",
                    description = "id of the product that we want to update",
                    example = "4"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public ProductDTO updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable int productId) {
        Product product = ProductDtoConverter.toProductEntity(productDTO);
        Product updatedProduct = productService.update(productId, product);

        return ProductDtoConverter.toProductDTO(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    @Operation(
            tags = "Product",
            operationId = "deleteProduct",
            summary = "delete Product",
            description = "Finds and soft deletes the Product by id" +
                    " or else return null if there is not such product.",
            parameters = @Parameter(
                    name = "productId",
                    description = "id of the product that we want to soft delete",
                    example = "2"
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = Product.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    ),
                    description = "Success Response"
            )
    )
    public ProductDTO deleteProduct(@PathVariable int productId) {
        Product deletedProduct = productService.delete(productId);

        return ProductDtoConverter.toProductDTO(deletedProduct);
    }
}

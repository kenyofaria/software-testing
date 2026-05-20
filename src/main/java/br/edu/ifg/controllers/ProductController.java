package br.edu.ifg.controllers;

import br.edu.ifg.entities.Product;
import br.edu.ifg.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management API")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a product", description = "Creates a new product and returns the persisted entity")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "400", description = "Invalid product data", content = @Content)
    })
    public Product create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product to create", required = true,
                    content = @Content(schema = @Schema(implementation = Product.class)))
            @RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a product by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product found",
                content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public Product getById(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable Long id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product updated successfully",
                content = @Content(schema = @Schema(implementation = Product.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public Product update(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated product data", required = true,
                    content = @Content(schema = @Schema(implementation = Product.class)))
            @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Product deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public void delete(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable Long id) {
        productService.delete(id);
    }
}
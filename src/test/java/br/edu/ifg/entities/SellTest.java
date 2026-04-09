package br.edu.ifg.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SellTest {

    @Test
    @DisplayName("Should add product and create SellItem successfully, when items is empty")
    void addProduct_Success_EmptyItems() {
        // Arrange
        Product product = Product.builder().name("Laptop").price(new BigDecimal("1000")).build();
        Sell sell = Sell.builder()
                .status(SellStatus.OPEN)
                .items(new ArrayList<>())
                .build();

        // Act
        sell.addProduct(product, 2);

        // Assert
        assertThat(sell.getItems()).hasSize(1);
        SellItem createdItem = sell.getItems().get(0);
        assertThat(createdItem.getProduct()).isEqualTo(product);
        assertThat(createdItem.getAmount()).isEqualTo(2);
        assertThat(createdItem.getSell()).isEqualTo(sell);
        assertThat(createdItem.getPrice()).isEqualByComparingTo("1000");
    }

    @Test
    @DisplayName("Should add product and create SellItem successfully, when items is null")
    void addProduct_Success_NullItems() {
        // Arrange
        Product product = Product.builder().name("Laptop").price(new BigDecimal("1000")).build();
        Sell sell = Sell.builder()
                .status(SellStatus.OPEN)
                .build();

        // Act
        sell.addProduct(product, 2);

        // Assert
        assertThat(sell.getItems()).hasSize(1);
        SellItem createdItem = sell.getItems().get(0);
        assertThat(createdItem.getProduct()).isEqualTo(product);
        assertThat(createdItem.getAmount()).isEqualTo(2);
        assertThat(createdItem.getSell()).isEqualTo(sell);
        assertThat(createdItem.getPrice()).isEqualByComparingTo("1000");
    }

    @Test
    @DisplayName("Should throw exception when product is null")
    void addProduct_NullProduct_ThrowsException() {
        Sell sell = Sell.builder().status(SellStatus.OPEN).items(new ArrayList<>()).build();

        assertThatThrownBy(() -> sell.addProduct(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product must not be null and amount must be positive.");
    }

    @Test
    @DisplayName("Should throw exception when amount is zero or negative")
    void addProduct_InvalidAmount_ThrowsException() {
        Sell sell = Sell.builder().status(SellStatus.OPEN).items(new ArrayList<>()).build();
        Product product = Product.builder().build();

        // Branch 1: Zero
        assertThatThrownBy(() -> sell.addProduct(product, 0))
                .isInstanceOf(IllegalArgumentException.class);

        // Branch 2: Negative
        assertThatThrownBy(() -> sell.addProduct(product, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should throw exception when status is not OPEN")
    void addProduct_FinishedStatus_ThrowsException() {
        Sell sell = Sell.builder().status(SellStatus.FINISHED).items(new ArrayList<>()).build();
        Product product = Product.builder().build();

        assertThatThrownBy(() -> sell.addProduct(product, 1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot add items to a sale that is not OPEN.");
    }

    @Test
    @DisplayName("Should return distinct list of products")
    void getProductsInSell_ReturnsUniqueProducts() {
        // Arrange
        Product p1 = Product.builder().id(1L).name("A").build();
        Product p2 = Product.builder().id(2L).name("B").build();

        Sell sell = Sell.builder().status(SellStatus.OPEN).items(new ArrayList<>()).build();

        // Simulating the internal list state
        sell.addProduct(p1, 1);
        sell.addProduct(p2, 1);
        sell.addProduct(p1, 5); // Add same product again

        // Act
        List<Product> products = sell.getProductsInSell();

        // Assert
        assertThat(products).hasSize(2);
        assertThat(products).containsExactlyInAnyOrder(p1, p2);
    }
}

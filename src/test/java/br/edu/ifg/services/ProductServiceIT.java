package br.edu.ifg.services;

import br.edu.ifg.entities.Product;
import br.edu.ifg.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Service integration test: uses SQLite as an embedded, file-less database.
 *
 * The "sqlite" profile activates application-sqlite.properties (in src/test/resources),
 * which overrides the default H2 datasource with SQLite. No Docker or external server
 * is required — SQLite runs entirely in-process.
 *
 * Why SQLite here instead of Testcontainers?
 *   Service tests validate business logic + JPA persistence without going through the
 *   HTTP layer. SQLite is fast, zero-config, and sufficient for that scope.
 *   Controller tests (ProductControllerIT) use a real MySQL container to validate the
 *   full stack including MySQL-specific behaviour.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductServiceIT {

    @Autowired
    private ProductService subject;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    @DisplayName("Should persist and retrieve a product from the database")
    void createAndRead_Integration() {
        // Arrange
        Product newProduct = Product.builder()
                .name("Mechanical Keyboard")
                .price(new BigDecimal("150.00"))
                .inventory(10)
                .build();

        // Act
        Product saved = subject.create(newProduct);

        // Assert
        assertThat(saved.getId()).isNotNull();
        Product found = subject.getById(saved.getId());
        assertThat(found.getName()).isEqualTo("Mechanical Keyboard");
    }

    @Test
    @Transactional
    @DisplayName("Should update an existing product in the database")
    void update_Integration() {
        // Arrange
        Product original = productRepository.save(Product.builder()
                .name("Old Mouse")
                .price(new BigDecimal("20.00"))
                .inventory(5)
                .build());

        Product updateDetails = Product.builder()
                .name("Gaming Mouse")
                .price(new BigDecimal("80.00"))
                .inventory(15)
                .build();

        // Act
        Product updated = subject.update(original.getId(), updateDetails);

        // Assert
        assertThat(updated.getName()).isEqualTo("Gaming Mouse");
        assertThat(updated.getPrice()).isEqualByComparingTo("80.00");

        // Verify it's the same record, not a new one
        assertThat(productRepository.count()).isEqualTo(1);
    }

    @Test
    @Transactional
    @DisplayName("Should delete a product and no longer find it")
    void delete_Integration() {
        // Arrange
        Product product = productRepository.save(Product.builder()
                .name("Disposable Item")
                .build());
        Long id = product.getId();

        // Act
        subject.delete(id);

        // Assert
        assertThat(productRepository.existsById(id)).isFalse();
        assertThrows(EntityNotFoundException.class, () -> subject.getById(id));
    }
}
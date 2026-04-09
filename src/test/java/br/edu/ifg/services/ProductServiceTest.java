package br.edu.ifg.services;

import br.edu.ifg.entities.Product;
import br.edu.ifg.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService subject;

    @Test
    @DisplayName("Should create product successfully")
    void create_Success() {
        // Arrange
        Product product = Product.builder().name("Keyboard").build();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = subject.create(product);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Keyboard");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Should find product by id")
    void getById_Success() {
        // Arrange
        Product product = Product.builder().id(1L).name("Mouse").build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product result = subject.getById(1L);

        // Assert
        assertThat(result.getName()).isEqualTo("Mouse");
    }

    @Test
    @DisplayName("Should throw exception when product not found by id")
    void getById_NotFound_ThrowsException() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> subject.getById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Product not found");
    }

    @Test
    @DisplayName("Should update product using toBuilder logic")
    void update_Success() {
        // Arrange
        Product existing = Product.builder()
                .id(1L).name("Old Name").price(BigDecimal.ONE).inventory(1).build();

        Product details = Product.builder()
                .name("New Name").price(BigDecimal.TEN).inventory(10).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        // Capture the argument passed to save to verify the builder worked
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Product updated = subject.update(1L, details);

        // Assert
        assertThat(updated.getId()).isEqualTo(1L); // Must keep same ID
        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getPrice()).isEqualTo(BigDecimal.TEN);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should delete product when it exists")
    void delete_Success() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(true);

        // Act
        subject.delete(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent product")
    void delete_NotFound_ThrowsException() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> subject.delete(1L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(productRepository, never()).deleteById(anyLong());
    }
}

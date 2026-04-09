package br.edu.ifg.services;

import br.edu.ifg.entities.Product;
import br.edu.ifg.entities.Sell;
import br.edu.ifg.entities.SellStatus;
import br.edu.ifg.repositories.SellRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellServiceTest {

    @Mock
    private SellRepository sellRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private SellService sellService;

    @Test
    @DisplayName("Should create a new OPEN sell")
    void create_Success() {
        // Arrange
        when(sellRepository.save(any(Sell.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Sell result = sellService.create();

        // Assert
        assertThat(result.getStatus()).isEqualTo(SellStatus.OPEN);
        assertThat(result.getDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
        verify(sellRepository).save(any(Sell.class));
    }

    @Test
    @DisplayName("Should find sell by id or throw exception")
    void getById_Test() {
        Sell sell = Sell.builder().id(1L).build();
        when(sellRepository.findById(1L)).thenReturn(Optional.of(sell));
        when(sellRepository.findById(2L)).thenReturn(Optional.empty());

        // Success branch
        assertThat(sellService.getById(1L)).isEqualTo(sell);

        // Exception branch
        assertThatThrownBy(() -> sellService.getById(2L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sell not found");
    }

    @Test
    @DisplayName("Should add item successfully calling both repository and product service")
    void addItem_Success() {
        // Arrange
        Product product = Product.builder().id(10L).price(BigDecimal.TEN).build();
        // Note: Using @Builder.Default in Sell entity ensures items list is not null
        Sell sell = Sell.builder().id(1L).status(SellStatus.OPEN).build();

        when(sellRepository.findById(1L)).thenReturn(Optional.of(sell));
        when(productService.getById(10L)).thenReturn(product);
        when(sellRepository.save(any(Sell.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Sell result = sellService.addItem(1L, 10L, 5);

        // Assert
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getItems().get(0).getProduct()).isEqualTo(product);
        verify(sellRepository).save(sell);
    }

    @Test
    @DisplayName("Should update status if current status is not FINISHED")
    void updateStatus_Success() {
        // Arrange
        Sell existing = Sell.builder().id(1L).status(SellStatus.OPEN).build();
        when(sellRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(sellRepository.save(any(Sell.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Sell result = sellService.updateStatus(1L, SellStatus.FINISHED);

        // Assert
        assertThat(result.getStatus()).isEqualTo(SellStatus.FINISHED);
        verify(sellRepository).save(any(Sell.class));
    }

    @Test
    @DisplayName("Should throw exception when updating status of a FINISHED sale")
    void updateStatus_Fail_AlreadyFinished() {
        // Arrange
        Sell existing = Sell.builder().id(1L).status(SellStatus.FINISHED).build();
        when(sellRepository.findById(1L)).thenReturn(Optional.of(existing));

        // Act & Assert
        assertThatThrownBy(() -> sellService.updateStatus(1L, SellStatus.OPEN))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot change status of a finished sale.");

        verify(sellRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete sale if not FINISHED")
    void delete_Success() {
        // Arrange
        Sell sell = Sell.builder().id(1L).status(SellStatus.OPEN).build();
        when(sellRepository.findById(1L)).thenReturn(Optional.of(sell));

        // Act
        sellService.delete(1L);

        // Assert
        verify(sellRepository).delete(sell);
    }

    @Test
    @DisplayName("Should throw exception when deleting a FINISHED sale")
    void delete_Fail_Finished() {
        // Arrange
        Sell sell = Sell.builder().id(1L).status(SellStatus.FINISHED).build();
        when(sellRepository.findById(1L)).thenReturn(Optional.of(sell));

        // Act & Assert
        assertThatThrownBy(() -> sellService.delete(1L))
                .isInstanceOf(IllegalStateException.class);

        verify(sellRepository, never()).delete(any());
    }
}
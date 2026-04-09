package br.edu.ifg.services;

import br.edu.ifg.entities.Product;
import br.edu.ifg.entities.Sell;
import br.edu.ifg.entities.SellStatus;
import br.edu.ifg.repositories.SellRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;
    private final ProductService productService;

    @Transactional
    public Sell create() {
        Sell sell = Sell.builder()
                .dateTime(LocalDateTime.now())
                .status(SellStatus.OPEN)
                .build();
        return sellRepository.save(sell);
    }

    public Sell getById(Long id) {
        return sellRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sell not found"));
    }

    @Transactional
    public Sell addItem(Long sellId, Long productId, Integer amount) {
        Sell sell = getById(sellId);
        Product product = productService.getById(productId);

        sell.addProduct(product, amount);

        return sellRepository.save(sell);
    }

    @Transactional
    public Sell updateStatus(Long id, SellStatus newStatus) {
        Sell existing = getById(id);

        // Logic Check: Don't allow changing status of a FINISHED sale
        if (existing.getStatus() == SellStatus.FINISHED) {
            throw new IllegalStateException("Cannot change status of a finished sale.");
        }

        Sell updated = existing.toBuilder()
                .status(newStatus)
                .build();

        return sellRepository.save(updated);
    }

    @Transactional
    public void delete(Long id) {
        Sell sell = getById(id);
        if (sell.getStatus() == SellStatus.FINISHED) {
            throw new IllegalStateException("Cannot delete a finished sale.");
        }
        sellRepository.delete(sell);
    }
}

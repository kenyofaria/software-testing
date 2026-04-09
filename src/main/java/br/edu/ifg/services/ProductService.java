package br.edu.ifg.services;

import br.edu.ifg.entities.Product;
import br.edu.ifg.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        if (product.readyForPersistence()) {
            return productRepository.save(product);
        }
        throw new IllegalArgumentException("Invalid product data");
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Transactional
    public Product update(Long id, Product details) {
        Product existing = getById(id);

        Product updatedProduct = existing.toBuilder()
                .name(details.getName())
                .price(details.getPrice())
                .inventory(details.getInventory())
                .build();

        return productRepository.save(updatedProduct);
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}

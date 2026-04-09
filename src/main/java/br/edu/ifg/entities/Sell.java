package br.edu.ifg.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private SellStatus status; // OPEN, FINISHED, CANCELLED

    @Builder.Default
    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL)
    private List<SellItem> items = new ArrayList<>();

    public List<Product> getProductsInSell() {
        return this.items.stream()
                .map(SellItem::getProduct)
                .distinct()
                .toList();
    }

    public void addProduct(Product product, Integer amount) {
        if (product == null || amount <= 0) {
            throw new IllegalArgumentException("Product must not be null and amount must be positive.");
        }

        if (this.status != SellStatus.OPEN) {
            throw new IllegalStateException("Cannot add items to a sale that is not OPEN.");
        }

        SellItem newItem = SellItem.builder()
                .sell(this)
                .product(product)
                .amount(amount)
                .price(product.getPrice())
                .build();

        this.items.add(newItem);
    }
}

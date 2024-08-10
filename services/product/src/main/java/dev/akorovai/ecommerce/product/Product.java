package dev.akorovai.ecommerce.product;

import dev.akorovai.ecommerce.category.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;
    private String description;
    private double availableQuantity;
    private BigDecimal price;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}

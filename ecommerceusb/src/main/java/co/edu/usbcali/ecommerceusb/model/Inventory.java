package co.edu.usbcali.ecommerceusb.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "inventory",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_inventory_product", columnNames = {"product_id"})
        }
)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_product"),
            referencedColumnName = "id"
    )
    private Product product;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
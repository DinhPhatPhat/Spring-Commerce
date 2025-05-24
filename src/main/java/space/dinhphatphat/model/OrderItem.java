package space.dinhphatphat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT AUTO_INCREMENT")
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_items_order"))
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_order_items_product"))
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private int quantity;

    @Column(name = "price", nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn hoặc bằng 0")
    private BigDecimal price;
}

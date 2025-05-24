package space.dinhphatphat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT AUTO_INCREMENT")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_orders_user"))
    private User user;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone_number", nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "total_price", nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Tổng tiền phải lớn hơn hoặc bằng 0")
    private BigDecimal totalPrice;

    @Column(name = "status", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'CHUẨN BỊ'")
    private String status;

    @Column(name = "shipping_address", length = 255)
    private String shippingAddress;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
        status = "ĐANG CHUẨN BỊ";
    }
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}

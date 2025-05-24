package space.dinhphatphat.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT AUTO_INCREMENT")
    private int id;

    @Column(name = "token", columnDefinition = "CHAR(36)")
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_tokens_to_users"))
    private User user;

    @Column(name = "type", columnDefinition = "TINYINT")
    private int type;

    @Column(name = "expired_at", columnDefinition = "TIMESTAMP DEFAULT (CURRENT_TIMESTAMP + INTERVAL 20 MINUTE)")
    private Timestamp expiredAt;
}

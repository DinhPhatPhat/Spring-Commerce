package space.dinhphatphat.model;

import jakarta.persistence.*;
import lombok.*;
import java.text.Normalizer;
import java.util.regex.Pattern;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "stories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT AUTO_INCREMENT")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_stories_to_users"))
    private User user; // Liên kết với bảng User

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "meta", columnDefinition = "VARCHAR(255) DEFAULT '' ")
    private String meta;

    @Column(name = "image_path", length = 255, columnDefinition = "VARCHAR(255) DEFAULT NULL")
    private String imagePath;

    @Column(name = "is_approved", columnDefinition = "BIT DEFAULT 0")
    private boolean isApproved;

    @Column(name = "created_at",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    //Update approving the stories later
    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
        updatedAt = Timestamp.valueOf(LocalDateTime.now());
        isApproved = true;
    }

    public void setMetaWithId() {
        this.meta = toSlug(this.title) + "-" + this.id;
    }
    
    public static String toSlug(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("\\p{M}", ""); // Loại bỏ dấu
        slug = slug.replaceAll("[^a-zA-Z0-9\\s-]", "").toLowerCase(); // Chỉ giữ lại chữ cái, số, khoảng trắng, dấu '-'
        slug = slug.replaceAll("\\s+", "-"); // Thay khoảng trắng thành '-'
        return slug;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
}

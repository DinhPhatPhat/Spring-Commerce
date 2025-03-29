package space.dinhphatphat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT AUTO_INCREMENT")
    private int id;

    @Column(name = "name", columnDefinition = "VARCHAR(100) DEFAULT 'A friend' ")
    @NotBlank(message = "Tên không được bỏ trống")
    private String name;

    @Column(name = "phone_number", columnDefinition = "CHAR(10)")
    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(100)")
    @NotBlank(message = "Mật khẩu không được trống")
    @Size(min = 6, message = "Mật khẩu yêu cầu ít nhất 6 ký tự")
    private String password;

    @Column(name = "bio", columnDefinition = "VARCHAR(200) DEFAULT NULL")
    @Size(max = 200, message = "Tiểu sử không được vượt quá 200 ký tự")
    private String bio;

    @Column(name ="image_path", columnDefinition = "DEFAULT NULL")
    private String imagePath;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "role", columnDefinition = "TINYINT DEFAULT 0")
    private int role; // 0: User, 1: Admin

    @Column(name = "is_active", columnDefinition = "BIT DEFAULT 0")
    private boolean isActive;

    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
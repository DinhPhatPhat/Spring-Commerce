package space.dinhphatphat.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT AUTO_INCREMENT")
    private int id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
}

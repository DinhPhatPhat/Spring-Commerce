package space.dinhphatphat.repository;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.dinhphatphat.model.User;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByEmail(@NotEmpty(message = "Email không được bỏ trống") String email);

    Optional<User> findByEmail(@NotEmpty(message = "Email không được bỏ trống") String email);
}
